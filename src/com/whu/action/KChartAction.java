package com.whu.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.whu.entity.ParamWeight;
import com.whu.entity.ResultEntity;
import com.whu.service.KChartService;
import com.whu.util.Algorithms;
import com.whu.util.ImageUtil;
import com.whu.util.ServerConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 20/11/2016
 * Author: qinjiangbo@github.io
 */
public class KChartAction extends ActionSupport{

    private String result;
    private double pHashWeight;
    private double levenWeight;
    private double siftWeight;
    private double kWeight;
    private String sourceNo;
    private String targetNo;

    /**
     * 加载图片
     *
     * @return
     */
    public String loadImages() {
        File imageDir = new File(ServerConstants.KCHART_IMAGES);
        File compressDir = new File(ServerConstants.KCHART_COMPRESSED_IMAGES);
        if (imageDir.listFiles() == null) {
            // 启动生成图片过程
            ImageUtil.generate();
        }
        if (compressDir.listFiles() == null) {
            // 启动压缩图片过程
            ImageUtil.compress();
        }
        // 获取所有的文件名
        File[] files = new File(ServerConstants.STOCK_DATA).listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".txt")) {
                fileNames.add(file.getName().substring(0, file.getName().indexOf(".txt")));
            }
        }
        result = JSON.toJSONString(fileNames);
        return SUCCESS;
    }

    /**
     * 比较各个算法
     *
     * @return
     */
    public String compare() {

        // 算法及权重映射
        Map<Algorithms, Double> algorithms = new HashMap<>();
        if (pHashWeight != 0.0) {
            algorithms.put(Algorithms.MULTIPHASH, pHashWeight);
        }
        if (levenWeight != 0.0) {
            algorithms.put(Algorithms.LEVENSHTEIN, levenWeight);
        }
        if (siftWeight != 0.0) {
            algorithms.put(Algorithms.SIFTPHASH, siftWeight);
        }

        // 设置K线图权重
        if (kWeight != 0.0) {
            ParamWeight.K_WEIGHT = kWeight;
        }

        // 计算
        ResultEntity resultEntity = KChartService.multiMixSimilarityComparation(sourceNo + ".txt", algorithms);
        resultEntity.sort(); // 排序
        int tag = resultEntity.getRank()[0].getTag();
        System.out.println("similarity: " + resultEntity.getRank()[0].getSimilarity());
        targetNo = resultEntity.getPath()[tag - 1];
        return SUCCESS;
    }


    /**
     * Getter和Setter方法
     */
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }

    public String getTargetNo() {
        return targetNo;
    }

    public void setTargetNo(String targetNo) {
        this.targetNo = targetNo;
    }

    public double getPHashWeight() {
        return pHashWeight;
    }

    public void setPHashWeight(double pHashWeight) {
        this.pHashWeight = pHashWeight;
    }

    public double getLevenWeight() {
        return levenWeight;
    }

    public void setLevenWeight(double levenWeight) {
        this.levenWeight = levenWeight;
    }

    public double getSiftWeight() {
        return siftWeight;
    }

    public void setSiftWeight(double siftWeight) {
        this.siftWeight = siftWeight;
    }

    public double getkWeight() {
        return kWeight;
    }

    public void setkWeight(double kWeight) {
        this.kWeight = kWeight;
    }


}
