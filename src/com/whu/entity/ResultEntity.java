package com.whu.entity;

public class ResultEntity {

    private String[] path;
    private int size;
    private double[] similarity;
    private Rank[] rank;

    public ResultEntity(int size) {
        path = new String[size];
        similarity = new double[size];
        this.size = size;
    }

    /**
     * 快速排序
     * @param n
     * @param left
     * @param right
     */
    private void quicksort(Rank n[], int left, int right) {
        int dp;
        if (left < right) {
            dp = partition(n, left, right);
            quicksort(n, left, dp - 1);
            quicksort(n, dp + 1, right);
        }
    }

    /**
     * 分治法
     * @param n
     * @param left
     * @param right
     * @return
     */
    private int partition(Rank n[], int left, int right) {
        Rank pivot = n[left];
        while (left < right) {
            while (left < right && n[right].getSimilarity() <= pivot.getSimilarity())
                right--;
            if (left < right)
                n[left++] = n[right];
            while (left < right && n[left].getSimilarity() >= pivot.getSimilarity())
                left++;
            if (left < right)
                n[right--] = n[left];
        }
        n[left] = pivot;
        return left;
    }

    /**
     * 排序
     */
    public void sort() {
        rank = new Rank[size];
        for (int i = 0; i < size; i++) {
            rank[i] = new Rank();
            rank[i].setTag(i + 1);
            rank[i].setSimilarity(similarity[i]);
        }
        //排序算法
        //快排;
        quicksort(rank, 0, size - 1);
        System.out.println("Sort Completed!");
    }

    public Rank[] getRank() {
        return rank;
    }

    public int getSize() {
        return this.size;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public double[] getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double[] similarity) {
        this.similarity = similarity;
    }
}
