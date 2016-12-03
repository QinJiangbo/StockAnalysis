package com.whu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.whu.util.ServerConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Date: 20/11/2016
 * Author: qinjiangbo@github.io
 */
public class ImageAction extends ActionSupport{

    private String type;
    private String name;
    private InputStream imageStream;

    /**
     * 展示图片内容
     * @return
     */
    public String image() {
        if (name.equals("undefined") || name.equals("")) {
            imageStream = null;
            return SUCCESS;
        }
        String imagePath = ServerConstants.KCHART_IMAGES + name + ".txt-" + type + ".jpg";
        File file = new File(imagePath);
        try {
            imageStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }


}
