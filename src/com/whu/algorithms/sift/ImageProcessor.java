package com.whu.algorithms.sift;

import java.awt.image.BufferedImage;

/**
 * 数字图像处理辅助类
 *
 * @author Administrator
 */
public class ImageProcessor {

    ///soble算子
    private static int[][] sobleX = {{-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}};

    private static int[][] sobleY = {{1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}};

    //开闭操作的
    ///结构元素
    private static int sData[] = {
            0, 0, 1, 0, 0,
            0, 0, 1, 0, 0,
            1, 1, 1, 1, 1,
            0, 0, 1, 0, 0,
            0, 0, 1, 0, 0
    };

    private static int sX = 5;///结构元素的列数
    private static int sY = 5;////结构元素的行数

    /**
     * 图像的开运算： 先腐蚀再膨胀
     * @param source    此处处理灰度图像或者二值图像
     * @param threshold :阈值————当膨胀结果小于阈值时，仍然设置图像位置的值为0；而进行腐蚀操作时，
     *                  当灰度值大于等于阈值（小于阈值）时并且结构元素为1（0）时，才认为对应位置匹配上；
     *                  如果为二值图像，则应该传入1。
     * @return
     */
    public static int[][] open(int[][] source, int threshold) {

        int width = source[0].length;
        int height = source.length;

        int[][] result = new int[height][width];
        int[][] result1 = new int[height][width];
        ///先腐蚀运算
        result = correde(source, threshold);
        //后膨胀运算
        result = dilate(result, threshold);

        return result;
    }

