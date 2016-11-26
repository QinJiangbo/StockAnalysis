package com.whu.test;

import com.whu.algorithms.phash.ImagePHash;
import com.whu.util.ServerConstants;

/**
 * Date: 26/11/2016
 * Author: qinjiangbo@github.io
 */
public class TestThread extends Thread {

    private String image1;
    private String image2;
    private ImagePHash imagePHash = new ImagePHash();

    public void run() {
        double similarity = imagePHash.getSimilarity(image1, image2);
        System.out.println(this.getName() + "@" + similarity);
    }

    public void start(String image1, String image2) {
        this.image1 = ServerConstants.KCHART_IMAGES + image1;
        this.image2 = ServerConstants.KCHART_IMAGES + image2;
        super.start();
    }
}
