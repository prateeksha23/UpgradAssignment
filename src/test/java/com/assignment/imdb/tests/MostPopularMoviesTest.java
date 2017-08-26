package com.assignment.imdb.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.assignment.imdb.TestInitiator;

public class MostPopularMoviesTest {
   
	TestInitiator test;
	
	@BeforeClass
	public void setUp(){
		test = new TestInitiator();
	}
	
	@Test
	public void Step01_VerifyUserIsOnHomeScreen(){
		test.homeScreen.verifyUserIsOnHomeScreen();
	}
	
	@Test(dependsOnMethods="Step01_VerifyUserIsOnHomeScreen")
	public void Step02_OpenMoviesScreen(){
		test.homeScreen.clickOnMoviesTab();
		test.homeScreen.verifyUserIsOnMoviesScreen();
	}
	
	@Test(dependsOnMethods="Step02_OpenMoviesScreen")
	public void Step03_OpenPopularMovies(){
		test.moviesScreen.clickOnSeeAllPopularMovies();
		test.popularMoviesScreen.verifyUserIsOnPopularMoviesScreen();
	}
	
	@Test(dependsOnMethods="Step03_OpenPopularMovies")
	public void Step04_VerifyPopularScreenElements(){
		test.popularMoviesScreen.verifyPopularMoviesScreenElements();
	}
	
	@Test(dependsOnMethods="Step04_VerifyPopularScreenElements")
	public void Step05_GetAllPopularMoviesDataAndStoreInDB(){
		test.popularMoviesScreen.getMovieNameRatingAndYear();
	}
	
	@Test(dependsOnMethods="Step05_GetAllPopularMoviesDataAndStoreInDB")
	public void Step06_VerifyNumberOfMovies(){
		test.popularMoviesScreen.verifyNumberOfMoviesFromDB(100);
	}
	
	@Test(dependsOnMethods="Step06_VerifyNumberOfMovies")
	public void Step07_VerifyUserIsAbleToNavigateBackToMoviesScreen(){
		test.popularMoviesScreen.clickOnBackButton();
		test.homeScreen.verifyUserIsOnMoviesScreen();
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.takeScreenShotOnException();
		}
	}
	
	@AfterClass
	public void tearDown(){
		test.closeSession();
	}
}
