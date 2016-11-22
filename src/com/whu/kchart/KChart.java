package com.whu.kchart;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;

import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

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

    public void show() {//��ͼ��ʾ
        JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, CombinePlot(), false);
        chart.setBackgroundPaint(Color.white);
        ChartFrame frame = new ChartFrame("ͼ", chart);
        frame.setBackground(Color.white);
        frame.pack();
        frame.setVisible(true);
    }

    public OHLCSeries getOHLS() {//��ȡk����������
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

    public TimeSeries getTime() {//��ȡ��״ͼ�ɽ�������
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

    public CombinedDomainXYPlot CombinePlot() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
        double highValue = Double.MIN_VALUE;// ����K�����ݵ��е����ֵ
        double minValue = Double.MAX_VALUE;// ����K�����ݵ��е���Сֵ
        double high2Value = Double.MIN_VALUE;// ���óɽ��������ֵ
        double min2Value = Double.MAX_VALUE;// ���óɽ��������ֵ

        final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// ����K�����ݵ����ݼ�����������Ϊfinal������Ҫ�������ڲ��������õ�
        seriesCollection.addSeries(getOHLS());

        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();// �����ɽ������ݵļ���
        timeSeriesCollection.addSeries(getTime());

        // ��ȡK�����ݵ����ֵ�����ֵ
        int seriesCount = seriesCollection.getSeriesCount();// һ���ж��ٸ����У�ĿǰΪһ��
        for (int i = 0; i < seriesCount; i++) {
            int itemCount = seriesCollection.getItemCount(i);// ÿһ�������ж��ٸ�������
            for (int j = 0; j < itemCount; j++) {
                if (highValue < seriesCollection.getHighValue(i, j)) {// ȡ��i�������еĵ�j������������ֵ
                    highValue = seriesCollection.getHighValue(i, j);
                }
                if (minValue > seriesCollection.getLowValue(i, j)) {// ȡ��i�������еĵ�j�����������Сֵ
                    minValue = seriesCollection.getLowValue(i, j);
                }
            }
        }
        // ��ȡ���ֵ�����ֵ
        int seriesCount2 = timeSeriesCollection.getSeriesCount();// һ���ж��ٸ����У�ĿǰΪһ��
        for (int i = 0; i < seriesCount2; i++) {
            int itemCount = timeSeriesCollection.getItemCount(i);// ÿһ�������ж��ٸ�������
            for (int j = 0; j < itemCount; j++) {
                if (high2Value < timeSeriesCollection.getYValue(i, j)) {// ȡ��i�������еĵ�j���������ֵ
                    high2Value = timeSeriesCollection.getYValue(i, j);
                }
                if (min2Value > timeSeriesCollection.getYValue(i, j)) {// ȡ��i�������еĵ�j���������ֵ
                    min2Value = timeSeriesCollection.getYValue(i, j);
                }
            }
        }

        final CandlestickRenderer candlestickRender = new CandlestickRenderer();// ����K��ͼ�Ļ�ͼ������������Ϊfinal������Ҫ�������ڲ��������õ�
        candlestickRender.setUseOutlinePaint(true); // �����Ƿ�ʹ���Զ���ı߿��ߣ������Դ��ı߿��ߵ���ɫ�������й���Ʊ�г���ϰ��
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// ������ζ�K��ͼ�Ŀ�Ƚ����趨
        candlestickRender.setAutoWidthGap(0.001);// ���ø���K��ͼ֮��ļ��
        candlestickRender.setUpPaint(Color.RED);// ���ù�Ʊ���ǵ�K��ͼ��ɫ
        candlestickRender.setDownPaint(Color.GREEN);// ���ù�Ʊ�µ���K��ͼ��ɫ
        candlestickRender.setCandleWidth(4D);
        candlestickRender.setBaseOutlinePaint(new Color(200, 200, 200));
        candlestickRender.setSeriesCreateEntities(1, true);

        DateAxis x1Axis = new DateAxis();// ����x�ᣬҲ����ʱ����
        x1Axis.setAutoRange(false);// ���ò������Զ�����ʱ�䷶Χ
        try {
            x1Axis.setRange(dateFormat.parse(beginDate),
                    dateFormat.parse(endDate));// ����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
        } catch (Exception e) {
            e.printStackTrace();
        }
        x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// ����ʱ������ʾ�Ĺ���������������������������������Щû�н��׵�����(�ܶ��˶���֪���д˷���)��ʹͼ�ο���ȥ����
        x1Axis.setAutoTickUnitSelection(true);// ���ò������Զ�ѡ��̶�ֵ
        x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// ���ñ�ǵ�λ��
        x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// ���ñ�׼��ʱ��̶ȵ�λ
        x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));// ����ʱ��̶ȵļ����һ������Ϊ��λ
        x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));// ������ʾʱ��ĸ�ʽ
        x1Axis.setVisible(false);
        NumberAxis y1Axis = new NumberAxis();// �趨y�ᣬ����������
        y1Axis.setAutoRange(false);// ����ʹ���Զ��趨��Χ
        y1Axis.setRange(minValue * 0.9, highValue * 1.1);// �趨y��ֵ�ķ�Χ�������ֵҪ��һЩ�������ֵҪ��һЩ������ͼ�ο�����������Щ
        y1Axis.setTickUnit(new NumberTickUnit(
                (highValue * 1.1 - minValue * 0.9) / 10));// ���ÿ̶���ʾ���ܶ�
        XYPlot plot1 = new XYPlot(seriesCollection, x1Axis, y1Axis,
                candlestickRender);// ���û�ͼ�������
        plot1.setDomainGridlinesVisible(false);
        plot1.setRangeGridlinesVisible(false);
        plot1.setBackgroundPaint(Color.white);

        XYLineAndShapeRenderer line1 = new XYLineAndShapeRenderer(true, false);//5�վ���
        line1.setSeriesPaint(1, Color.red);
        plot1.setDataset(1, timeSeriesCollection);
        plot1.setRenderer(1, line1);

        XYBarRenderer xyBarRender = new XYBarRenderer() {
            private static final long serialVersionUID = 1L;// Ϊ�˱�����־�����Ϣ�����趨��ֵ

            public Paint getItemPaint(int i, int j) {// �����ڲ������������յĳɽ�������ͼ����ɫ��K��ͼ����ɫ����һ��
                if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {// ���̼۸��ڿ��̼ۣ���Ʊ���ǣ�ѡ�ù�Ʊ���ǵ���ɫ
                    return candlestickRender.getUpPaint();
                } else {
                    return candlestickRender.getDownPaint();
                }
            }
        };

        xyBarRender.setMargin(0.1);// ��������ͼ֮��ļ��
        NumberAxis y2Axis = new NumberAxis();// ����Y�ᣬΪ��ֵ,��������ã��ο������y������
        y2Axis.setAutoRange(false);
        y2Axis.setRange(min2Value * 0.9, high2Value * 1.1);
        y2Axis.setTickUnit(new NumberTickUnit(
                (high2Value * 1.1 - min2Value * 0.9) / 4));
        y2Axis.setVisible(false);
        y1Axis.setVisible(false);
        XYPlot plot2 = new XYPlot(timeSeriesCollection, null, y2Axis, xyBarRender);// �����ڶ�����ͼ���������Ҫ��ʱ��x����Ϊ��nullֵ����ΪҪ���һ����ͼ���������x��
        plot2.setDomainGridlinesVisible(false);
        plot2.setRangeGridlinesVisible(false);
        plot2.setBackgroundPaint(Color.white);
        plot1.setOutlineVisible(true);
        plot2.setOutlineVisible(true);

        CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);// ����һ��ǡ��������ͼ�����������x��Ϊ������
        combineddomainxyplot.add(plot1, 2);// ���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������2/3
        combineddomainxyplot.add(plot2, 1);// ���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������1/3
        combineddomainxyplot.setGap(10);// ��������ͼ���������֮��ļ���ռ�
        combineddomainxyplot.setDomainGridlinesVisible(false);
        return combineddomainxyplot;
    }

    /**
     * ����K��ͼ
     * @return
     */
    public Plot drawKLineChart() {

        final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();// ����K�����ݵ����ݼ�����������Ϊfinal������Ҫ�������ڲ��������õ�
        seriesCollection.addSeries(getOHLS());

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
        timeSeriesCollection.addSeries(getTime());

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

    public boolean savaAsPic(String kPath, String vPath) {
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