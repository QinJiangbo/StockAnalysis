package com.whu.util;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ImageUtil {

    private static List<Thread> genThreadList = new ArrayList<>();

    /**
     * ����ͼƬ
     * @return
     */
    public static List<Thread> generate() {
        File dir = new File(ServerConstants.STOCK_DATA);
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.getName().toLowerCase().endsWith(".txt")) {
                    //����ͼƬ
                    ImageGenerator generator = new ImageGenerator(file);
                    Thread thread = new Thread(generator);
                    genThreadList.add(thread);
                    thread.start();
                }
            }
        }
        return genThreadList;
    }

    /**
     * ѹ��ͼƬ
     * @return
     */
    public static boolean compress() {
        File dir = new File(ServerConstants.KCHART_IMAGES);
        File[] images = dir.listFiles();
        if (images != null) {
            for (File image: images) {
                if(image.getName().toLowerCase().endsWith(".jpg")) {
                    // ѹ��ͼƬ
                    ImageCompressor compressor = new ImageCompressor(image);
                    compressor.start();
                }
            }
        }
        return true;
    }

}

