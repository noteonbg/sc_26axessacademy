package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

/**
 * Public Website Demo 1: Robust Google Search Automation
 * Demonstrates navigating to Google, handling consent popups, using Explicit Waits (WebDriverWait),
 * entering a search query, submitting, and validating search results.
 */
public class TC001_GoogleSearchPublicDemo {
    public static WebDriver driver;

    public static void main(String[] args) {
        try {
            // Driver setup: prefer WebDriverManager for automated driver-browser version matching
            try {
                WebDriverManager.edgedriver().setup();
            } catch (Exception wdmException) {
                File localDriver = new File("drivers" + File.separator + "msedgedriver.exe");
                if (localDriver.exists()) {
                    System.setProperty("webdriver.edge.driver", localDriver.getAbsolutePath());
                    System.out.println("Using local EdgeDriver binary: " + localDriver.getAbsolutePath());
                }
            }

            driver = new EdgeDriver();
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 1. Navigate to Google
            System.out.println("Navigating to https://www.google.com...");
            driver.get("https://www.google.com");
            System.out.println("Initial Title: " + driver.getTitle());

            // Handle optional Cookie Consent Popup if present
            try {
                WebElement acceptCookiesBtn = driver.findElement(By.xpath("//button[contains(., 'Accept all') or contains(., 'I agree')]"));
                if (acceptCookiesBtn.isDisplayed()) {
                    acceptCookiesBtn.click();
                    System.out.println("Dismissed Cookie Consent Dialog.");
                }
            } catch (Exception ignored) {
                // Consent popup not present in this region/locale
            }

            // 2. Locate Search Box using Explicit Wait
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));

            // 3. Type search text and press Enter key
            searchBox.clear();
            searchBox.sendKeys("Selenium WebDriver Java" + Keys.ENTER);
            System.out.println("Submitted search query for 'Selenium WebDriver Java'");

            // 4. Wait until search results URL or Title contains the search query
            wait.until(d -> d.getCurrentUrl().contains("q=Selenium") || d.getTitle().toLowerCase().contains("selenium"));
            
            System.out.println("SUCCESS - Current Page URL: " + driver.getCurrentUrl());
            System.out.println("SUCCESS - Search Results Page Title: " + driver.getTitle());

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
