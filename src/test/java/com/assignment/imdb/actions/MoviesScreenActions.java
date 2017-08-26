package com.assignment.imdb.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class MoviesScreenActions {

	public MoviesScreenActions(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="See all Most Popular Movies")
	WebElement link_seeAll;
	
	
	/**
	 * Click on See All link for Most Popular Movies
	 */
	public void clickOnSeeAllPopularMovies(){
		link_seeAll.click();
		Reporter.log("Clicked on See All", true);
	}
}
