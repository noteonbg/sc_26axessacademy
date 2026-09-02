package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;

/**
 * Public Website Demo 1: Google Search Automation
 * Opens https://www.google.com, enters search query, submits search, and prints page title.
 */
public class TC001_GoogleSearchPublicDemo {
    public static WebDriver driver;

    public static void main(String[] args) {
        try {
            // Smart Driver Resolution
            String existingDriverProp = System.getProperty("webdriver.edge.driver");
            File localDriver = new File("drivers" + File.separator + "msedgedriver.exe");

            if (existingDriverProp != null && new File(existingDriverProp).exists()) {
                System.out.println("Using EdgeDriver from System Property: " + existingDriverProp);
            } else if (localDriver.exists()) {
                System.setProperty("webdriver.edge.driver", localDriver.getAbsolutePath());
                System.out.println("Using EdgeDriver from local project directory: " + localDriver.getAbsolutePath());
            } else {
                try {
                    WebDriverManager.edgedriver().setup();
                } catch (Exception wdmException) {
                    System.out.println("WebDriverManager online lookup skipped (" + wdmException.getMessage() + ").");
                }
            }

            driver = new EdgeDriver();
            driver.manage().window().maximize();

            // 1. Navigate to Google
            System.out.println("Navigating to https://www.google.com...");
            driver.get("https://www.google.com");
            System.out.println("Initial Title: " + driver.getTitle());

            // 2. Locate Google Search input box using By.name("q")
            WebElement searchBox = driver.findElement(By.name("q"));

            // 3. Type search text and press Enter key
            searchBox.sendKeys("Selenium WebDriver Java" + Keys.ENTER);
            System.out.println("Submitted search query for 'Selenium WebDriver Java'");

            Thread.sleep(3000);

            // 4. Print updated Page Title
            System.out.println("Search Results Page Title: " + driver.getTitle());

        } catch (Exception e) {
            System.err.println("Google Search Demo Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser session closed cleanly.");
            }
        }
    }
}
