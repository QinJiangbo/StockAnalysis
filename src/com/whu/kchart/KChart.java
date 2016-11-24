package com.whu.kchart;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;

import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;


public class KChart {

    List<KData> klist = new ArrayList<>();//����һ�������������ݵļ�����List
    String beginDate;//��ʼʱ�䣬��ʽ��yyyy-MM-dd
    String endDate;//����ʱ��

    public KChart(List<KData> list, String beginDate, String endDate) {
        this.klist = list;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public void clearList() {// ��ձ������ݵļ�����List
        klist.clear();
    }

    /**
     * ��ȡk����������
     *
     * @return
     */
    public OHLCSeries getOHLCSeries() {
        OHLCSeries series = new OHLCSeries("");// �߿������������У���ƱK��ͼ���ĸ����ݣ������ǿ����ߣ��ͣ���
        for (KData m : klist) {
            String date[] = (new SimpleDateFormat("yyyy-MM-dd").format(m.getIssueDate())).split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            series.add(new Day(day, month, year), m.getOpenPrice(), m.getHighPrice(), m.getLowPrice(), m.getClosePrice());
        }
        return series;
    }

    /**
     * ��ȡ��״ͼ�ɽ�������
     *
     * @return
     */
    public TimeSeries getTimeSeries() {
        TimeSeries series = new TimeSeries("");// ��Ӧʱ��ɽ�������
        for (KData m : klist) {
            String date[] = (new SimpleDateFormat("yyyy-MM-dd").format(m.getIssueDate())).split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            series.add(new Day(day, month, year), m.getDealNumber());
        }
        return series;
    }

    /**
     * ����K��ͼ
     * @return
     */
    public Plot drawKLineChart() {

        final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// ����K�����ݵ����ݼ�����������Ϊfinal������Ҫ�������ڲ��������õ�
        seriesCollection.addSeries(getOHLCSeries());

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
        candlestickRender.setUseOutlinePaint(true); // �����Ƿ�ʹ���Զ���ı߿��ߣ������Դ��ı߿��ߵ���ɫ�������й���Ʊ�г���ϰ��
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// ������ζ�K��ͼ�Ŀ�Ƚ����趨
        candlestickRender.setAutoWidthGap(0.001);// ���ø���K��ͼ֮��ļ��
        candlestickRender.setUpPaint(Color.RED);// ���ù�Ʊ���ǵ�K��ͼ��ɫ
        candlestickRender.setDownPaint(Color.GREEN);// ���ù�Ʊ�µ���K��ͼ��ɫ
        candlestickRender.setCandleWidth(4D);
        candlestickRender.setBaseOutlinePaint(new Color(200, 200, 200));
        candlestickRender.setSeriesCreateEntities(1, true);

        DateAxis xAxis = new DateAxis();// ����x�ᣬҲ����ʱ����
        xAxis.setAutoRange(false);// ���ò������Զ�����ʱ�䷶Χ
        try {
            xAxis.setRange(dateFormat.parse(beginDate), dateFormat.parse(endDate));// ����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
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
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.white);

        return plot;
    }

    /**
     * ���Ƴɽ���ͼ
     * @return
     */
    public Plot drawDealNumberChart() {
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();// �����ɽ������ݵļ���
        timeSeriesCollection.addSeries(getTimeSeries());

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
            xAxis.setRange(dateFormat.parse(beginDate),dateFormat.parse(endDate));//����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
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
        xAxis.setVisible(false);
        yAxis.setVisible(false);

        XYPlot plot = new XYPlot(timeSeriesCollection, xAxis, yAxis, xyBarRender);// �����ڶ�����ͼ���������Ҫ��ʱ��x����Ϊ��nullֵ����ΪҪ���һ����ͼ���������x��
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.white);
        return plot;
    }

    public boolean savaAsImage(String kPath, String vPath) {
        JFreeChart kChart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, drawKLineChart(), false);
        JFreeChart vChart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, drawDealNumberChart(), false);
        try {
            ChartUtilities.saveChartAsJPEG(new File(kPath), kChart, 600, 480);
            ChartUtilities.saveChartAsJPEG(new File(vPath), vChart, 600, 480);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}