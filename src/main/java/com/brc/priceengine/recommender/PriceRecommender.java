package com.brc.priceengine.recommender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceRecommender {

	private List<SurveyData> surveyDataList;

	private List<Product> productList;
	
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

	private double productAvgPrice(Product product) {
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

	public Map<String, Double> getAvgPriceList() {
		Map<String, Double> productAvgMap = new HashMap<String, Double>();
		for (Product product : this.productList) {
			double avgPrice = productAvgPrice(product);
			productAvgMap.put(product.getName(), avgPrice);
		}

		return productAvgMap;
	}

	private boolean isDataError(double surveyDataPrice, double avgPrice) {
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

	public List<Double> getChosenPriceForEachProduct(List<Product> productList, List<SurveyData> surveyDataList) {
		Integer maxCount = -1;
		
		Collection<Map<Double,Integer>> pricingCountPerProduct = new ArrayList<Map<Double,Integer>>();
		List<Double> chosenValuesPerProduct = new ArrayList<Double>();
		for (Product product : productList) {
			Map<Double, Integer> wordCountMap = new HashMap<Double, Integer>();
			for (SurveyData surveyData : surveyDataList) {
				if (product.getName().equals(surveyData.getProductName())) {
					if (!wordCountMap.containsKey(surveyData.getPrice())) {
						wordCountMap.put(surveyData.getPrice(), 0);
					}
					int count = wordCountMap.get(surveyData.getPrice()) + 1;
					if (count > maxCount) {
						maxCount = count;
					}
					wordCountMap.put(surveyData.getPrice(), count);
				}
			}
			pricingCountPerProduct.add(wordCountMap);
		}
		
		for(Map<Double, Integer> map : pricingCountPerProduct) {
			Double chosenValue = 0.0; Integer chosenValueMaxCount = -1;
			for(Double price : map.keySet()) {
				Double currentValue = price;
				Integer chosenValueCount = map.get(price);
				
				if (price != 0 && (chosenValueCount > chosenValueMaxCount || currentValue < chosenValue)) {
					chosenValue = currentValue;
					chosenValueMaxCount = chosenValueCount;
				}
			}
			chosenValuesPerProduct.add(chosenValue);
		}
		return chosenValuesPerProduct;
	}

	public List<Double> recommendedPriceForEachProduct(List<Double> chosenValuesPerProduct) {
		List<Double> recommendedPriceList = new ArrayList<Double>();
		int index = 0;
		for(Product product : this.productList) {
			if(product.getSupply().equals("H") && product.getDemand().equals("H")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index));
			}else if(product.getSupply().equals("L") && product.getDemand().equals("L")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index) + (0.1 * chosenValuesPerProduct.get(index)));
			}else if(product.getSupply().equals("L") && product.getDemand().equals("H")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index) + (0.05 * chosenValuesPerProduct.get(index)));
			}else if(product.getSupply().equals("H") && product.getDemand().equals("L")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index) - (0.05 * chosenValuesPerProduct.get(index)));
			}else {
				System.out.println("Invalid Supply/Demand parameters for Product Input data");
			}
			index++;
		}
		return recommendedPriceList;
	}
}
