package com.whu.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.whu.entity.ParamWeight;
import com.whu.entity.ResultEntity;
import com.whu.entity.ResultRank;
import com.whu.service.KChartService;
import com.whu.util.Algorithms;
import com.whu.util.EhCacheUtil;
import com.whu.util.ServerConstants;
import com.whu.util.StopWatch;

import java.io.File;
import java.util.*;

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
        String algsName = ""; // 确定参与本次计算算法的名称
        if (hashWeight != 0) {
            algorithms.put(Algorithms.MULTIPHASH, hashWeight);
            algsName = Algorithms.MULTIPHASH.name();
        }
        if (levenWeight != 0) {
            algorithms.put(Algorithms.LEVENSHTEIN, levenWeight);
            algsName = Algorithms.LEVENSHTEIN.name();
        }
        if (siftWeight != 0) {
            algorithms.put(Algorithms.SIFTPHASH, siftWeight);
            algsName = Algorithms.SIFTPHASH.name();
        }

        // 设置K线图权重
        ParamWeight.K_WEIGHT = klineWeight != 0 ? klineWeight : 0;

        // 如果是三个算法混合计算就不加入缓存，没意义
        Set<Map.Entry<Algorithms, Double>> entrySet = algorithms.entrySet();
        if(entrySet.size() > 1) {
            // 计算
            ResultEntity resultEntity = KChartService.multiMixSimilarityComparision(sourceNo + ".txt", algorithms);
            resultEntity.sort(); // 排序
            // 输出结果
            ResultRank[] resultRanks = resultEntity.getRank();
            // 存入缓存
            StringBuffer stringBuffer = new StringBuffer();
            for(ResultRank resultRank : resultRanks) {
                stringBuffer.append(resultEntity.getPath()[resultRank.getTag()-1] + ",");
            }
            result = stringBuffer.toString();
            result = result.substring(0, result.length() - 1);
            return SUCCESS;
        }

        // 从缓存中取结果
        String cacheKey = ParamWeight.K_WEIGHT > 0 ? algsName + "-" + sourceNo + "-k" : algsName + "-" + sourceNo + "-v";
        String value = EhCacheUtil.getInstance().get(cacheKey);
        if(value == null) {
            // 计时
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            // 计算
            ResultEntity resultEntity = KChartService.multiMixSimilarityComparision(sourceNo + ".txt", algorithms);
            resultEntity.sort(); // 排序
            // 打印时间
            double time = stopWatch.stop();
            System.out.println("Time: " + time + "s");
            // 输出结果
            ResultRank[] resultRanks = resultEntity.getRank();
            // 存入缓存
            StringBuffer stringBuffer = new StringBuffer();
            for(ResultRank resultRank : resultRanks) {
                stringBuffer.append(resultEntity.getPath()[resultRank.getTag()-1] + ",");
            }
            String cacheValue = stringBuffer.toString();
            cacheValue = cacheValue.substring(0, cacheValue.length() - 1);
            EhCacheUtil.getInstance().put(cacheKey, cacheValue);
            result = cacheValue;
        }
        else{
            result = value;
        }
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
