package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;

/**
 * Public Website Demo 3: Select Dropdown Automation
 * Opens https://the-internet.herokuapp.com/dropdown and tests selectByVisibleText, selectByValue, selectByIndex.
 */
public class TC006_HerokuAppDropdownPublicDemo {
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

            // 1. Open public HerokuApp dropdown demo page
            System.out.println("Navigating to https://the-internet.herokuapp.com/dropdown...");
            driver.get("https://the-internet.herokuapp.com/dropdown");

            // 2. Locate select element by ID 'dropdown'
            WebElement dropdownElement = driver.findElement(By.id("dropdown"));
            Select selectOption = new Select(dropdownElement);

            // 3. Select Option 1 by Value attribute
            selectOption.selectByValue("1");
            System.out.println("Selected Option 1 via selectByValue('1')");
            Thread.sleep(2000);

            // 4. Select Option 2 by Visible Text
            selectOption.selectByVisibleText("Option 2");
            System.out.println("Selected Option 2 via selectByVisibleText('Option 2')");
            Thread.sleep(2000);

            // 5. Select Option 1 by Index (1-based index in this dropdown)
            selectOption.selectByIndex(1);
            System.out.println("Selected Option 1 via selectByIndex(1)");
            Thread.sleep(2000);

        } catch (Exception e) {
            System.err.println("Dropdown Demo Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Browser session closed cleanly.");
            }
        }
    }
}
