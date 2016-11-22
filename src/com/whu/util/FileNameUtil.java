package com.whu.util;

import java.io.File;

public class FileNameUtil {

    public static String[] listImageNames() {
        File f = new File(ServerConstants.STOCK_DATA);
        File[] list = f.listFiles();
        String[] fileNames = new String[list.length];
        int tag = 0;
        for (File file : list) {
            if (file.getName().toLowerCase().endsWith(".txt")) {
                fileNames[tag] = file.getName();
                tag++;
            }
        }
        return fileNames;
    }
}
