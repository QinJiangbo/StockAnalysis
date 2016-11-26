package com.whu.algorithms.phash;

import com.whu.algorithms.KChartThread;

public class MultiPHash extends KChartThread {

    double[] similarity; // 两张图之间的相似度
    int tag; // 比较的组别
    String path1 = "";
    String path2 = "";
    private ImagePHash imagePHash = new ImagePHash();

    public void run() {
        if (!path1.equals("") && !path2.equals("")) {
            similarity[tag - 1] = imagePHash.getSimilarity(path1, path2);
        }
    }

    public void start(int tag, double[] similarity, String path1, String path2) {
        this.tag = tag;
        this.similarity = similarity;
        this.path1 = path1;
        this.path2 = path2;
        super.start();
    }
}
