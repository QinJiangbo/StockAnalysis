package com.whu.algorithms.levenshtein;

import com.whu.algorithms.KChartThread;

import java.awt.image.BufferedImage;

/**
 * Date: 20/11/2016
 * Author: qinjiangbo@github.io
 */
public class LevenShtein extends KChartThread {

    double[] similarity; // ����ͼ֮������ƶ�
    int tag; // �Ƚϵ����
    String path1 = "";
    String path2 = "";

    public void start(int tag, double[] similarity, String path1, String path2)
    {
        this.tag = tag;
        this.similarity = similarity;
        this.path1 = path1;
        this.path2 = path2;
        super.start();
    }

    /**
     * �Ƚ����ƶ�
     */
    public void run() {
        if (!path1.equals("") && !path2.equals("")) {
            String img1HashCode = produceFingerPrint(path1);
            String img2HashCode = produceFingerPrint(path2);
            similarity[tag - 1] = HashCodeDiff.levenshtein(img1HashCode, img2HashCode);
        }
    }

	/**
	 * ����ͼƬָ��
	 * @param filename �ļ���
	 * @return ͼƬָ��
	 */
	public String produceFingerPrint(String filename) {

        BufferedImage source = ImageRead.readImage(filename);// ��ȡ�ļ�
		int width = 8;
		int height = 8;
		// ��һ������С�ߴ硣
		// ��ͼƬ��С��8x8�ĳߴ磬�ܹ�64�����ء���һ����������ȥ��ͼƬ��ϸ�ڣ�ֻ�����ṹ�������Ȼ�����Ϣ��������ͬ�ߴ硢����������ͼƬ���졣
		BufferedImage thumb = ImageRead.thumb(source, width, height, false);

		// �ڶ�������ɫ�ʡ�
		// ����С���ͼƬ��תΪ64���Ҷȡ�Ҳ����˵���������ص��ܹ�ֻ��64����ɫ��
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i * height + j] = ImageRead.rgbToGray(thumb.getRGB(i, j));
			}
		}

		// ������������ƽ��ֵ��
		// ��������64�����صĻҶ�ƽ��ֵ��
		int avgPixel = ImageRead.average(pixels);

		// ���Ĳ����Ƚ����صĻҶȡ�
		// ��ÿ�����صĻҶȣ���ƽ��ֵ���бȽϡ����ڻ����ƽ��ֵ����Ϊ1��С��ƽ��ֵ����Ϊ0��
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// ���岽�������ϣֵ��
		// ����һ���ıȽϽ���������һ�𣬾͹�����һ��64λ�����������������ͼƬ��ָ�ơ���ϵĴ��򲢲���Ҫ��ֻҪ��֤����ͼƬ������ͬ����������ˡ�
		StringBuffer hashCode = new StringBuffer();
		for (int i = 0; i < comps.length; i += 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2)
					+ comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(binaryToHex(result));
		}

		// �õ�ָ���Ժ󣬾Ϳ��ԶԱȲ�ͬ��ͼƬ������64λ���ж���λ�ǲ�һ���ġ�
		return hashCode.toString();
	}

	/**
	 * ������תΪʮ������
	 * @param binary
	 * @return hex
	 */
	private char binaryToHex(int binary) {
		char ch = ' ';
		switch (binary) {
		case 0:
			ch = '0';
			break;
		case 1:
			ch = '1';
			break;
		case 2:
			ch = '2';
			break;
		case 3:
			ch = '3';
			break;
		case 4:
			ch = '4';
			break;
		case 5:
			ch = '5';
			break;
		case 6:
			ch = '6';
			break;
		case 7:
			ch = '7';
			break;
		case 8:
			ch = '8';
			break;
		case 9:
			ch = '9';
			break;
		case 10:
			ch = 'a';
			break;
		case 11:
			ch = 'b';
			break;
		case 12:
			ch = 'c';
			break;
		case 13:
			ch = 'd';
			break;
		case 14:
			ch = 'e';
			break;
		case 15:
			ch = 'f';
			break;
		default:
			ch = ' ';
		}
		return ch;
	}
}
