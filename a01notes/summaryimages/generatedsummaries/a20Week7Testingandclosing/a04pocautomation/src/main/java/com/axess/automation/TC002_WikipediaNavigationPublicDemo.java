package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

/**
 * Public Website Demo 2: Robust Wikipedia Search & Navigation Automation
 * Opens https://www.wikipedia.org, searches 'Artificial Intelligence',
 * and demonstrates browser back(), forward(), and refresh() navigation APIs.
 */
public class TC002_WikipediaNavigationPublicDemo {
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

            // 1. Open Wikipedia Homepage
            System.out.println("Navigating to https://www.wikipedia.org...");
            driver.get("https://www.wikipedia.org");
            System.out.println("Page Title: " + driver.getTitle());

            // 2. Locate search input by ID 'searchInput' with Explicit Wait
            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchInput")));
            searchInput.clear();
            searchInput.sendKeys("Artificial Intelligence");

            // 3. Click search button (or submit form)
            try {
                WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
                searchButton.click();
            } catch (Exception fallback) {
                searchInput.submit();
            }

            // Wait until article page title contains "Artificial intelligence"
            wait.until(ExpectedConditions.titleContains("Artificial intelligence"));
            System.out.println("SUCCESS - Article Page Title: " + driver.getTitle());

            // 4. Test Navigation APIs
            System.out.println("Navigating back to Wikipedia Homepage...");
            driver.navigate().back();
            wait.until(ExpectedConditions.titleContains("Wikipedia"));
            System.out.println("Back Navigation Verified. Current Title: " + driver.getTitle());

            System.out.println("Navigating forward to Article Page...");
            driver.navigate().forward();
            wait.until(ExpectedConditions.titleContains("Artificial intelligence"));
            System.out.println("Forward Navigation Verified. Current Title: " + driver.getTitle());

            System.out.println("Refreshing current page...");
            driver.navigate().refresh();
            System.out.println("Page Refresh Complete.");

        } catch (Exception e) {
            System.err.println("Wikipedia Demo Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser session closed cleanly.");
            }
        }
    }
}
