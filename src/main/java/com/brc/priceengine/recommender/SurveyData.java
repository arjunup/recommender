package com.brc.priceengine.recommender;

public class SurveyData {
	
	private String productName;
	private String competitorName;
	private double price;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String product) {
		this.productName = product;
	}
	public String getCompetitorName() {
		return competitorName;
	}
	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}	
}
