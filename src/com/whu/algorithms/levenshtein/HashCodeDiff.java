package com.whu.algorithms.levenshtein;

/**
 * ����ͼƬָ�Ƶ����ƶ�,��ȡ�༭������㷨
 * Date: 20/11/2016
 * Author: qinjiangbo@github.io
 */
public class HashCodeDiff {

    /**
     * ���������ַ��������ƶ�
     * @param str1
     * @param str2
     * @return
     */
    public static double levenshtein(String str1, String str2) {
        // ���������ַ����ĳ���
        int len1 = str1.length();
        int len2 = str2.length();
        // ��������˵�����飬���ַ����ȴ�һ���ռ�
        int[][] dif = new int[len1 + 1][len2 + 1];
        // ����ֵ
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        // ���������ַ��Ƿ�һ��
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // ȡ����ֵ����С��
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1, dif[i - 1][j] + 1);
            }
        }
//        System.out.println("�ַ���\"" + str1 + "\"��\"" + str2 + "\"�ıȽ�");
//        System.out.println("���첽�裺" + dif[len1][len2]);
        // �������ƶ�
        double similarity = 1 - (double) dif[len1][len2] / Math.max(str1.length(), str2.length());
//        System.out.println(similarity);
        return similarity;
    }

    // �õ���Сֵ
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

}
