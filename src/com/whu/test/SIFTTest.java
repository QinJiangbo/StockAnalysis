package com.whu.test;

import com.whu.algorithms.phash.ImagePHash;
import com.whu.algorithms.sift.GrayTransformer;
import com.whu.util.ServerConstants;
import org.jfree.chart.ChartUtilities;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Wenan Wu on 2016/11/20 0020.
 */
public class SIFTTest {
    public static void main(String[] args) {
        ImagePHash ip = new ImagePHash();
        double s1 = 0;
        double s2 = 0;

        String imageURL1 = ServerConstants.KCHART_IMAGES + "SZ300026.txt-k.jpg";
        String imageURL2 = ServerConstants.KCHART_IMAGES + "SZ300080.txt-k.jpg";

        String cImageURL1 = ServerConstants.KCHART_COMPRESSED_IMAGES + "SZ300026.txt-k.jpg";
        String cImageURL2 = ServerConstants.KCHART_COMPRESSED_IMAGES + "SZ300080.txt-k.jpg";
        OutputStream outputStream1 = null;
        OutputStream outputStream2 = null;
        try {
            outputStream1 = new FileOutputStream(new File(cImageURL1));
            outputStream2 = new FileOutputStream(new File(cImageURL2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        double pHashStart = System.currentTimeMillis();
        s1 = ip.getSimilarity(imageURL1, imageURL2);

        double pHashEnd = System.currentTimeMillis();

        GrayTransformer transformer = new GrayTransformer();
        BufferedImage img1 = transformer.compute(imageURL1);
        BufferedImage img2 = transformer.compute(imageURL2);

        try {
            ChartUtilities.writeBufferedImageAsJPEG(outputStream1, img1);
            ChartUtilities.writeBufferedImageAsJPEG(outputStream2, img2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double siftStart = System.currentTimeMillis();
        try {
            s2 = ip.getSimilarity(img1, img2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double siftEnd = System.currentTimeMillis();

        System.out.println("pHash相似度:" + s1);
        System.out.println("pHash耗时:"+ (pHashEnd-pHashStart));
        System.out.println("SIFT+pHash相似度:" + s2);
        System.out.println("SIFT+pHash耗时:" + (siftEnd - siftStart));
    }
}
