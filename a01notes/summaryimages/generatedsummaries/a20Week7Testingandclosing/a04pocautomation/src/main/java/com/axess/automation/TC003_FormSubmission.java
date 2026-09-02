package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;

/**
 * Hands-on 3: Form Interaction & Credentials Entry
 */
public class TC003_FormSubmission {
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

            WebElement userField = driver.findElement(By.id("user"));
            WebElement pwdField = driver.findElement(By.id("pwd"));

            userField.clear();
            userField.sendKeys("sam@bank.com");

            pwdField.clear();
            pwdField.sendKeys("12345");

            Thread.sleep(2000);

            WebElement submitBtn = driver.findElement(By.className("submit-button"));
            submitBtn.click();

            System.out.println("Form submission triggered successfully.");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Form Submission Test Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
