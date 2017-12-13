package com.brc.priceengine.recommender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		System.out.println("Enter number of products followed by each Product's supply and demand parameters");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int numOfProducts = Integer.parseInt(br.readLine());
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < numOfProducts; i++) {
			Product product = new Product();
			String value = br.readLine();
			String[] prodDescArray = value.split(" ");

			product.setName(prodDescArray[0]);
			product.setSupply(prodDescArray[1]);
			product.setDemand(prodDescArray[2]);
			productList.add(product);

		}

		int numofSurveyedPrices = Integer.parseInt(br.readLine());
		List<SurveyData> surveyDataList = new ArrayList<SurveyData>();

		for (int i = 0; i < numofSurveyedPrices; i++) {
			SurveyData surveyData = new SurveyData();
			String value = br.readLine();
			String[] competitorInfoArray = value.split(" ");

			surveyData.setProductName(competitorInfoArray[0]);
			surveyData.setCompetitorName(competitorInfoArray[1]);
			surveyData.setPrice(Double.parseDouble(competitorInfoArray[2]));
			surveyDataList.add(surveyData);

		}

		/*System.out.println(surveyDataList.get(0).getProduct());
		System.out.println(surveyDataList.get(0).getPrice());
		System.out.println(surveyDataList.get(0).getCompetitorName());*/
		
		PriceRecommender priceRecommender = new PriceRecommender();
		priceRecommender.setSurveyDataList(surveyDataList);
		priceRecommender.setProductList(productList);
		Map<String,Double> productAvgPriceMap = priceRecommender.getAvgPriceList();
	//	System.out.println("AvgPrice = " + avgPrice.get(0) + " " + avgPrice.get(1));
		
		List<SurveyData> cleanedSurveyDataList = priceRecommender.getCleanedSurveyData(productAvgPriceMap);
		
		System.out.println(cleanedSurveyDataList.get(0).getPrice());
		System.out.println(cleanedSurveyDataList.get(1).getPrice());
		System.out.println(cleanedSurveyDataList.get(2).getPrice());
		
		Map<Double, Integer> wordCount = priceRecommender.getChosenPriceForEachProduct(productList, cleanedSurveyDataList);
		System.out.println("wordCount = " + wordCount.get(11.0));
		
		
	}
}