    /**
     * 利用腐蚀运算进行边缘提取，再膨胀
     * @param source
     * @param threshold
     * @return
     */
    public static int[][] edgeExtract(int[][] source, int threshold) {

        int width = source[0].length;
        int height = source.length;

        int[][] result = new int[height][width];
        int[][] result1 = new int[height][width];
        ///先腐蚀运算
        result = correde(source, threshold);
        //后膨胀运算
        result1 = dilate(source, threshold);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
//				System.out.print(result[j][i]+",");
                int temp = Math.abs(result1[j][i] - result[j][i]);

                if (temp >= 100) {
                    result[j][i] = temp;
                } else {
                    result[j][i] = 0;
                }
            }

        }    ///获取边缘
        return result;
    }

    /**
     * 腐蚀运算
     * @param source
     * @param threshold 当灰度值大于阈值（小于阈值）时并且结构元素为1（0）时，才认为对应位置匹配上；
     * @return
     */
    public static int[][] correde(int[][] source, int threshold) {
        int width = source[0].length;
        int height = source.length;

        int[][] result = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tempx = sX / 2;
                int tempy = sY / 2;
                ///边缘不进行操作，边缘内才操作
                if (i >= tempy && j >= tempx && i < height - tempy && j < width - tempx) {
                    int max = 0;

                    ///对结构元素进行遍历
                    for (int k = 0; k < sData.length; k++) {
                        int x = k % sX;///商表示x偏移量
                        int y = k / sX;///余数表示y偏移量

                        if (sData[k] != 0) {
                            ///不为0时，必须全部大于阈值，否则就设置为0并结束遍历
                            if (source[i - tempy + y][j - tempx + x] >= threshold) {
                                if (source[i - tempy + y][j - tempx + x] > max) {
                                    max = source[i - tempy + y][j - tempx + x];
                                }
                            } else {
                                ////与结构元素不匹配,赋值0,结束遍历
                                max = 0;
                                break;
                            }
                        }
                    }

                    ////此处可以设置阈值，当max小于阈值的时候就赋为0
                    result[i][j] = max;

                } else {
                    ///直接赋值
                    result[i][j] = source[i][j];

                }///end of the most out if-else clause .

            }
        }///end of outer for clause
        return result;
    }

    /**
     * 膨胀运算
     * @param source
     * @param threshold 当与运算结果值小于阈值时，图像点的值仍然设为0
     * @return
     */
    public static int[][] dilate(int[][] source, int threshold) {
        int width = source[0].length;
        int height = source.length;

        int[][] result = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                int tempx = sX / 2;
                int tempy = sY / 2;
                ///边缘不进行操作，边缘内才操作
                if (i >= tempy && j >= tempx && i < height - tempy && j < width - tempx) {
                    int max = 0;

                    ///对结构元素进行遍历
                    for (int k = 0; k < sData.length; k++) {
                        int y = k / sX;///商表示x偏移量
                        int x = k % sX;///余数表示y偏移量

                        if (sData[k] != 0) {
                            ///当结构元素中不为0时,取出图像中对应各项的最大值赋给图像当前位置作为灰度值
                            if (source[i - tempy + y][j - tempx + x] > max) {
                                max = source[i - tempy + y][j - tempx + x];
                            }
                        }
                    }

                    result[i][j] = max;

                } else {
                    ///直接赋值
                    result[i][j] = source[i][j];
                }

            }
        }
        return result;
    }

    /**
     * 图像soble算子梯度化轮廓灰度图提取
     * @param sourceImage
     * @param threshold
     * @return
     */
    public static BufferedImage sobleTran(BufferedImage sourceImage, int threshold) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                int rgb = 0;
                if (i > 0 && j > 0 && j < height - 1 & i < width - 1) {

                    int grayRGB0 = sourceImage.getRGB(i - 1, j - 1) >> 16;
                    int grayRGB1 = sourceImage.getRGB(i - 1, j) >> 16;
                    int grayRGB2 = sourceImage.getRGB(i - 1, j + 1) >> 16;
                    int grayRGB3 = sourceImage.getRGB(i, j - 1) >> 16;
                    int grayRGB4 = sourceImage.getRGB(i, j) >> 16;
                    int grayRGB5 = sourceImage.getRGB(i, j + 1) >> 16;
                    int grayRGB6 = sourceImage.getRGB(i + 1, j - 1) >> 16;
                    int grayRGB7 = sourceImage.getRGB(i + 1, j) >> 16;
                    int grayRGB8 = sourceImage.getRGB(i + 1, j + 1) >> 16;

                    //int result=(int) Math.sqrt((grayRGB0-grayRGB1)*(grayRGB0-grayRGB1)+(grayRGB0-grayRGB1)*(grayRGB0-grayRGB2));///梯度处理
                    //int result=Math.abs(grayRGB5-grayRGB4)+Math.abs(grayRGB7-grayRGB4);///梯度处理

                    ///soble算子获取梯度
                    int result = 0;
                    int dx = sobleX[0][0] * grayRGB0 + sobleX[0][1] * grayRGB1 + sobleX[0][2] * grayRGB2
                            + sobleX[1][0] * grayRGB3 + sobleX[1][1] * grayRGB4 + sobleX[1][2] * grayRGB5
                            + sobleX[2][0] * grayRGB6 + sobleX[2][1] * grayRGB7 + sobleX[2][2] * grayRGB8;
                    int dy = sobleY[0][0] * grayRGB0 + sobleY[0][1] * grayRGB1 + sobleY[0][2] * grayRGB2
                            + sobleY[1][0] * grayRGB3 + sobleY[1][1] * grayRGB4 + sobleY[1][2] * grayRGB5
                            + sobleY[2][0] * grayRGB6 + sobleY[2][1] * grayRGB7 + sobleY[2][2] * grayRGB8;
                    result = (int) Math.sqrt(dx * dx + dy * dy);
			/*		result=dx>dy?dx:dy;
					result=(int) Math.sqrt(result)+100;*/
                    if (result <= threshold) {
                        ///此处阈值设为??实验效果最好
                        rgb = 0;
                        //System.out.print(0);
                    } else {
                        int grayRGB = result;
                        rgb = (grayRGB << 16) | (grayRGB << 8) | grayRGB;
                    }
                    int grayRGB = result;
                    rgb = (grayRGB << 16) | (grayRGB << 8) | grayRGB;
                } else {
                    rgb = sourceImage.getRGB(i, j);
                }
                targetImage.setRGB(i, j, rgb);

            }
        }
        return targetImage;
    }

    /**
     * 在x——y轴上取梯度极差值作为灰度值
     * @param sourceImage
     * @param threshold
     * @return
     */
    public static BufferedImage xyTran(BufferedImage sourceImage, int threshold) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                int rgb = 0;
                if (i > 0 && j > 0 && j < height - 1 & i < width - 1) {

                    int grayRGB0 = sourceImage.getRGB(i, j) >> 16;
                    int grayRGB1 = sourceImage.getRGB(i + 1, j) >> 16;
                    int grayRGB2 = sourceImage.getRGB(i, j + 1) >> 16;


                    int xd = Math.abs(grayRGB0 - grayRGB1);
                    int yd = Math.abs(grayRGB2 - grayRGB0);
                    int result = xd > yd ? xd : yd;///梯度处理


                    if (result <= threshold) {
                        ///此处阈值设为??实验效果最好
                        result = 0;
                        //System.out.print(0);
                    }

                    int grayRGB = result;
                    rgb = (grayRGB << 16) | (grayRGB << 8) | grayRGB;

                } else {
                    rgb = sourceImage.getRGB(i, j);
                }

                targetImage.setRGB(i, j, rgb);

            }
        }
        return targetImage;
    }

    /**
     * 高斯滤波，全局平均
     * @param sourceImage
     * @return
     */
    public static BufferedImage guassFilter(BufferedImage sourceImage) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                int rgb = 0;
                if (i > 0 && j > 0 && j < height - 1 & i < width - 1) {

                    int grayRGB0 = sourceImage.getRGB(i, j) >> 16;
                    int grayRGB1 = sourceImage.getRGB(i + 1, j) >> 16;
                    int grayRGB2 = sourceImage.getRGB(i - 1, j) >> 16;
                    int grayRGB3 = sourceImage.getRGB(i, j - 1) >> 16;
                    int grayRGB4 = sourceImage.getRGB(i, j + 1) >> 16;

                    int grayRGB = Math.abs(4 * grayRGB0 + grayRGB1 + grayRGB2 + grayRGB3 + grayRGB4) / 8;
                    rgb = (grayRGB << 16) | (grayRGB << 8) | grayRGB;

                } else {
                    rgb = sourceImage.getRGB(i, j);
                }

                targetImage.setRGB(i, j, rgb);

            }
        }
        return targetImage;
    }

    /**
     * 灰度图像提取数组
     * @param image
     * @return int[][]数组
     */
    public static int[][] imageToArray(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        int[][] result = new int[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = image.getRGB(i, j);
                int grey = (rgb >> 16) & 0xFF;
//				System.out.println(grey);
                result[j][i] = grey;
            }
        }
        return result;
    }

    /**
     * 灰度图像提取数组
     * @param image
     * @return int[][]数组
     */
    public static double[][] imageToDoubleArray(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        double[][] result = new double[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = image.getRGB(i, j);
                int grey = (rgb >> 16) & 0xFF;
//				System.out.println(grey);
                result[j][i] = grey;

            }
        }
        return result;
    }

    /**
     * 数组转为灰度图像
     * @param sourceArray
     * @return
     */
    public static BufferedImage arrayToGreyImage(int[][] sourceArray) {
        int width = sourceArray[0].length;
        int height = sourceArray.length;
        BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int greyRGB = sourceArray[j][i];
                int rgb = (greyRGB << 16) | (greyRGB << 8) | greyRGB;

                targetImage.setRGB(i, j, rgb);
            }
        }

        return targetImage;
    }

    /**
     * 数组转为灰度图像
     * @param sourceArray
     * @return
     */
    public static BufferedImage doubleArrayToGreyImage(double[][] sourceArray) {
        int width = sourceArray[0].length;
        int height = sourceArray.length;
        BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int greyRGB = (int) sourceArray[j][i];
                int rgb = (greyRGB << 16) | (greyRGB << 8) | greyRGB;

                targetImage.setRGB(i, j, rgb);
            }
        }
        return targetImage;
    }
}
