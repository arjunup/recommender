package com.brc.priceengine.recommender.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brc.priceengine.recommender.model.Product;
import com.brc.priceengine.recommender.model.SurveyData;

public class PriceRecommender {

	public List<Double> getChosenPriceForEachProduct(List<Product> productList, List<SurveyData> surveyDataList) {
		List<Double> chosenValuesPerProductList = new ArrayList<Double>();
		Collection<Map<Double,Integer>> pricingCountPerProduct = getPricingCountPerProdMap(productList, surveyDataList);
		for(Map<Double, Integer> map : pricingCountPerProduct) {
			Double chosenValue = 0.0; Integer chosenValueMaxCount = -1;
			for(Double price : map.keySet()) {
				Double currentValue = price;
				Integer chosenValueCount = map.get(price);
				
				// If price is 0, then it is not valid
				if (price != 0 && (chosenValueCount > chosenValueMaxCount || currentValue < chosenValue)) {	
					chosenValue = currentValue;
					chosenValueMaxCount = chosenValueCount;
				}
			}
			chosenValuesPerProductList.add(chosenValue);
		}
		return chosenValuesPerProductList;
	}

	public List<Double> recommendedPriceForEachProduct(List<Double> chosenValuesPerProduct, List<Product> productList) {
		List<Double> recommendedPriceList = new ArrayList<Double>();
		int index = 0;
		for(Product product : productList) {
			if(product.getSupply().equals("H") && product.getDemand().equals("H")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index));
			}else if(product.getSupply().equals("L") && product.getDemand().equals("L")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index) + (0.1 * chosenValuesPerProduct.get(index)));
			}else if(product.getSupply().equals("L") && product.getDemand().equals("H")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index) + (0.05 * chosenValuesPerProduct.get(index)));
			}else if(product.getSupply().equals("H") && product.getDemand().equals("L")) {
				recommendedPriceList.add(chosenValuesPerProduct.get(index) - (0.05 * chosenValuesPerProduct.get(index)));
			}else {				
				recommendedPriceList.add(null);
			}
			index++;
		}
		return recommendedPriceList;
	}
	
	public void reportOutput(List<Double> recommendedPriceList) {
		char c = 'A';
		Double zero = 0.0;
		for (Double recommendedPrice : recommendedPriceList) {
			
			if(zero.equals(recommendedPrice)) {
				System.out.println(c + " " + "Invalid Survey Data");
			}else if(recommendedPrice == null ){
				System.out.println(c + " " + "Invalid Supply/Demand parameters for Product Input data");
			}else {
				System.out.println(c + " " + recommendedPrice);
			}			
			c++;
		}
	}
	
	private Collection<Map<Double,Integer>> getPricingCountPerProdMap(List<Product> productList, List<SurveyData> surveyDataList) {
		Integer maxCount = -1;
		
		Collection<Map<Double,Integer>> pricingCountPerProduct = new ArrayList<Map<Double,Integer>>();
		
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
		return pricingCountPerProduct;
	}
	
}
