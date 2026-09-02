package com.axess.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
import java.io.File;

/**
 * Hands-on 5: Handling Mouse Hover & Menu Navigation with Actions Class
 */
public class TC009_MouseActions {
    public static WebDriver driver;
    public static Actions actionsBuilder;

    public static void main(String[] args) {
        try {
            appStartup();
            personalLoansWorkflow();
        } catch (Exception e) {
            System.err.println("Mouse Actions Test Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeApp();
        }
    }

    public static void appStartup() {
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
        driver.get("https://www.sc.com/in/");
        System.out.println("Opened Standard Chartered India Homepage.");
    }

    public static void personalLoansWorkflow() throws InterruptedException {
        actionsBuilder = new Actions(driver);

        WebElement loansMenu = driver.findElement(By.xpath("//button[text()='Loans']"));

        Action hoverOverLoans = actionsBuilder.moveToElement(loansMenu).build();
        hoverOverLoans.perform();
        System.out.println("Hovered mouse over Loans menu item.");

        Thread.sleep(2000);

        WebElement personalLoanLink = driver.findElement(By.xpath("//*[text()='Personal Loan']"));
        personalLoanLink.click();
        System.out.println("Clicked 'Personal Loan' sub-menu option.");

        Thread.sleep(3000);
    }

    public static void closeApp() {
        if (driver != null) {
            driver.quit();
            System.out.println("Closed browser session.");
        }
    }
}
