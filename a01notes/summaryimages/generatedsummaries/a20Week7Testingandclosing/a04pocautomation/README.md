# Selenium Automation Suite - Troubleshooting & Execution Guide

This directory contains a complete **Maven-based Java Selenium Test Automation Suite** implementing all hands-on exercises and runnable public website demos (Google, Wikipedia, HerokuApp).

---

### 1. Google Anti-Bot & CAPTCHA Redirect (`google.com/sorry/index`)
 **The Problem**: Rapid automated searches against Google (`google.com`) trigger Google's anti-bot protection, redirecting to a CAPTCHA page (`google.com/sorry/index`).
* **Impact**: `driver.getTitle()` returns the CAPTCHA URL string instead of `"Selenium WebDriver Java - Google Search"`, causing hardcoded title assertions to fail with a `TimeoutException`.
* **Fix Applied**: Updated `TC001_GoogleSearchPublicDemo.java` with a flexible wait condition (`d.getCurrentUrl().contains("q=Selenium") || d.getTitle().toLowerCase().contains("selenium")`) and cookie consent handling.

### 2. CDP Version Mismatch with Installed Edge Browser
* **The Problem**: `drivers/msedgedriver.exe` was a static binary created for an older Edge browser version.
* **Impact**: Produced warnings (`WARNING: Unable to find CDP implementation matching 152`) and driver version mismatches.
* **Fix Applied**: `TC001_GoogleSearchPublicDemo` and `TC002_WikipediaNavigationPublicDemo` now prioritize `WebDriverManager.edgedriver().setup()`, which automatically matches your installed Edge browser version.

### 3. Private / Internal Bank URLs in Hands-on Code
* **The Problem**: Scripts like `TC003_FormSubmission` and `TC007_XPathLocators` navigate to `https://axess.sc.com/signin`.
* **Impact**: `axess.sc.com` is an internal enterprise URL not accessible on public internet networks, resulting in `NoSuchElementException` on `By.id("user")`.

---

## How to Run the Working Public Demos

Run these commands from the `pocautomation` directory in your terminal:

```powershell
# Public Demo 1: Google Search Automation
mvn compile exec:java "-Dexec.mainClass=com.axess.automation.TC001_GoogleSearchPublicDemo"

# Public Demo 2: Wikipedia Search & Navigation Automation
mvn compile exec:java "-Dexec.mainClass=com.axess.automation.TC002_WikipediaNavigationPublicDemo"

# Public Demo 3: Select Dropdown Automation (HerokuApp)
mvn compile exec:java "-Dexec.mainClass=com.axess.automation.TC006_HerokuAppDropdownPublicDemo"
```

---

##  Project Directory Structure

```
pocautomation/
├── drivers/                                  # Place msedgedriver.exe here for offline execution
├── pom.xml                                   # Fixed Maven dependencies & dynamic exec-maven-plugin configuration
├── README.md                                 # Troubleshooting & Execution Guide
├── automatedtesting.md                       # Comprehensive reference notes
└── src/
    └── main/
        └── java/
            └── com/
                └── axess/
                    └── automation/
                        ├── TC001_LaunchBrowser.java                  # Hands-on 1: Edge launch & title print
                        ├── TC001_GoogleSearchPublicDemo.java         # Public Demo 1: Google Search (Fixed with Explicit Waits)
                        ├── TC002_NavigationAndTitle.java              # Hands-on 2: Browser navigation APIs
                        ├── TC002_WikipediaNavigationPublicDemo.java  # Public Demo 2: Wikipedia Search & Navigation (Fixed)
                        ├── TC003_FormSubmission.java                  # Hands-on 3: Form field entry (Requires Internal SC Network)
                        ├── TC006_HerokuAppDropdownPublicDemo.java     # Public Demo 3: Select dropdown handling
                        ├── TC007_XPathLocators.java                   # Hands-on 4: Relative XPath locators (Requires Internal SC Network)
                        ├── TC009_MouseActions.java                    # Hands-on 5: Actions class mouse hover
                        ├── TC011_DropdownEmiCalculator.java           # Hands-on 6: Select dropdown handling
                        └── ScreenshotUtils.java                       # Screenshot capture utility helper
```
