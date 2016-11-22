package com.whu.util;

/**
 * Date: 20/11/2016
 * Author: qinjiangbo@github.io
 */
public enum CompareType {
    K("k"), // 比较K线图
    V("v"); // 比较成交量

    private String name;

    private CompareType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
