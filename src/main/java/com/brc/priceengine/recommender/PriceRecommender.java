package com.brc.priceengine.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceRecommender {

	private List<SurveyData> surveyDataList;

	private List<Product> productList;

	private List<Double> priceRecommenderList;

	private List<Double> cleanedCompetitorPriceList;

	private double productAvgPrice(Product product) {
		double priceTotal = 0;
		double avgPrice = 0;
		int i = 0;
		for (SurveyData surveyData : surveyDataList) {
			if (product.getName().equals(surveyData.getProductName())) {
				// System.out.println("priceTotal = " + priceTotal);
				priceTotal += surveyData.getPrice();
				i++;
				avgPrice = priceTotal / i;
				// System.out.println("avgPrice = " + avgPrice);
			}
		}

		return avgPrice;
	}

	public Map<String, Double> getAvgPriceList() {
		Map<String, Double> productAvgMap = new HashMap<String, Double>();
		for (Product product : this.productList) {
			double avgPrice = productAvgPrice(product);
			productAvgMap.put(product.getName(), avgPrice);
		}

		return productAvgMap;
	}

	private boolean isDataError(double surveyDataPrice, double avgPrice) {
		// System.out.println("surveyDataPrice = " + surveyDataPrice);
		// System.out.println("avgPrice * 0.5 " + avgPrice * 0.5);
		return (surveyDataPrice > (avgPrice + avgPrice * 0.5)) ? true : false;
	}

	private boolean isPromoOffer(double surveyDataPrice, double avgPrice) {
		return (surveyDataPrice < (avgPrice - avgPrice * 0.5)) ? true : false;
	}

	public List<SurveyData> getCleanedSurveyData(Map<String, Double> avgPriceList) {
		List<SurveyData> cleanedSurveyDataList = new ArrayList<SurveyData>();
		for (String key : avgPriceList.keySet()) {
			for (SurveyData surveyData : surveyDataList) {
				if (surveyData.getProductName().equals(key)) {
					if (isDataError(surveyData.getPrice(), avgPriceList.get(key))) {
						surveyData.setPrice(0);
					} else if (isPromoOffer(surveyData.getPrice(), avgPriceList.get(key))) {
						surveyData.setPrice(0);
					}
					cleanedSurveyDataList.add(surveyData);
				}
			}
		}
		return cleanedSurveyDataList;
	}

	public Map<Double, Integer> getChosenPriceForEachProduct(List<Product> productList, List<SurveyData> surveyDataList) {
		Integer maxCount = -1;
		Map<Double, Integer> wordCount = new HashMap<Double, Integer>();
		for (Product product : productList) {
			for (SurveyData surveyData : surveyDataList) {
				if (product.getName().equals(surveyData.getProductName())) {
					if (!wordCount.containsKey(surveyData.getPrice())) {
						wordCount.put(surveyData.getPrice(), 0);
					}
					int count = wordCount.get(surveyData.getPrice()) + 1;
					if (count > maxCount) {
						maxCount = count;
					}
					wordCount.put(surveyData.getPrice(), count);
				}
			}
		}
		return wordCount;
	}

	private double recommendedPriceBasedonChosenPrice() {
		return 0;

	}

	public List<SurveyData> getSurveyDataList() {
		return surveyDataList;
	}

	public void setSurveyDataList(List<SurveyData> surveyDataList) {
		this.surveyDataList = surveyDataList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
