package com.brc.priceengine.recommender.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brc.priceengine.recommender.model.Product;
import com.brc.priceengine.recommender.model.SurveyData;

public class PriceRecommenderHelper {
	
	public Map<String, Double> getProductAvgPriceMap(List<Product> productList, List<SurveyData> surveyDataList) {
		Map<String, Double> productAvgMap = new HashMap<String, Double>();
		for (Product product : productList) {
			double avgPrice = productAvgPrice(product, surveyDataList);
			productAvgMap.put(product.getName(), avgPrice);
		}

		return productAvgMap;
	}
	
	public List<SurveyData> getCleanedSurveyData(Map<String, Double> productAvgMap, List<SurveyData> surveyDataList) {
		List<SurveyData> cleanedSurveyDataList = new ArrayList<SurveyData>();
		for (String key : productAvgMap.keySet()) {
			for (SurveyData surveyData : surveyDataList) {
				if (surveyData.getProductName().equals(key)) {
					if (isDataError(surveyData.getPrice(), productAvgMap.get(key))) {
						surveyData.setPrice(0);
					} else if (isPromoOffer(surveyData.getPrice(), productAvgMap.get(key))) {
						surveyData.setPrice(0);
					}
					cleanedSurveyDataList.add(surveyData);
				}
			}
		}
		return cleanedSurveyDataList;
	}

	protected double productAvgPrice(Product product, List<SurveyData> surveyDataList ) {
		double priceTotal = 0;
		double avgPrice = 0;
		int i = 0; 
		for (SurveyData surveyData : surveyDataList) {
			if (product.getName().equals(surveyData.getProductName())) {
				priceTotal += surveyData.getPrice();
				i++;
				avgPrice = priceTotal / i;
			}
		}

		return avgPrice;
	}
	
	protected boolean isDataError(double surveyDataPrice, double avgPrice) {
		return (surveyDataPrice > (avgPrice + avgPrice * 0.5)) ? true : false;
	}

	protected boolean isPromoOffer(double surveyDataPrice, double avgPrice) {
		return (surveyDataPrice < (avgPrice - avgPrice * 0.5)) ? true : false;
	}
	
	
}
