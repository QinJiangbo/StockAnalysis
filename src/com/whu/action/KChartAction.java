package com.whu.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.whu.util.ImageUtil;
import com.whu.util.ServerConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 20/11/2016
 * Author: qinjiangbo@github.io
 */
public class KChartAction extends ActionSupport{

    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
