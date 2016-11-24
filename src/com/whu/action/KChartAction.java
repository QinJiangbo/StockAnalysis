package com.whu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.whu.util.ServerConstants;

import java.io.File;

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
        if (imageDir.listFiles() == null) {
            // 启动生成图片的过程

        }
        result = "Hello Kitty!";
        return SUCCESS;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
