package com.whu.service;

import com.whu.util.ImageUtil;
import com.whu.util.ServerConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
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
        String DATE_FORMAT = properties.getProperty("DATE_FORMAT");
        // 设置服务器属性值
        if (STOCK_DATA != null && !STOCK_DATA.equals("")) {
            ServerConstants.STOCK_DATA = STOCK_DATA;
        }
        if (KCHART_IMAGES != null && !KCHART_IMAGES.equals("")) {
            ServerConstants.KCHART_IMAGES = KCHART_IMAGES;
        }
        if (KCHART_COMPRESSED_IMAGES != null && !KCHART_COMPRESSED_IMAGES.equals("")) {
            ServerConstants.KCHART_COMPRESSED_IMAGES = KCHART_COMPRESSED_IMAGES;
        }
        if (DATE_FORMAT != null && !DATE_FORMAT.equals("")) {
            ServerConstants.DATE_FORMAT = DATE_FORMAT;
        }

        File imageDir = new File(ServerConstants.KCHART_IMAGES);
        File compressDir = new File(ServerConstants.KCHART_COMPRESSED_IMAGES);
        List<Thread> threadList = new LinkedList<>();
        if (imageDir.listFiles().length <= 1) {
            // 启动生成图片过程
            threadList = ImageUtil.generate();
        }
        for (Thread thread : threadList) {
            while (thread.isAlive());
        }
        if (compressDir.listFiles().length <= 1) {
            // 启动压缩图片过程
            ImageUtil.compress();
        }
        System.out.println("Images initialized completed!");
    }

    /**
     * 项目停止时执行的操作
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * 修改静态常量,对于基本类型无效
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
