package com.whu.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.whu.entity.ParamWeight;
import com.whu.entity.ResultEntity;
import com.whu.service.KChartService;
import com.whu.util.Algorithms;
import com.whu.util.ImageUtil;
import com.whu.util.ServerConstants;
import com.whu.util.StopWatch;

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
    private double hashWeight;
    private double levenWeight;
    private double siftWeight;
    private double klineWeight;
    private String sourceNo;
    private String targetNo;

    /**
     * 加载图片
     * @return
     */
    public String loadImages() {

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
     * @return
     */
    public String compare() {

        // 算法及权重映射
        Map<Algorithms, Double> algorithms = new HashMap<>();
        if (hashWeight != 0) {
            algorithms.put(Algorithms.MULTIPHASH, hashWeight);
        }
        if (levenWeight != 0) {
            algorithms.put(Algorithms.LEVENSHTEIN, levenWeight);
        }
        if (siftWeight != 0) {
            algorithms.put(Algorithms.SIFTPHASH, siftWeight);
        }

        // 设置K线图权重
        if (klineWeight != 0) {
            ParamWeight.K_WEIGHT = klineWeight;
        }

        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 计算
        ResultEntity resultEntity = KChartService.multiMixSimilarityComparision(sourceNo + ".txt", algorithms);
        resultEntity.sort(); // 排序
        // 打印时间
        int time = stopWatch.stop();
        System.out.println("Time: " + time + "s");
        // 输出结果
        int tag = resultEntity.getRank()[0].getTag();
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

    public double getHashWeight() {
        return hashWeight;
    }

    public void setHashWeight(double hashWeight) {
        this.hashWeight = hashWeight;
    }

    public double getKlineWeight() {
        return klineWeight;
    }

    public void setKlineWeight(double klineWeight) {
        this.klineWeight = klineWeight;
    }

}
