package com.whu.util;

import java.io.File;

public class FileNameUtil {

    /**
     * 列出所有的图片名称
     * @return
     */
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

    /**
     * 重命名文件
     * @param dir 目录
     */
    public static void renameFileNames(String dir) {
        File f = new File(dir);
        File[] list = f.listFiles();
        for (File file : list) {
            String fileName = file.getName();
            String newName = fileName.replace("#","");
            File newFile = new File(dir + newName);
            file.renameTo(newFile);
            System.out.println(fileName + "==>" + newName);
        }
    }

}
