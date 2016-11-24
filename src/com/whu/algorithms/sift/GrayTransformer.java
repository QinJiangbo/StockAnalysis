package com.whu.algorithms.sift;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Wenan Wu on 2016/11/19 0019.
 */
public class GrayTransformer {

    /**
     * 灰度变化
     * @param sourcePath
     */
    public BufferedImage compute(String sourcePath) {
        BufferedImage sourceImage = grayTransform(sourcePath);
        HashMap<Integer, double[][]> result = ImageTransformer.getGaussPyramid(
                ImageProcessor.imageToDoubleArray(sourceImage), 20, 3, 1.6);
        return ImageProcessor.doubleArrayToGreyImage(result.get(8));
    }

    /**
     * 灰度转换
     * @param imagePath
     * @return
     */
    private BufferedImage grayTransform(String imagePath) {

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage targetImage;
        targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//BufferedImage.TYPE_BYTE_BINARY);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = bufferedImage.getRGB(i, j);

                int cRed = (rgb >> 16) & 0xFF;
                int cGreen = (rgb >> 8) & 0xFF;
                int cBlue = rgb & 0xFF;
                int grayRGB = (int) (0.3 * cRed + 0.59 * cGreen + 0.11 * cBlue);

                rgb = (255 << 24) | (grayRGB << 16) | (grayRGB << 8) | grayRGB;
                targetImage.setRGB(i, j, rgb);
            }
        }
        return targetImage;
    }
}
