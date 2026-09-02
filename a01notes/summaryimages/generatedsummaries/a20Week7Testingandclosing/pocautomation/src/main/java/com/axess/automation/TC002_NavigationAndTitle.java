package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;

/**
 * Hands-on 2: Browser Navigation Commands & Title Validation
 */
public class TC002_NavigationAndTitle {
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

            driver.get("https://axess.sc.com/signin");
            System.out.println("Initial Title: " + driver.getTitle());

            driver.navigate().to("https://www.sc.com/in/");
            Thread.sleep(2000);

            driver.navigate().back();
            Thread.sleep(1000);

            driver.navigate().forward();
            Thread.sleep(1000);

            driver.navigate().refresh();
            Thread.sleep(1000);

            System.out.println("Current URL: " + driver.getCurrentUrl());

            String currentTitle = driver.getTitle();
            if (currentTitle != null && currentTitle.contains("Standard Chartered")) {
                System.out.println("PASS: Successfully navigated to expected Standard Chartered page.");
            } else {
                System.out.println("FAIL: Unexpected page title -> " + currentTitle);
            }

        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
