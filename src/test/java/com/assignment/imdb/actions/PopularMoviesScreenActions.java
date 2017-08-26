package com.assignment.imdb.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.assignment.utility.MySqlDBConnection;

import io.appium.java_client.AppiumDriver;

public class PopularMoviesScreenActions {

	WebDriver driver;
	
	public PopularMoviesScreenActions(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	@FindBy(id="com.imdb.mobile:id/label")
	List<WebElement> list_nameYear;
	
	@FindBy(xpath="(//android.view.ViewGroup[@resource-id='com.imdb.mobile:id/toolbar']//android.widget.TextView)[1]")
	WebElement title;
	
	@FindBy(xpath="(//android.view.ViewGroup[@resource-id='com.imdb.mobile:id/toolbar']//android.widget.TextView)[2]")
	WebElement subTitle;
	
	@FindBy(name="Navigate up")
	WebElement btn_back;
	
	@FindBy(id="com.imdb.mobile:id/search")
	WebElement icon_search;
	
	@FindBy(id="com.imdb.mobile:id/menu_account")
	WebElement icon_accountMenu;
	
	@FindBy(id="com.imdb.mobile:id/image")
	WebElement coverImage;
	
	@FindBy(id="com.imdb.mobile:id/watchlist_ribbon")
	WebElement watchListRibbon;
	
	@FindBy(id="com.imdb.mobile:id/ranking")
	WebElement txt_ranking;
	
	@FindBy(id="com.imdb.mobile:id/rank_velocity")
	WebElement txt_rankVelocity;
	
	@FindBy(id="com.imdb.mobile:id/list_component_rating")
	WebElement ratingComponent;
	
	@FindBy(id="com.imdb.mobile:id/metascore_label")
	WebElement lbl_metascore;
	
	/**
	 * Read all movie name, release year and rating from app
	 * And Store it in database
	 */
	public void getMovieNameRatingAndYear(){
		MySqlDBConnection msc = new MySqlDBConnection();
		msc.createConnection();
		String name, year, rating, currentMovie = null;
		for(int i=0;;i++){
			String lastName = list_nameYear.get(list_nameYear.size()-1).getText();
			if(lastName.substring(0, lastName.lastIndexOf("(")).equalsIgnoreCase(currentMovie)){
				scrollOnceUp();
				
				//to check if the list has ended and to break to loop
				lastName = list_nameYear.get(list_nameYear.size()-1).getText();
				if(lastName.substring(0, lastName.lastIndexOf("(")).equalsIgnoreCase(currentMovie)){
					break;
				}
				
				//if not continue
				i=-1; //reinitialize the counter
				continue;
			}
			WebElement e = list_nameYear.get(i);
			//get the movie name and year from the string
			name= e.getText().substring(0, e.getText().lastIndexOf("("));
			year = e.getText().substring(e.getText().lastIndexOf("(")+1, e.getText().lastIndexOf(")"));
			
			//update rating to NA is rating is not available for any movie
			List<WebElement> ratings = driver.findElements(By.xpath("(//android.widget.TextView[@resource-id='com.imdb.mobile:id/label'])["+(i+1)+"]//following-sibling::*[@resource-id='com.imdb.mobile:id/list_component_rating']//*[@resource-id='com.imdb.mobile:id/imdb_rating']"));
			if(ratings.size()==0){
				rating ="NA";
			}
			else{
				rating = ratings.get(0).getText();
			}
			
			currentMovie = name;
			//insert the movie data in row
			msc.insertRow(name, year, rating);
		}
		msc.closeConnection();
		Reporter.log("Inserted movies data to Database", true);
	}
	
	/**
	 * Scroll the mobile screen up
	 */
	public void scrollOnceUp(){
		Dimension dimensions = driver.manage().window().getSize();
		  Double screenHeightStart = dimensions.getHeight() * 0.70;
		  int scrollStart = screenHeightStart.intValue();
		  Double screenHeightEnd = dimensions.getHeight() * 0.1;
		  int scrollEnd = screenHeightEnd.intValue();
		  ((AppiumDriver<?>)driver).swipe(0,scrollStart,0,scrollEnd,1500);
	}

	
	/**
	 * Verify that user is on Most Popular Movies Screen
	 */
	public void verifyUserIsOnPopularMoviesScreen() {
		Assert.assertEquals(title.getText().trim(), "Most Popular Movies", "Assertion Failed : User is on Most Popular Movies Screen");
		Reporter.log("Assertion Passed : User is on Most Popular Movies Screen", true);
	}

	/**
	 * Verify that all expected elements are present on Most Popular Movies Screen
	 */
	public void verifyPopularMoviesScreenElements() {
		Assert.assertTrue(title.isDisplayed(), "Assertion Failed : Title is displayed");
		Reporter.log("Assertion Passed : Title is displayed", true);
		Assert.assertTrue(subTitle.isDisplayed(), "Assertion Failed : Sub Title is displayed");
		Reporter.log("Assertion Passed : Sub Title is displayed", true);
		Assert.assertTrue(icon_search.isDisplayed(), "Assertion Failed : Search icon is displayed");
		Reporter.log("Assertion Passed : Search icon is displayed", true);
		Assert.assertTrue(icon_accountMenu.isDisplayed(), "Assertion Failed : Account Menu icon is displayed");
		Reporter.log("Assertion Passed : Account Menu icon is displayed", true);
		Assert.assertTrue(btn_back.isDisplayed(), "Assertion Failed : Back button is displayed");
		Reporter.log("Assertion Passed : Back button is displayed", true);
		Assert.assertTrue(list_nameYear.size()>0, "Assertion Failed : Movie Name and Year is displayed");
		Reporter.log("Assertion Passed : Movie Name and Year is displayed", true);
		Assert.assertTrue(coverImage.isDisplayed(), "Assertion Failed : Cover Image is displayed");
		Reporter.log("Assertion Passed : Cover Image is displayed", true);
		Assert.assertTrue(watchListRibbon.isDisplayed(), "Assertion Failed : Watchlist Ribbon is displayed");
		Reporter.log("Assertion Passed : Watchlist Ribbon is displayed", true);
		Assert.assertTrue(txt_ranking.isDisplayed(), "Assertion Failed : Ranking is displayed");
		Reporter.log("Assertion Passed : Ranking is displayed", true);
		Assert.assertTrue(txt_rankVelocity.isDisplayed(), "Assertion Failed : Rank Velocity is displayed");
		Reporter.log("Assertion Passed : Rank Velocity is displayed", true);
		Assert.assertTrue(ratingComponent.isDisplayed(), "Assertion Failed : Rating is displayed");
		Reporter.log("Assertion Passed : Rating is displayed", true);
		Assert.assertTrue(lbl_metascore.isDisplayed(), "Assertion Failed : Metascore is displayed");
		Reporter.log("Assertion Passed : Metascore is displayed", true);
	}

	/**
	 * Verify the number of most popular movies from db
	 * @param expected count of movies
	 */
	public void verifyNumberOfMoviesFromDB(int i) {
		MySqlDBConnection msc = new MySqlDBConnection();
		msc.createConnection();
		Assert.assertEquals(msc.getCountOfMovies(), i, "Assertion Failed : Count of movies is correct");
		Reporter.log("Assertion Passed : Count of movies is correct", true);
		msc.closeConnection();
	}
	
	/**
	 * Click on Back button
	 */
	public void clickOnBackButton(){
		btn_back.click();
		Reporter.log("Clicked on Back button", true);
	}
	
}
