package com.whu.util;

/**
 * Date: 03/12/2016
 * Author: qinjiangbo@github.io
 */
public class StopWatch {

    private long start = 0l;

    /**
     * 开始计时
     */
    public void start() {
        start = System.currentTimeMillis();
    }

    /**
     * 计算时间
     * @return
     */
    public int stop() {
        long end = System.currentTimeMillis();
        int time = (int) (end - start) / 1000;
        return time;
    }

    /**
     * 重置时间
     */
    public void reset() {
        start = System.currentTimeMillis();
    }
}
