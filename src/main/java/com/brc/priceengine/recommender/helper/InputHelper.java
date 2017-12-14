package com.brc.priceengine.recommender.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.brc.priceengine.recommender.model.Product;
import com.brc.priceengine.recommender.model.SurveyData;

public class InputHelper {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public List<Product> processProductInput() {
		List<Product> productList = new ArrayList<Product>();
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
		} catch (Exception e) {
			System.out.println("Invalid Input, please enter a valid number to begin the input process for Product info ");
		}
		return productList;
	}
	
	public List<SurveyData> processSurveyDataInput(){
		List<SurveyData> surveyDataList = new ArrayList<SurveyData>();
		try {
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
		}  catch (Exception e) {
			System.out.println("Invalid Input, please enter a valid number to begin the input process for SurveyData info ");
		}
		return surveyDataList;
	}
}
