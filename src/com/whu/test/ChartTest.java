package com.whu.test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;//ʱ���ʽ

import org.jfree.data.time.*;
import org.jfree.data.time.Day;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.*;

public class ChartTest {
    public static void main(String[] args) {
        Plot plot = drawKLineChart();
//        Plot plot = drawDealNumberChart();
        savaAsPic("/Users/Richard/Documents/KCharts/SIFTTest/test-k.jpg", plot);
    }

    /**
     * ����K��ͼ
     * @return
     */
    public static Plot drawKLineChart() {
        OHLCSeries series = new OHLCSeries("");// �߿������������У���ƱK��ͼ���ĸ����ݣ������ǿ����ߣ��ͣ���
        series.add(new Day(28, 9, 2007), 9.2, 9.58, 9.16, 9.34);
        series.add(new Day(27, 9, 2007), 8.9, 9.06, 8.83, 8.96);
        series.add(new Day(26, 9, 2007), 9.0, 9.1, 8.82, 9.04);
        series.add(new Day(25, 9, 2007), 9.25, 9.33, 8.88, 9.00);
        series.add(new Day(24, 9, 2007), 9.05, 9.50, 8.91, 9.25);
        series.add(new Day(21, 9, 2007), 8.68, 9.05, 8.40, 9.00);
        series.add(new Day(20, 9, 2007), 8.68, 8.95, 8.50, 8.69);
        series.add(new Day(19, 9, 2007), 8.80, 8.94, 8.50, 8.66);
        series.add(new Day(18, 9, 2007), 8.88, 9.17, 8.69, 8.80);
        series.add(new Day(17, 9, 2007), 8.26, 8.98, 8.15, 8.89);
        series.add(new Day(14, 9, 2007), 8.44, 8.45, 8.13, 8.33);
        series.add(new Day(13, 9, 2007), 8.13, 8.46, 7.97, 8.42);
        series.add(new Day(12, 9, 2007), 8.2, 8.4, 7.81, 8.13);
        series.add(new Day(11, 9, 2007), 9.0, 9.0, 8.1, 8.24);
        series.add(new Day(10, 9, 2007), 8.6, 9.03, 8.40, 8.95);
        series.add(new Day(7, 9, 2007), 8.89, 9.04, 8.70, 8.73);
        series.add(new Day(6, 9, 2007), 8.4, 9.08, 8.33, 8.88);
        series.add(new Day(5, 9, 2007), 8.2, 8.74, 8.17, 8.36);
        series.add(new Day(4, 9, 2007), 7.7, 8.46, 7.67, 8.27);
        series.add(new Day(3, 9, 2007), 7.5, 7.8, 7.48, 7.69);
        series.add(new Day(31, 8, 2007), 7.4, 7.6, 7.28, 7.43);
        series.add(new Day(30, 8, 2007), 7.42, 7.56, 7.31, 7.40);
        series.add(new Day(29, 8, 2007), 7.42, 7.66, 7.22, 7.33);
        series.add(new Day(28, 8, 2007), 7.31, 7.70, 7.15, 7.56);
        series.add(new Day(27, 8, 2007), 7.05, 7.46, 7.02, 7.41);
        series.add(new Day(24, 8, 2007), 7.05, 7.09, 6.90, 6.99);
        series.add(new Day(23, 8, 2007), 7.12, 7.16, 7.00, 7.03);
        series.add(new Day(22, 8, 2007), 6.96, 7.15, 6.93, 7.11);
        series.add(new Day(21, 8, 2007), 7.10, 7.15, 7.02, 7.07);
        series.add(new Day(20, 8, 2007), 7.02, 7.19, 6.94, 7.14);

        final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// ����K�����ݵ����ݼ�����������Ϊfinal������Ҫ�������ڲ��������õ�
        seriesCollection.addSeries(series);

        // ��ȡK�����ݵ����ֵ�����ֵ
        double maxValue = Double.MIN_VALUE;// ����K�����ݵ��е����ֵ
        double minValue = Double.MAX_VALUE;// ����K�����ݵ��е���Сֵ
        int seriesCount = seriesCollection.getSeriesCount();// һ���ж��ٸ����У�ĿǰΪһ��
        for (int i = 0; i < seriesCount; i++) {
            int itemCount = seriesCollection.getItemCount(i);// ÿһ�������ж��ٸ�������
            for (int j = 0; j < itemCount; j++) {
                if (maxValue < seriesCollection.getHighValue(i, j)) {// ȡ��i�������еĵ�j������������ֵ
                    maxValue = seriesCollection.getHighValue(i, j);
                }
                if (minValue > seriesCollection.getLowValue(i, j)) {// ȡ��i�������еĵ�j�����������Сֵ
                    minValue = seriesCollection.getLowValue(i, j);
                }
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
        final CandlestickRenderer candlestickRender = new CandlestickRenderer();// ����K��ͼ�Ļ�ͼ������������Ϊfinal������Ҫ�������ڲ��������õ�
        candlestickRender.setUseOutlinePaint(false); // �����Ƿ�ʹ���Զ���ı߿��ߣ������Դ��ı߿��ߵ���ɫ�������й���Ʊ�г���ϰ��
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// ������ζ�K��ͼ�Ŀ�Ƚ����趨
        candlestickRender.setAutoWidthGap(0.001);// ���ø���K��ͼ֮��ļ��
        candlestickRender.setUpPaint(Color.RED);// ���ù�Ʊ���ǵ�K��ͼ��ɫ
        candlestickRender.setDownPaint(Color.GREEN);// ���ù�Ʊ�µ���K��ͼ��ɫ
        DateAxis xAxis = new DateAxis();// ����x�ᣬҲ����ʱ����
        xAxis.setAutoRange(false);// ���ò������Զ�����ʱ�䷶Χ
        try {
            xAxis.setRange(dateFormat.parse("2007-08-20"), dateFormat.parse("2007-09-29"));// ����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
        } catch (Exception e) {
            e.printStackTrace();
        }
        xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// ����ʱ������ʾ�Ĺ���������������������������������Щû�н��׵�����(�ܶ��˶���֪���д˷���)��ʹͼ�ο���ȥ����
        xAxis.setAutoTickUnitSelection(false);// ���ò������Զ�ѡ��̶�ֵ
        xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// ���ñ�ǵ�λ��
        xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// ���ñ�׼��ʱ��̶ȵ�λ
        xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));// ����ʱ��̶ȵļ����һ������Ϊ��λ
        xAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));// ������ʾʱ��ĸ�ʽ
        NumberAxis yAxis = new NumberAxis();// �趨y�ᣬ����������
        yAxis.setAutoRange(false);// ����ʹ���Զ��趨��Χ
        yAxis.setRange(minValue * 0.9, maxValue * 1.1);// �趨y��ֵ�ķ�Χ�������ֵҪ��һЩ�������ֵҪ��һЩ������ͼ�ο�����������Щ
        yAxis.setTickUnit(new NumberTickUnit((maxValue * 1.1 - minValue * 0.9) / 10));// ���ÿ̶���ʾ���ܶ�
        xAxis.setVisible(false);
        yAxis.setVisible(false);
        XYPlot plot = new XYPlot(seriesCollection, xAxis, yAxis, candlestickRender);// ���û�ͼ�������

        return plot;
    }

    /**
     * ���Ƴɽ���ͼ
     * @return
     */
    public static Plot drawDealNumberChart() {
        TimeSeries series = new TimeSeries("");// ��Ӧʱ��ɽ�������
        series.add(new Day(28, 9, 2007), 260659400 / 100);
        series.add(new Day(27, 9, 2007), 119701900 / 100);
        series.add(new Day(26, 9, 2007), 109719000 / 100);
        series.add(new Day(25, 9, 2007), 178492400 / 100);
        series.add(new Day(24, 9, 2007), 269978500 / 100);
        series.add(new Day(21, 9, 2007), 361042300 / 100);
        series.add(new Day(20, 9, 2007), 173912600 / 100);
        series.add(new Day(19, 9, 2007), 154622600 / 100);
        series.add(new Day(18, 9, 2007), 200661600 / 100);
        series.add(new Day(17, 9, 2007), 312799600 / 100);
        series.add(new Day(14, 9, 2007), 141652900 / 100);
        series.add(new Day(13, 9, 2007), 221260400 / 100);
        series.add(new Day(12, 9, 2007), 274795400 / 100);
        series.add(new Day(11, 9, 2007), 289287300 / 100);
        series.add(new Day(10, 9, 2007), 289063600 / 100);
        series.add(new Day(7, 9, 2007), 351575300 / 100);
        series.add(new Day(6, 9, 2007), 451357300 / 100);
        series.add(new Day(5, 9, 2007), 442421200 / 100);
        series.add(new Day(4, 9, 2007), 671942600 / 100);
        series.add(new Day(3, 9, 2007), 349647800 / 100);
        series.add(new Day(31, 8, 2007), 225339300 / 100);
        series.add(new Day(30, 8, 2007), 160048200 / 100);
        series.add(new Day(29, 8, 2007), 247341700 / 100);
        series.add(new Day(28, 8, 2007), 394975400 / 100);
        series.add(new Day(27, 8, 2007), 475797500 / 100);
        series.add(new Day(24, 8, 2007), 297679500 / 100);
        series.add(new Day(23, 8, 2007), 191760600 / 100);
        series.add(new Day(22, 8, 2007), 232570200 / 100);
        series.add(new Day(21, 8, 2007), 215693200 / 100);
        series.add(new Day(20, 8, 2007), 200287500 / 100);
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();// �����ɽ������ݵļ���
        timeSeriesCollection.addSeries(series);

        // ��ȡ���ֵ�����ֵ
        double maxValue = Double.MIN_VALUE;// ���óɽ��������ֵ
        double minValue = Double.MAX_VALUE;// ���óɽ��������ֵ
        int seriesCount2 = timeSeriesCollection.getSeriesCount();// һ���ж��ٸ����У�ĿǰΪһ��
        for (int i = 0; i < seriesCount2; i++) {
            int itemCount = timeSeriesCollection.getItemCount(i);// ÿһ�������ж��ٸ�������
            for (int j = 0; j < itemCount; j++) {
                if (maxValue < timeSeriesCollection.getYValue(i, j)) {// ȡ��i�������еĵ�j���������ֵ
                    maxValue = timeSeriesCollection.getYValue(i, j);
                }
                if (minValue > timeSeriesCollection.getYValue(i, j)) {// ȡ��i�������еĵ�j���������ֵ
                    minValue = timeSeriesCollection.getYValue(i, j);
                }
            }
        }

        XYBarRenderer xyBarRender = new XYBarRenderer() {
            private static final long serialVersionUID = 1L;// Ϊ�˱�����־�����Ϣ�����趨��ֵ
            public Paint getItemPaint(int i, int j) {// �����ڲ������������յĳɽ�������ͼ����ɫ��K��ͼ����ɫ����һ��
                return Color.BLUE;
            }
        };
        xyBarRender.setMargin(0.1);// ��������ͼ֮��ļ��

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
        DateAxis xAxis=new DateAxis();//����x�ᣬҲ����ʱ����
        xAxis.setAutoRange(false);//���ò������Զ�����ʱ�䷶Χ
        try{
            xAxis.setRange(dateFormat.parse("2007-08-20"),dateFormat.parse("2007-09-29"));//����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
        }catch(Exception e){
            e.printStackTrace();
        }
        xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//����ʱ������ʾ�Ĺ���������������������������������Щû�н��׵�����(�ܶ��˶���֪���д˷���)��ʹͼ�ο���ȥ����
        xAxis.setAutoTickUnitSelection(false);//���ò������Զ�ѡ��̶�ֵ
        xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//���ñ�ǵ�λ��
        xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//���ñ�׼��ʱ��̶ȵ�λ
        xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//����ʱ��̶ȵļ����һ������Ϊ��λ
        xAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//������ʾʱ��ĸ�ʽ

        NumberAxis yAxis = new NumberAxis();// ����Y�ᣬΪ��ֵ,��������ã��ο������y������
        yAxis.setAutoRange(false);
        yAxis.setRange(minValue * 0.9, maxValue * 1.1);
        yAxis.setTickUnit(new NumberTickUnit((maxValue * 1.1 - minValue * 0.9) / 4));
        XYPlot plot = new XYPlot(timeSeriesCollection, xAxis, yAxis, xyBarRender);// �����ڶ�����ͼ���������Ҫ��ʱ��x����Ϊ��nullֵ����ΪҪ���һ����ͼ���������x��
        xAxis.setVisible(false);
        yAxis.setVisible(false);
        return plot;
    }

    /**
     * ����ΪͼƬ
     * @param path
     * @param plot
     * @return
     */
    public static boolean savaAsPic(String path, Plot plot) {
        JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        try {
            ChartUtilities.saveChartAsJPEG(new File(path), chart, 600, 480);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}