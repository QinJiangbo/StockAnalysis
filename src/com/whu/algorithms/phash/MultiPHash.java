package com.whu.algorithms.phash;

import com.whu.algorithms.KChartThread;

public class MultiPHash extends KChartThread {

    double[] similarity; // ����ͼ֮������ƶ�
    int tag; // �Ƚϵ����
    String path1 = "";
    String path2 = "";
    private ImagePHash imagePHash = new ImagePHash();

    public void run() {
//        try {
            similarity[tag - 1] = imagePHash.getSimilarity(path1, path2);
//        } catch (NullPointerException e) {
//            System.out.println("path1==>" + path1);
//            System.out.println("path2==>" + path2);
//        }

    }

    public void start(int tag, double[] similarity, String path1, String path2) {
        this.tag = tag;
        this.similarity = similarity;
        this.path1 = path1;
        this.path2 = path2;
        super.start();
    }
}
