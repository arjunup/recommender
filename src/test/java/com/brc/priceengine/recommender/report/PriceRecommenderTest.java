package com.brc.priceengine.recommender.report;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.brc.priceengine.recommender.model.Product;
import com.brc.priceengine.recommender.model.SurveyData;

public class PriceRecommenderTest {
	
	PriceRecommender priceRecommenderTest = new PriceRecommender();
	List<SurveyData> mockSurveyDataList = new ArrayList<SurveyData>();
	List<Product> mockProductList = new ArrayList<Product>();
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void init() {
		System.setOut(new PrintStream(outContent));
		mockProductList = setMockProductList();
		mockSurveyDataList = setMockSurveyDataList();
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
	
	private List<SurveyData> setMockSurveyDataList() {
		SurveyData surveyData1 = new SurveyData();
		surveyData1.setCompetitorName("W");
		surveyData1.setPrice(11.0);
		surveyData1.setProductName("ssd");
		
		SurveyData surveyData2 = new SurveyData();
		surveyData2.setCompetitorName("Y");
		surveyData2.setPrice(50.0);
		surveyData2.setProductName("mp3player");
		
		SurveyData surveyData3 = new SurveyData();
		surveyData3.setCompetitorName("X");
		surveyData3.setPrice(12.0);
		surveyData3.setProductName("ssd");
		
		SurveyData surveyData4 = new SurveyData();
		surveyData4.setCompetitorName("Z");
		surveyData4.setPrice(60.0);
		surveyData4.setProductName("mp3player");
		
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
		surveyData8.setPrice(0.0);
		surveyData8.setProductName("mp3player");
		
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
		product1.setName("mp3player");
		product1.setDemand("H");
		product1.setSupply("H");
		
		Product product2 = new Product();
		product2.setName("ssd");
		product2.setDemand("L");
		product2.setSupply("L");
		
		mockProductList.add(product1);
		mockProductList.add(product2);
		return mockProductList;
	}
	
	@Test
	public void testGetChosenPriceForEachProduct() {
		List<Double> chosenValuesPerProductList = priceRecommenderTest.getChosenPriceForEachProduct(mockProductList, mockSurveyDataList);
		assertEquals(50.0, chosenValuesPerProductList.get(0), 0.001);
		assertEquals(11.0, chosenValuesPerProductList.get(1), 0.001);
	}
	
	@Test
	public void testRecommendedPriceForEachProduct() {
		List<Double> chosenValuesPerProductList = priceRecommenderTest.getChosenPriceForEachProduct(mockProductList, mockSurveyDataList);
		List<Double> recommendedPriceList = priceRecommenderTest.recommendedPriceForEachProduct(chosenValuesPerProductList, mockProductList);
		assertEquals(50.0, recommendedPriceList.get(0), 0.001);
		assertEquals(12.1, recommendedPriceList.get(1), 0.001);
	}
	
	@Test
	public void testReportOutput() {
		List<Double> recommendedPriceList = new ArrayList<Double>();
		recommendedPriceList.add(0.0);
		recommendedPriceList.add(null);
		recommendedPriceList.add(10.0);
		
		priceRecommenderTest.reportOutput(recommendedPriceList);
		
		assertEquals("A Invalid Survey Data\r\n" + 
				"B Invalid Supply/Demand parameters for Product Input data\r\n" + 
				"C 10.0\r\n",outContent.toString());
	}
}
