package com.brc.priceengine.recommender;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.brc.priceengine.recommender.helper.InputHelper;
import com.brc.priceengine.recommender.helper.PriceRecommender;
import com.brc.priceengine.recommender.helper.PriceRecommenderHelper;
import com.brc.priceengine.recommender.model.Product;
import com.brc.priceengine.recommender.model.SurveyData;

/**
 * 
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		System.out.println("Enter number of products followed by each product name and the product's supply and demand parameters and then enter the Surveyed data "
				+ "that contains Product code, Competitor and Price.");
		
		InputHelper inputHelper = new InputHelper();
		List<Product> productList = inputHelper.processProductInput();
		List<SurveyData> surveyDataList = inputHelper.processSurveyDataInput();

		PriceRecommenderHelper priceRecommenderhelper = new PriceRecommenderHelper();
		priceRecommenderhelper.setSurveyDataList(surveyDataList);
		priceRecommenderhelper.setProductList(productList);
		
		Map<String, Double> productAvgPriceMap = priceRecommenderhelper.getAvgPriceList();
		List<SurveyData> cleanedSurveyDataList = priceRecommenderhelper.getCleanedSurveyData(productAvgPriceMap);
		
		PriceRecommender priceRecommender = new PriceRecommender();
		List<Double> chosenPriceList = priceRecommender.getChosenPriceForEachProduct(productList,
				cleanedSurveyDataList);
		List<Double> recommendedPriceList = priceRecommender.recommendedPriceForEachProduct(chosenPriceList, productList);
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
}
