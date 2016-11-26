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
        for (int i = 0; i < 300; i++) {
            threadList.add(new Thread(new RunTest(i)));
        }
        for (int i = 0; i < 300; i++) {
            threadList.get(i).start();
        }
//        for (int i=0; i<300; i++) {
//            while (threadList.get(i).isAlive());
//        }
    }


}

class RunTest implements Runnable {

    private int i;

    public RunTest(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i + "*" + i + "=" + i * i);
    }

}