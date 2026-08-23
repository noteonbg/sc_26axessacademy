package com.example;

import com.example.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Main Entry Point for the Core Java Maven Application.
 * Demonstrates reading Maven Profile-filtered properties at runtime.
 */
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("=================================================");
        logger.info("  Starting Maven Core Concepts Demonstration App ");
        logger.info("=================================================");

        // Load configuration from filtered application.properties
        Properties props = loadProperties();
        String appName = props.getProperty("app.name", "Maven Core Demo");
        String appVersion = props.getProperty("app.version", "1.0.0");
        String activeProfileEnv = props.getProperty("environment.active", "Unknown Profile");
        String logLevel = props.getProperty("log.level", "INFO");

        logger.info("Application: {} | Version: {}", appName, appVersion);
        logger.info(">>> ACTIVE MAVEN PROFILE / ENVIRONMENT: {}", activeProfileEnv);
        logger.info(">>> CONFIGURED LOG LEVEL: {}", logLevel);

        // Execute core business logic
        CalculatorService calculator = new CalculatorService();

        int sum = calculator.add(15, 25);
        int product = calculator.multiply(6, 7);
        double quotient = calculator.divide(100, 4);
        String formatted = calculator.formatMessage("   hello maven core concepts   ");

        logger.info("Calculation Results:");
        logger.info(" - 15 + 25 = {}", sum);
        logger.info(" - 6 * 7 = {}", product);
        logger.info(" - 100 / 4 = {}", quotient);
        logger.info(" - Formatted String: {}", formatted);

        logger.info("=================================================");
        logger.info("  Application Executed Successfully!              ");
        logger.info("=================================================");
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                logger.warn("application.properties file not found on classpath!");
            }
        } catch (Exception e) {
            logger.error("Failed to load application properties", e);
        }
        return props;
    }
}
