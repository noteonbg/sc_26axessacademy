package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;

/**
 * Public Website Demo 2: Wikipedia Search & Navigation Automation
 * Opens https://www.wikipedia.org, locates search box by ID, searches 'Artificial Intelligence',
 * and demonstrates browser back(), forward(), and refresh() APIs.
 */
public class TC002_WikipediaNavigationPublicDemo {
    public static WebDriver driver;

    public static void main(String[] args) {
        try {
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

            // 1. Open Wikipedia Homepage
            System.out.println("Navigating to https://www.wikipedia.org...");
            driver.get("https://www.wikipedia.org");
            System.out.println("Page Title: " + driver.getTitle());

            // 2. Locate input by ID 'searchInput'
            WebElement searchInput = driver.findElement(By.id("searchInput"));
            searchInput.clear();
            searchInput.sendKeys("Artificial Intelligence");

            // 3. Click search button by Class Name
            WebElement searchButton = driver.findElement(By.className("pure-button-primary-progressive"));
            searchButton.click();

            Thread.sleep(3000);
            System.out.println("Article Page Title: " + driver.getTitle());

            // 4. Test Navigation APIs
            System.out.println("Navigating back to Wikipedia Homepage...");
            driver.navigate().back();
            Thread.sleep(2000);

            System.out.println("Navigating forward to Article Page...");
            driver.navigate().forward();
            Thread.sleep(2000);

            System.out.println("Refreshing current page...");
            driver.navigate().refresh();
            Thread.sleep(2000);

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
