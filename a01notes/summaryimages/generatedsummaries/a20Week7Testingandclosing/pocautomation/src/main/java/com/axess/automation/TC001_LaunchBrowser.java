package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;

/**
 * Hands-on 1: Basic Browser Launch & Title Capture
 * Demonstrates Edge Browser launch, title extraction, and browser exit.
 */
public class TC001_LaunchBrowser {
    public static WebDriver driver;

    public static void main(String[] args) {
        try {
            // Smart Driver Resolution:
            // 1. Check if system property "webdriver.edge.driver" is already set.
            // 2. Check if local driver exists at "drivers/msedgedriver.exe".
            // 3. Fallback to WebDriverManager online lookup.
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
                    System.out.println("WebDriverManager online lookup skipped (" + wdmException.getMessage() 
                            + "). Set 'webdriver.edge.driver' property or place msedgedriver.exe in drivers/ folder.");
                }
            }

            // Instantiate Edge Browser Driver
            driver = new EdgeDriver();

            // Navigate to destination URL
            driver.get("https://www.google.com/");

            // Print Page Title
            System.out.println("Page Title: " + driver.getTitle());

            // Pause briefly to view browser action
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Test Execution Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser session closed cleanly.");
            }
        }
    }
}
