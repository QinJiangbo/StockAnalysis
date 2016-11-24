package com.whu.util;

import com.whu.kchart.KChart;
import com.whu.kchart.KData;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Date: 19/11/2016
 * Author: qinjiangbo@github.io
 */
public class ImageGenerator implements Runnable {

    private File file;

    public ImageGenerator(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        TextDataReader textDataReader = new TextDataReader();
        textDataReader.init(file);
        if (textDataReader.getHighPrice().length == 0) return;
        List<KData> list = new ArrayList<>();
        int list_size = textDataReader.getIssueDate().length;
        for (int i = 0; i < list_size; i++) {
            KData k = new KData(textDataReader.getStockName(), textDataReader.getIssueDate()[i],
                    textDataReader.getOpenPrice()[i], textDataReader.getHighPrice()[i], textDataReader.getLowPrice()[i],
                    textDataReader.getClosePrice()[i], textDataReader.getDealNumber()[i], textDataReader.getDealValue()[i]);
            list.add(k);
        }
        String beginDate = "0000-00-00";
        String endDate = "0000-00-00";
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            beginDate = sf1.format(sf2.parse(textDataReader.getIssueDate()[0]));
            Date tempDate = sf2.parse(textDataReader.getIssueDate()[list_size - 1]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(tempDate);
            cal.add(Calendar.DATE, 1);
            tempDate = cal.getTime();
            endDate = sf1.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        KChart chart = new KChart(list, beginDate, endDate);
        chart.savaAsImage(textDataReader.getKLineImagePath(), textDataReader.getDealNumberImagePath());
    }

}
