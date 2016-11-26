package com.whu.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 26/11/2016
 * Author: qinjiangbo@github.io
 */
public class ThreadTest {

    public static void main(String[] args) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 100; i < 200; i++) {
            TestThread thread = new TestThread();
            threadList.add(thread);
            thread.start("SZ300001.txt-k.jpg", "SZ300" + i + ".txt-k.jpg");
        }
    }
}
