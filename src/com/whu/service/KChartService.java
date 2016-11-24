package com.whu.service;

import com.whu.algorithms.KChartThread;
import com.whu.entity.ParamWeight;
import com.whu.entity.ResultEntity;
import com.whu.util.*;

import java.util.*;

/**
 * Date: 20/11/2016
 * Author: qinjiangbo@github.io
 */
public class KChartService {

    /**
     * 通过算法比较图片相似度
     * @param sourceImage 源图片
     * @param type 比较类型
     * @return 最相似图片
     */
    public static ResultEntity compareSimilarity(String sourceImage, CompareType type, Algorithms algorithms) {
        String[] imageNames = FileNameUtil.listImageNames();
        int size = imageNames.length -1;
        // 判断用户输入的图片信息是否有效
        boolean flag = false;
        for (String name: imageNames) {
            if(name.equals(sourceImage)) {
                flag = true;
            }
        }
        if(flag == false) {
            return null;
        }

        String[] imagePath = new String[size];
        // 结果实体
        ResultEntity resultEntity = new ResultEntity(size);
        List<Thread> threadList = new ArrayList<>();
        int groupNo = 1;
        for (String targetImage: imageNames) {
            if(!targetImage.equals(sourceImage)) {
                String image0 = ServerConstants.KCHART_IMAGES + sourceImage + "-" + type.getName() + ".jpg";
                String imageI = ServerConstants.KCHART_IMAGES + targetImage + "-" + type.getName() + ".jpg";
                imagePath[groupNo-1] = targetImage.substring(0, targetImage.indexOf(".txt"));
                try {
                    Class clazz = algorithms.getClazz();
                    KChartThread thread = (KChartThread) clazz.newInstance();
                    threadList.add(thread);
                    thread.start(groupNo++, resultEntity.getSimilarity(), image0, imageI);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < size - 1; ++i) {
            while (threadList.get(i).isAlive()); // 直到这个线程结束
        }
        resultEntity.setPath(imagePath);
        return resultEntity;
    }

    /**
     * 通过一个算法综合比较图片相似度
     * @param sourceImage 源图片
     * @return
     */
    public static ResultEntity mixSimilarityComparation(String sourceImage, Algorithms algorithms) {
        ResultEntity kResultEntity = compareSimilarity(sourceImage, CompareType.K, algorithms);
        ResultEntity vResultEntity = compareSimilarity(sourceImage, CompareType.V, algorithms);
        if(kResultEntity == null) {
            return null;
        }
        int size = kResultEntity.getSize();
        ResultEntity resultEntity = new ResultEntity(size);
        resultEntity.setPath(kResultEntity.getPath());
        double[] similarity = new double[size];
        double[] kSimilarity = kResultEntity.getSimilarity();
        double[] vSimilarity = vResultEntity.getSimilarity();
        for(int i = 0; i<size; i++) {
            similarity[i] = ParamWeight.K_WEIGHT * kSimilarity[i] + (1 - ParamWeight.K_WEIGHT) * vSimilarity[i];
        }
        resultEntity.setSimilarity(similarity);
        return resultEntity;
    }

    /**
     * 多个算法间取综合
     *
     * @param sourceImage
     * @param map
     * @return
     */
    public static ResultEntity multiMixSimilarityComparation(String sourceImage, Map<Algorithms, Double> map) {
        List<ResultEntity> resultEntities = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        Set<Map.Entry<Algorithms, Double>> entrySet = map.entrySet();
        for (Map.Entry<Algorithms, Double> entry : entrySet) {
            resultEntities.add(mixSimilarityComparation(sourceImage, entry.getKey()));
            weights.add(entry.getValue());
        }

        int size = resultEntities.get(0).getSize();
        ResultEntity resultEntity = new ResultEntity(size);
        resultEntity.setPath(resultEntities.get(0).getPath());
        double[] similarity = new double[size];
        for (int i = 0; i < resultEntities.size(); i++) {
            for (int j = 0; j < size; j++) {
                similarity[j] += resultEntities.get(i).getSimilarity()[j] * weights.get(i);
            }
        }
        resultEntity.setSimilarity(similarity);
        return resultEntity;
    }

    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        ResultEntity resultEntity = mixSimilarityComparation("SZ300122.txt", Algorithms.MULTIPHASH);
//        if(resultEntity != null) {
//            int tag = resultEntity.getRank()[0].getTag();
//            System.out.println(resultEntity.getRank()[0].getSimilarity());
//            System.out.println(tag-1);
//            System.out.println(resultEntity.getPath()[tag-1]);
//            long end = System.currentTimeMillis();
//            System.out.println("Time Consumed: " + (end - start) / 1000 + "s");
//            System.out.println("Comparision Completed!");
//        }else{
//            System.out.println("The source image name is wrong!");
//        }
        Map<Algorithms, Double> map = new HashMap<>();
        map.put(Algorithms.MULTIPHASH, 1.0);
        ResultEntity resultEntity = multiMixSimilarityComparation("SZ300122.txt", map);
        resultEntity.sort();
        int tag = resultEntity.getRank()[0].getTag();
        System.out.println(resultEntity.getRank()[0].getSimilarity());
        System.out.println(tag - 1);
        System.out.println(resultEntity.getPath()[tag-1]);
    }
}
