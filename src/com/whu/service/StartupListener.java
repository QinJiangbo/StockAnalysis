package com.whu.service;

import com.whu.util.ServerConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * Date: 21/11/2016
 * Author: qinjiangbo@github.io
 */
public class StartupListener implements ServletContextListener {

    /**
     * 项目启动时执行的操作
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String LOGO = "$$\\   $$\\  $$$$$$\\  $$\\                            $$\\     \n" +
                "$$ | $$  |$$  __$$\\ $$ |                           $$ |    \n" +
                "$$ |$$  / $$ /  \\__|$$$$$$$\\   $$$$$$\\   $$$$$$\\ $$$$$$\\   \n" +
                "$$$$$  /  $$ |      $$  __$$\\  \\____$$\\ $$  __$$\\\\_$$  _|  \n" +
                "$$  $$<   $$ |      $$ |  $$ | $$$$$$$ |$$ |  \\__| $$ |    \n" +
                "$$ |\\$$\\  $$ |  $$\\ $$ |  $$ |$$  __$$ |$$ |       $$ |$$\\ \n" +
                "$$ | \\$$\\ \\$$$$$$  |$$ |  $$ |\\$$$$$$$ |$$ |       \\$$$$  |\n" +
                "\\__|  \\__| \\______/ \\__|  \\__| \\_______|\\__|        \\____/ \n";
        System.out.println(LOGO);
        ServletContext context = servletContextEvent.getServletContext();
        InputStream inputStream = context.getResourceAsStream("/WEB-INF/classes/com/whu/config/server.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String STOCK_DATA = properties.getProperty("STOCK_DATA");
        String KCHART_IMAGES = properties.getProperty("KCHART_IMAGES");
        String KCHART_COMPRESSED_IMAGES = properties.getProperty("KCHART_COMPRESSED_IMAGES");
        // 设置服务器属性值
        if(STOCK_DATA != null && STOCK_DATA != "") {
            ServerConstants.STOCK_DATA = STOCK_DATA;
        }
        if(KCHART_IMAGES != null && KCHART_IMAGES != "") {
            ServerConstants.KCHART_IMAGES = KCHART_IMAGES;
        }
        if(KCHART_COMPRESSED_IMAGES != null && KCHART_COMPRESSED_IMAGES != "") {
            ServerConstants.KCHART_COMPRESSED_IMAGES = KCHART_COMPRESSED_IMAGES;
        }
    }

    /**
     * 项目停止时执行的操作
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * 修改静态常量
     * @param clazz
     * @param name
     * @param value
     */
    private void changeFinalFields(Class clazz, String name, String value) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getField(name);
        /* 将字段的访问权限设为true: 即去除private修饰符的影响 */
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        /* 去除final修饰符的影响，将字段设为可修改的 */
        modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
    }
}
