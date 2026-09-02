package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;

/**
 * Hands-on 6: Handling Dropdowns & Select Operations
 */
public class TC011_DropdownEmiCalculator {
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

            driver.get("https://www.sc.com/in/loans/personal-loans/");
            Thread.sleep(4000);

            WebElement loanAmountInput = driver.findElement(By.xpath("//input[@aria-label='Loan amount'][1]"));
            loanAmountInput.clear();
            loanAmountInput.sendKeys("500000");
            System.out.println("Set Loan Amount to 500,000");
            Thread.sleep(3000);

            WebElement emiDisplay = driver.findElement(By.xpath("//span[@class='sc-pil-calculator__payment'][1]"));
            System.out.println("Calculated EMI: " + emiDisplay.getText());

            WebElement tenureDropdownElement = driver.findElement(By.xpath("//select[@aria-label='For Yrs']"));
            Select tenureSelect = new Select(tenureDropdownElement);

            tenureSelect.selectByIndex(2);
            System.out.println("Selected tenure by index (2)");
            Thread.sleep(3000);

            tenureSelect.selectByValue("4");
            System.out.println("Selected tenure by value ('4')");
            Thread.sleep(3000);

            tenureSelect.selectByVisibleText("5");
            System.out.println("Selected tenure by visible text ('5')");
            Thread.sleep(3000);

            WebElement applyBtn = driver.findElement(By.xpath("//span[text()='Apply Now']"));
            applyBtn.click();
            System.out.println("Clicked Apply Now.");

            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Dropdown Test Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
