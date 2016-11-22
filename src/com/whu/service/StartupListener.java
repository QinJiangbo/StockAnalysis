package com.whu.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    }

    /**
     * 项目停止时执行的操作
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
