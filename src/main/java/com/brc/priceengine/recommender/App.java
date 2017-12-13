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
		System.out.println("Enter number of products followed by each product name and the product's supply and demand parameters and then enter the Surveyed data "
				+ "that contains Product code, Competitor and Price.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Product> productList = new ArrayList<Product>();
		List<SurveyData> surveyDataList = new ArrayList<SurveyData>();

		try {
			int numOfProducts = Integer.parseInt(br.readLine());

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

			for (int i = 0; i < numofSurveyedPrices; i++) {
				SurveyData surveyData = new SurveyData();
				String value = br.readLine();
				String[] competitorInfoArray = value.split(" ");

				surveyData.setProductName(competitorInfoArray[0]);
				surveyData.setCompetitorName(competitorInfoArray[1]);
				surveyData.setPrice(Double.parseDouble(competitorInfoArray[2]));
				surveyDataList.add(surveyData);

			}

		} catch (Exception e) {
			System.out.println("Invalid Input, please enter a valid number for both ");
		}

		PriceRecommender priceRecommender = new PriceRecommender();
		priceRecommender.setSurveyDataList(surveyDataList);
		priceRecommender.setProductList(productList);
		Map<String, Double> productAvgPriceMap = priceRecommender.getAvgPriceList();
		List<SurveyData> cleanedSurveyDataList = priceRecommender.getCleanedSurveyData(productAvgPriceMap);
		List<Double> chosenPriceList = priceRecommender.getChosenPriceForEachProduct(productList,
				cleanedSurveyDataList);
		List<Double> recommendedPriceList = priceRecommender.recommendedPriceForEachProduct(chosenPriceList);
		char c = 'A';
		for (Double recommendedPrice : recommendedPriceList) {
			System.out.println(c + " " + recommendedPrice);
			c++;
		}
	}
}
