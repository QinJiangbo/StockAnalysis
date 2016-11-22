package com.whu.algorithms.phash;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * phash�㷨Ӧ����ͼƬ���ƶȼ���
 */
public class ImagePHash {

    private int size;
    private int smallerSize;

    public ImagePHash() {
        this.size = 32;
        this.smallerSize = 8;
    }

    /**
     * ��ȡͼƬ��Hashֵ
     *
     * @param sourceImage
     * @return
     * @throws Exception
     */
    public String getHash(BufferedImage sourceImage) throws Exception {

        // 1. ��СͼƬ�ߴ�,��ͼƬ�任��32*32�Ĵ�С
        BufferedImage newImg = resize(sourceImage, size, size);

        // 2. ��ɫ��
        sourceImage = grayscale(newImg);

        double[][] vals = new double[size][size];

        for (int i = 0; i < sourceImage.getWidth(); i++) {
            for (int j = 0; j < sourceImage.getHeight(); j++) {
                vals[i][j] = getBlue(sourceImage, i, j);
            }
        }

        //3. ����DCTϵ������
        double[][] dctVals = applyDCT(vals);

        // 4. ��СDCTϵ�����󣬱������Ͻ�8*8
        double total = 0.0;

        for (int i = 0; i < smallerSize; i++) {
            for (int j = 0; j < smallerSize; j++) {
                total += dctVals[i][j];
            }
        }
        total -= dctVals[0][0];//ȥ����һ������

        // 5. �����ֵ
        double avg = total / (double) (smallerSize * smallerSize - 1);

        // 6. ��һ����СDCT,���ɶ������ַ���
        String hashStr = "";

        for (int i = 0; i < smallerSize; i++) {
            for (int j = 0; j < smallerSize; j++) {
                if (i == 0 && j == 0)
                    continue;
                hashStr += (dctVals[i][j] >= avg ? "1" : "0");
            }
        }

        return hashStr;
    }

    /*
     * ��Ҫ����
     * ���ͼƬ��hash�룬�Զ������ַ�����ʾ
     * �� 001010111011100010...
     */
    public String getHash(String imgUrl) throws Exception {
        BufferedImage img = ImageIO.read(new FileInputStream(new File(imgUrl)));
        return getHash(img);
    }

    /*
     * �任ͼƬ
     */
    private BufferedImage resize(BufferedImage image, int width, int height) throws IOException {

        //����һ��BufferedImage���趨���ĸߡ��������
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        //��ԭͼ���ػ�����ͼ����
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    /*
     * ��ɫ��,��Ϊ�Ҷ�
     */
    private BufferedImage grayscale(BufferedImage img) {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp colorConvert = new ColorConvertOp(cs, null);
        colorConvert.filter(img, img);
        return img;
    }

    /*
     * ��ȡ�Ҷ�ͼƬ������ֵ
     * �Ҷ�ͼƬ��red = green = red��
     * ��ȡһ����������
     */
    private static int getBlue(BufferedImage img, int x, int y) {
        return (img.getRGB(x, y)) & 0xff;
    }

    /*
     * ������ɢ���ұ任(��άDCT�任)
     * ����ϵ������
     */
    private double[][] applyDCT(double[][] f) {

        int N = size;

        double[][] F = new double[smallerSize][smallerSize];
        double coefficients = 0.0;

        for (int u = 0; u < smallerSize; u++) {
            for (int v = 0; v < smallerSize; v++) {

                double sum = 0.0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        sum += Math.cos(((i + 0.5) / N) * u * Math.PI) * Math.cos(((j + 0.5) / N) * v * Math.PI) * f[i][j];
                    }
                }

                if (u == 0 && v == 0)
                    coefficients = Math.sqrt(1.0 / (N * N));
                else if (u == 0 || v == 0)
                    coefficients = Math.sqrt(2.0 / (N * N));
                else
                    coefficients = Math.sqrt(4.0 / (N * N));

                sum *= coefficients;
                F[u][v] = sum;
            }
        }
        return F;
    }

    /*
     * ���㺺������
     */
    public int caculateHammingDistance(String hashStr1, String hashStr2) {
        int distance = 0;

        for (int i = 0; i < hashStr1.length(); i++) {
            if (hashStr1.charAt(i) != hashStr2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    /*
     * �������ͼƬ�����ƶ�
     */
    public double getSimilarity(String imageUrl1, String imageUrl2){
        double similarity = 0;//���ƶ�

        String hashStr1 = null;//��һ��ͼƬ��64λ�������ַ���
        String hashStr2 = null;//�ڶ���ͼƬ��64λ�������ַ���
        try {
            hashStr1 = getHash(imageUrl1);
            hashStr2 = getHash(imageUrl2);
        } catch (Exception e) {
            //�ر��쳣���
        }

        int distance = caculateHammingDistance(hashStr1, hashStr2);//���㺺������
        similarity = (64 - distance) / 64.0;
        return similarity;
    }

    /**
     * ���ط���
     * ��ȡ����ͼƬ�����ƶ�
     *
     * @param sourceImage ԴͼƬ
     * @param targetImage Ŀ��ͼƬ
     * @return
     */
    public double getSimilarity(BufferedImage sourceImage, BufferedImage targetImage) {
        double similarity = 0; //���ƶ�

        String hashStr1 = null; // ԴͼƬ��64λHashֵ
        String hashStr2 = null; // Ŀ��ͼƬ��64λHashֵ
        try {
            hashStr1 = getHash(sourceImage);
            hashStr2 = getHash(targetImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int distance = caculateHammingDistance(hashStr1, hashStr2);
        similarity = (64 - distance) / 64.0;
        return similarity;
    }
}