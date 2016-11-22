package com.whu.entity;

public class Rank {
	
	private int tag; // 标记,标识它的位置
	private double similarity; // 相似度
	
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	
}
