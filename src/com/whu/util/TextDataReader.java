package com.whu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextDataReader {
	private String regEx = "[\u4e00-\u9fa5]";
	private Pattern pat = Pattern.compile(regEx);
	private File file;
	
	private String kLineImagePath = null;//K��ͼͼƬ�ļ���;
    private String dealNumberImagePath = null; //�ɽ���ͼƬ�ļ���
	private String stockName = null;//��Ʊ����
	private String[] issueDate = null;//����
	private double[] openPrice = null;//���̼�
	private double[] highPrice = null;//��߼�
	private double[] lowPrice = null;//��ͼ�
	private double[] closePrice = null;//���̼�
	private double[] dealNumber = null;//�ɽ���
	private double[] dealValue = null;//�ɽ���
	
	public void init(File file)
	{
		this.file = file;
		kLineImagePath = ServerConstants.KCHART_IMAGES + file.getName() + "-k.jpg";
        dealNumberImagePath = ServerConstants.KCHART_IMAGES + file.getName() + "-v.jpg";
		readText();
	}

    /**
     * ����ַ������Ƿ���������ַ�
     * @param str
     * @return
     */
	private boolean isChineseContained(String str)
	{
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if(matcher.find()){
			flg = true;
		}	
		return flg;
	}

    private void readText()
    {
		try{
			stockName = file.getName();
			InputStreamReader read = new InputStreamReader(
					new FileInputStream(file), "GBK");
            BufferedReader bf = new BufferedReader(read);
            String line;
            Queue<String> dataQueue = new LinkedList<>();
            Queue<String> openQueue = new LinkedList<>();
            Queue<String> highQueue = new LinkedList<>();
            Queue<String> lowQueue = new LinkedList<>();
            Queue<String> closeQueue = new LinkedList<>();
            Queue<String> volumeQueue = new LinkedList<>();
            Queue<String> turnOverQueue = new LinkedList<>();
            while((line=bf.readLine()) != null)
            {
            	if(isChineseContained(line))//����������������һ��;
            	{
            		line = bf.readLine();
            		continue;
            	}
                String data[] = line.split("\\t");
            	if (data.length == 7)
            	{
            		dataQueue.offer(data[0]);
            		openQueue.offer(data[1]);
                    highQueue.offer(data[2]);
            		lowQueue.offer(data[3]);
            		closeQueue.offer(data[4]);
            		volumeQueue.offer(data[5]);
            		turnOverQueue.offer(data[6]);
            	}
            }
            read.close();
            bf.close();
            
            issueDate = new String[dataQueue.size()];
            openPrice = new double[openQueue.size()];
            highPrice = new double[highQueue.size()];
            lowPrice = new double[lowQueue.size()];
            closePrice = new double[closeQueue.size()];
            dealNumber = new double[volumeQueue.size()];
            dealValue = new double[turnOverQueue.size()];
            String head;
            int tag = 0;
            while((head = dataQueue.poll())!= null)
            {
            	issueDate[tag] = head;
            	openPrice[tag] = Double.parseDouble(openQueue.poll());
            	highPrice[tag] = Double.parseDouble(highQueue.poll());
            	lowPrice[tag] = Double.parseDouble(lowQueue.poll());
            	closePrice[tag] = Double.parseDouble(closeQueue.poll());
            	dealNumber[tag] = Double.parseDouble(volumeQueue.poll());
            	dealValue[tag] = Double.parseDouble(turnOverQueue.poll());
            	tag++;
            }
		}catch (Exception e)
		{
            e.printStackTrace();
		}
	}
	
	public String getStockName() {return stockName;}
	public String getKLineImagePath() {return kLineImagePath;}
	public String getDealNumberImagePath() {return dealNumberImagePath;}
	public String[] getIssueDate(){return issueDate;}
	public double[] getOpenPrice() {return openPrice;}
	public double[] getHighPrice() {return highPrice;}
	public double[] getLowPrice() {return lowPrice;}
	public double[] getClosePrice() {return closePrice;}
	public double[] getDealNumber() {return dealNumber;}
	public double[] getDealValue() {return dealValue;}
}

