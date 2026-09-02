package com.axess.automation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility helper demonstrating 3-step screenshot capture process:
 * 1. Typecast WebDriver object to TakesScreenshot interface
 * 2. Invoke getScreenshotAs(OutputType.FILE)
 * 3. Copy source file to destination path using FileUtils
 */
public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            System.err.println("Driver is null. Cannot capture screenshot.");
            return null;
        }

        try {
            // Step 1: Typecast driver to TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) driver;

            // Step 2: Capture raw image output file
            File sourceFile = ts.getScreenshotAs(OutputType.FILE);

            // Generate timestamped file name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destDirPath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "screenshots";
            File destDir = new File(destDirPath);

            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            File destinationFile = new File(destDir, screenshotName + "_" + timestamp + ".png");

            // Step 3: Copy source file to target location
            FileUtils.copyFile(sourceFile, destinationFile);
            System.out.println("Screenshot captured successfully: " + destinationFile.getAbsolutePath());

            return destinationFile.getAbsolutePath();

        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }
}
