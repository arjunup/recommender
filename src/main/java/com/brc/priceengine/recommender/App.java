package com.brc.priceengine.recommender;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.brc.priceengine.recommender.helper.InputHelper;
import com.brc.priceengine.recommender.helper.PriceRecommenderHelper;
import com.brc.priceengine.recommender.model.Product;
import com.brc.priceengine.recommender.model.SurveyData;
import com.brc.priceengine.recommender.report.PriceRecommender;

/**
 * 
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		
		try {
			System.out.println("Enter number of products followed by each product name and the product's supply and demand parameters and then enter the Surveyed data "
					+ "that contains Product code, Competitor and Price.");
			
			InputHelper inputHelper = new InputHelper();
			List<Product> productList = inputHelper.processProductInput();
			List<SurveyData> surveyDataList = inputHelper.processSurveyDataInput();
	
			PriceRecommenderHelper priceRecommenderhelper = new PriceRecommenderHelper();
			
			Map<String, Double> productAvgPriceMap = priceRecommenderhelper.getProductAvgPriceMap(productList, surveyDataList);
			List<SurveyData> cleanedSurveyDataList = priceRecommenderhelper.getCleanedSurveyData(productAvgPriceMap, surveyDataList);
			
			PriceRecommender priceRecommender = new PriceRecommender();
			List<Double> chosenPriceList = priceRecommender.getChosenPriceForEachProduct(productList,
					cleanedSurveyDataList);
			priceRecommender.reportOutput(priceRecommender.recommendedPriceForEachProduct(chosenPriceList, productList));
		}catch(Exception e) {
			System.out.println("Process ended due to an exception");
		}		
	}
}
