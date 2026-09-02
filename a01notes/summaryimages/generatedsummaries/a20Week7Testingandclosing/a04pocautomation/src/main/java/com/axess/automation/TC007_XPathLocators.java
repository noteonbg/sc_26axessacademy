package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;

/**
 * Hands-on 4: Advanced Element Identification via Relative XPath
 */
public class TC007_XPathLocators {
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

            driver.findElement(By.id("user")).sendKeys("sam@bank.com");
            driver.findElement(By.id("pwd")).sendKeys("12345");
            Thread.sleep(2000);

            WebElement viewPasswordBtn = driver.findElement(By.xpath("//div[@role='button']"));
            viewPasswordBtn.click();
            System.out.println("Clicked View Password toggle via XPath //div[@role='button']");
            Thread.sleep(2000);

            WebElement continueBtn = driver.findElement(By.xpath("//button[text()='Continue']"));
            continueBtn.click();
            System.out.println("Clicked Continue button via XPath //button[text()='Continue']");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("XPath Test Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
