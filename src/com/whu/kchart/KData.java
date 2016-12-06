package com.whu.kchart;

import com.whu.util.ServerConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KData {

    private String stockName;//股票名称

    private Date issueDate;//日期

    private double openPrice;//开盘价

    private double highPrice;//最高价

    private double lowPrice;//最低价

    private double closePrice;//收盘价

    private double dealNumber;//成交量

    private double dealValue;//成交额

    public KData() {
    }

    public KData(String stockName, String issueDate, double openPrice, double highPrice, double lowPrice, double closePrice, double dealNumber, double dealValue) {
        this.stockName = stockName;
        SimpleDateFormat sf = new SimpleDateFormat(ServerConstants.DATE_FORMAT);
        try {
            this.issueDate = sf.parse(issueDate);
        } catch (ParseException e) {
            System.out.println("日期格式弄错了！");
        }
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.dealNumber = dealNumber;
        this.dealValue = dealValue;
    }

    public double getDealValue() {
        return dealValue;
    }

    public void setDealValue(double dealValue) {
        this.dealValue = dealValue;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(double dealNumber) {
        this.dealNumber = dealNumber;
    }

}
