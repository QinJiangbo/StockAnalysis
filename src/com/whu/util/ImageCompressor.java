package com.whu.util;

import com.whu.algorithms.sift.GrayTransformer;
import org.jfree.chart.ChartUtilities;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Date: 21/11/2016
 * Author: qinjiangbo@github.io
 */
public class ImageCompressor{

    private File file;

    public ImageCompressor(File file) {
        this.file = file;
    }

    public void start() {
        GrayTransformer siftComputer = new GrayTransformer();
        String imageName = file.getName();
        String compressPath = ServerConstants.KCHART_COMPRESSED_IMAGES + imageName;
        try {
            OutputStream outputStream = new FileOutputStream(new File(compressPath));
            BufferedImage bufferedImage = siftComputer.compute(file.getAbsolutePath());
            ChartUtilities.writeBufferedImageAsJPEG(outputStream, bufferedImage);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
