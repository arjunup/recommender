package com.brc.priceengine.recommender.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.brc.priceengine.recommender.model.Product;
import com.brc.priceengine.recommender.model.SurveyData;

public class PriceRecommenderHelperTest {
	
	PriceRecommenderHelper priceRecommenderHelperTest = new PriceRecommenderHelper();
	List<SurveyData> surveyDataList = new ArrayList<SurveyData>();
	List<Product> productList = new ArrayList<Product>();
	
	@Before
	public void init() {
		productList = setMockProductList();
		surveyDataList = setMockSurveyDataList();
	}
	
	private List<SurveyData> setMockSurveyDataList() {
		SurveyData surveyData1 = new SurveyData();
		surveyData1.setCompetitorName("X");
		surveyData1.setPrice(10.0);
		surveyData1.setProductName("ssd");
		
		SurveyData surveyData2 = new SurveyData();
		surveyData2.setCompetitorName("Y");
		surveyData2.setPrice(20.0);
		surveyData2.setProductName("flashdrive");
		
		SurveyData surveyData3 = new SurveyData();
		surveyData3.setCompetitorName("Y");
		surveyData3.setPrice(15.0);
		surveyData3.setProductName("ssd");
		
		SurveyData surveyData4 = new SurveyData();
		surveyData4.setCompetitorName("Z");
		surveyData4.setPrice(25.0);
		surveyData4.setProductName("flashdrive");
		
		surveyDataList.add(surveyData1);
		surveyDataList.add(surveyData2);
		surveyDataList.add(surveyData3);
		surveyDataList.add(surveyData4);
		return surveyDataList;
	}

	private List<Product> setMockProductList() {
		Product product1 = new Product();
		product1.setName("ssd");
		product1.setDemand("H");
		product1.setSupply("H");
		
		Product product2 = new Product();
		product2.setName("flashdrive");
		product2.setDemand("L");
		product2.setSupply("L");
		
		productList.add(product1);
		productList.add(product2);
		return productList;
	}

	@Test
	public void isDataErrorTest() {
		Double surveyDataPrice = 60.0;
		Double avgPrice = 43.3;
				
		assertFalse(priceRecommenderHelperTest.isDataError(surveyDataPrice, avgPrice));
		surveyDataPrice = 80.0;
		assertTrue(priceRecommenderHelperTest.isDataError(surveyDataPrice, avgPrice));
	}
	
	@Test
	public void isPromoOfferTest() {
		Double surveyDataPrice = 50.0;
		Double avgPrice = 43.3;
				
		assertFalse(priceRecommenderHelperTest.isPromoOffer(surveyDataPrice, avgPrice));
		surveyDataPrice = 20.0;
		assertTrue(priceRecommenderHelperTest.isPromoOffer(surveyDataPrice, avgPrice));
	}
	
	@Test
	public void productAvgPriceTest() {
		Product product = productList.get(0);
		assertEquals(12.5, priceRecommenderHelperTest.productAvgPrice(product, surveyDataList), 0.001);
	}
	
	@Test
	public void getProductAvgPriceMapTest() {
		Map<String, Double> testProductAvgPriceMap = new HashMap<String, Double>();
		testProductAvgPriceMap.put("ssd", 12.5);
		testProductAvgPriceMap.put("flashdrive", 22.5);
		assertEquals(testProductAvgPriceMap, priceRecommenderHelperTest.getProductAvgPriceMap(productList, surveyDataList));
		
	}
}
