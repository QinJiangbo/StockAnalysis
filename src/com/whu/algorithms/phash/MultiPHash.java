package com.whu.algorithms.phash;

import com.whu.algorithms.KChartThread;

public class MultiPHash extends KChartThread {

    double[] similarity; // ����ͼ֮������ƶ�
    int tag; // �Ƚϵ����
    String path1 = "";
    String path2 = "";

    public void run() {
        if (path1 != "" && path2 != "") {
            similarity[tag - 1] = new ImagePHash().getSimilarity(path1, path2);
        }
    }

    public void start(int tag, double[] similarity, String path1, String path2) {
        super.start();
        this.tag = tag;
        this.similarity = similarity;
        this.path1 = path1;
        this.path2 = path2;
    }
}
