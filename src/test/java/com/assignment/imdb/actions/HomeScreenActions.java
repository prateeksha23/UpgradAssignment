package com.assignment.imdb.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class HomeScreenActions {
	
	WebDriver driver;

	public HomeScreenActions(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	
	@FindBy(name="Movies")
	WebElement tab_movies;
	
	@FindBy(name="Home")
	WebElement tab_home;
	
	
	/**
	 * Verify that user is on Home Screen
	 */
	public void verifyUserIsOnHomeScreen(){
		Assert.assertEquals(tab_home.getAttribute("selected"), "true", "Assertion Failed : User is on home screen");
		Reporter.log("Assertion Passed : User is on home screen", true);
	}
	
	/**
	 * Verify that user is on Movies Screen
	 */
	public void verifyUserIsOnMoviesScreen(){
		Assert.assertEquals(tab_movies.getAttribute("selected"), "true", "Assertion Failed : User is on Movies screen");
		Reporter.log("Assertion Passed : User is on Movies screen", true);
	}
	
	/**
	 * Click on Movies Tab
	 */
	public void clickOnMoviesTab(){
		tab_movies.click();
		Reporter.log("Clicked on Movies tab", true);
	}
}
