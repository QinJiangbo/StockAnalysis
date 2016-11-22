package com.whu.algorithms;

/**
 * Date: 21/11/2016
 * Author: qinjiangbo@github.io
 */
public abstract class KChartThread extends Thread {
    /**
     * 计算两个图片的相似度
     * @param tag
     * @param similarity
     * @param path1
     * @param path2
     */
    public abstract void start(int tag, double[] similarity, String path1, String path2);
}
