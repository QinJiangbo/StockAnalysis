package com.whu.algorithms.sift;

import com.whu.algorithms.KChartThread;
import com.whu.algorithms.phash.ImagePHash;
import com.whu.util.ServerConstants;

/**
 * Date: 21/11/2016
 * Author: qinjiangbo@github.io
 */
public class SiftPHash extends KChartThread {

    private double[] similarity; // 两张图之间的相似度
    private int tag; // 比较的组别
    private String path1 = "";
    private String path2 = "";
    private ImagePHash imagePHash = new ImagePHash();

    public void run() {
        path1 = path1.replace(ServerConstants.KCHART_IMAGES, ServerConstants.KCHART_COMPRESSED_IMAGES);
        path2 = path2.replace(ServerConstants.KCHART_IMAGES, ServerConstants.KCHART_COMPRESSED_IMAGES);
        similarity[tag - 1] = imagePHash.getSimilarity(path1, path2);
    }

    @Override
    public void start(int tag, double[] similarity, String path1, String path2) {
        this.tag = tag;
        this.similarity = similarity;
        this.path1 = path1;
        this.path2 = path2;
        super.start();
    }

}
