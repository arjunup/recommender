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
	List<SurveyData> mockSurveyDataList = new ArrayList<SurveyData>();
	List<Product> mockProductList = new ArrayList<Product>();
	
	@Before
	public void init() {
		mockProductList = setMockProductList();
		mockSurveyDataList = setMockSurveyDataList();
	}
	
	private List<SurveyData> setMockSurveyDataList() {
		SurveyData surveyData1 = new SurveyData();
		surveyData1.setCompetitorName("X");
		surveyData1.setPrice(11.0);
		surveyData1.setProductName("ssd");
		
		SurveyData surveyData2 = new SurveyData();
		surveyData2.setCompetitorName("Y");
		surveyData2.setPrice(20.0);
		surveyData2.setProductName("flashdrive");
		
		SurveyData surveyData3 = new SurveyData();
		surveyData3.setCompetitorName("Y");
		surveyData3.setPrice(12.0);
		surveyData3.setProductName("ssd");
		
		SurveyData surveyData4 = new SurveyData();
		surveyData4.setCompetitorName("Z");
		surveyData4.setPrice(60.0);
		surveyData4.setProductName("flashdrive");
		
		SurveyData surveyData5 = new SurveyData();
		surveyData5.setCompetitorName("Y");
		surveyData5.setPrice(10.0);
		surveyData5.setProductName("ssd");
		
		SurveyData surveyData6 = new SurveyData();
		surveyData6.setCompetitorName("Y");
		surveyData6.setPrice(11.0);
		surveyData6.setProductName("ssd");
		
		SurveyData surveyData7 = new SurveyData();
		surveyData7.setCompetitorName("Y");
		surveyData7.setPrice(12.0);
		surveyData7.setProductName("ssd");
		
		SurveyData surveyData8 = new SurveyData();
		surveyData8.setCompetitorName("Y");
		surveyData8.setPrice(50.0);
		surveyData8.setProductName("flashdrive");
		
		mockSurveyDataList.add(surveyData1);
		mockSurveyDataList.add(surveyData2);
		mockSurveyDataList.add(surveyData3);
		mockSurveyDataList.add(surveyData4);
		mockSurveyDataList.add(surveyData5);
		mockSurveyDataList.add(surveyData6);
		mockSurveyDataList.add(surveyData7);
		mockSurveyDataList.add(surveyData8);
		return mockSurveyDataList;
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
		
		mockProductList.add(product1);
		mockProductList.add(product2);
		return mockProductList;
	}

	@Test
	public void isDataError_Test() {
		Double surveyDataPrice = 60.0;
		Double avgPrice = 43.3;
				
		assertFalse(priceRecommenderHelperTest.isDataError(surveyDataPrice, avgPrice));
		surveyDataPrice = 80.0;
		assertTrue(priceRecommenderHelperTest.isDataError(surveyDataPrice, avgPrice));
	}
	
	@Test
	public void isPromoOffer_Test() {
		Double surveyDataPrice = 50.0;
		Double avgPrice = 43.3;
				
		assertFalse(priceRecommenderHelperTest.isPromoOffer(surveyDataPrice, avgPrice));
		surveyDataPrice = 20.0;
		assertTrue(priceRecommenderHelperTest.isPromoOffer(surveyDataPrice, avgPrice));
	}
	
	@Test
	public void testProductAvgPrice() {
		Product product = mockProductList.get(0);
		assertEquals(11.2, priceRecommenderHelperTest.productAvgPrice(product, mockSurveyDataList), 0.001);
	}
	
	@Test
	public void testGetProductAvgPriceMap() {
		Map<String, Double> testProductAvgPriceMap = priceRecommenderHelperTest.getProductAvgPriceMap(mockProductList, mockSurveyDataList);	
		assertEquals(11.2, testProductAvgPriceMap.get("ssd"), 0.001);
	}
	
	@Test
	public void testGetCleanedSurveyData() {
		Map<String, Double> testProductAvgPriceMap = new HashMap<String, Double>();
		testProductAvgPriceMap.put("ssd", 11.2);
		testProductAvgPriceMap.put("flashdrive", 43.3333336);
		
		List<SurveyData> cleanedSurveyData = priceRecommenderHelperTest.getCleanedSurveyData(testProductAvgPriceMap, mockSurveyDataList);
		assertEquals(0.0, cleanedSurveyData.get(5).getPrice(), 0.001);
	}
}
