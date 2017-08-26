package com.assignment.imdb;

import static com.assignment.utility.ConfigReader.getProperty;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.assignment.imdb.actions.HomeScreenActions;
import com.assignment.imdb.actions.MoviesScreenActions;
import com.assignment.imdb.actions.PopularMoviesScreenActions;

import io.appium.java_client.android.AndroidDriver;

public class TestInitiator {
	WebDriver driver;
	public HomeScreenActions homeScreen;
	public MoviesScreenActions moviesScreen;
	public PopularMoviesScreenActions popularMoviesScreen;
    public TestInitiator(){
    	initializeDriver();
    	initScreenActions();
    }
    
    private void initScreenActions() {
    	homeScreen = new HomeScreenActions(driver);
    	moviesScreen = new MoviesScreenActions(driver);
    	popularMoviesScreen = new PopularMoviesScreenActions(driver);
	}

	public void initializeDriver(){
    	DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", getProperty("Config.properties", "deviceName"));
        cap.setCapability("platformName", getProperty("Config.properties", "platformName"));
        cap.setCapability("appPackage", getProperty("Config.properties", "appPackage"));
       	cap.setCapability("appActivity", getProperty("Config.properties", "appActivity"));
       	cap.setCapability("udid", getProperty("Config.properties", "deviceId"));
        String seleniuhubaddress =  getProperty("Config.properties", "appiumHost");
        URL selserverhost = null;
        try {
            selserverhost = new URL(seleniuhubaddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        cap.setJavascriptEnabled(true);
        driver = new AndroidDriver<WebElement>(selserverhost, cap);
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(getProperty("Config.properties", "timeout")),TimeUnit.SECONDS);
    }
	
	public void takeScreenShotOnException() {
		  String destDir = "screenshots";
		  File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		  new File(destDir).mkdirs();
		  String destFile = dateFormat.format(new Date()) + ".png";

		  try {
		   FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }

	
	public void closeSession(){
		driver.quit();
	}
}
