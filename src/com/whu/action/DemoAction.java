package com.whu.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Date: 24/11/2016
 * Author: qinjiangbo@github.io
 */
public class DemoAction extends ActionSupport {

    private String name;
    private double age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String test() {
        System.out.println(name + "@" + age);
        return SUCCESS;
    }
}
