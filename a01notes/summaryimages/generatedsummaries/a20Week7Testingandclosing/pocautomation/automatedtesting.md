# Automated Testing with Selenium - Simple & Practical Reference Guide

---

## What is Automated Testing?

**Automated Testing** means using a software program to run test scripts on a web browser automatically, instead of a human clicking buttons and typing text manually.

### Real-World Examples:
* **Manual Testing:** A human tester opens Microsoft Edge, types `https://www.google.com`, types `"Selenium WebDriver"` into the search box, hits Enter, and checks if the search results appear. Doing this manually for dozens of search terms on Chrome, Edge, and Firefox takes hours.
* **Automated Testing:** You write a 10-line Java script. When you run the script, Selenium opens the browser, types the search query, submits the form, and verifies the page title in 2 seconds automatically.

---

## 1. Introduction to the Selenium Suite

**Selenium** is a free, open-source tool used to automate web browsers. It works across different browsers (Chrome, Edge, Firefox, Safari) and operating systems (Windows, Linux, macOS).

The Selenium suite consists of 4 main components:

```
+-------------------------------------------------------------------+
|                           SELENIUM SUITE                          |
+-------------------+--------------------+--------------------------+
|  1. Selenium IDE  |  2. Selenium RC    | 3. Selenium WebDriver    | 4. Selenium Grid
|  (Record & Play)  |  (Legacy/Old)      | (Modern Standard)        | (Parallel Execution)
+-------------------+--------------------+--------------------------+
```

---

### Component Breakdown & Examples:

#### A. Selenium IDE (Integrated Development Environment)
* **What it is:** A browser extension (add-on for Chrome or Firefox) that records your clicks and typing, then lets you play them back.
* **Best for:** Quick prototypes or fast non-technical testing.
* **Example:** You open Selenium IDE in Chrome, click **Record**, go to `https://www.google.com`, type `"Weather today"`, press Enter, and stop recording. Next time, you click **Play** to re-run those exact steps automatically.

#### B. Selenium RC (Remote Control) - Legacy
* **What it is:** The old version of Selenium. It injected a JavaScript program called "Selenium Core" into the browser to control it.
* **Status:** Deprecated (outdated). Merged with WebDriver to create Selenium 2 and modern Selenium.
* **Example:** Sending commands from Java to an intermediate RC Server, which injected JavaScript into web pages like `http://gmail.com` to fill in login fields.

#### C. Selenium WebDriver (Modern Core)
* **What it is:** The current industry standard tool. It talks directly to the browser using the browser's own native language (via driver binaries like `msedgedriver.exe` or `chromedriver.exe`).
* **Public Website Example (Google):**
```java
WebDriver driver = new EdgeDriver();
driver.get("https://www.google.com");
System.out.println("Page Title: " + driver.getTitle()); // Outputs "Google"
```

#### D. Selenium Grid
* **What it is:** A tool that allows you to run multiple tests simultaneously across different machines, browsers, and operating systems at the same time.
* **Architecture:**
  * **Hub (Central Controller):** Receives tests and decides where to send them.
  * **Nodes (Worker Computers):** The actual computers executing tests.
* **Example Diagram:**
```
               +-----------------------+
               |   Selenium Grid HUB   |
               | (Central Controller)  |
               +-----------+-----------+
                           |
       +-------------------+-------------------+
       |                   |                   |
+------v-------+    +------v-------+    +------v-------+
|  Node 1      |    |  Node 2      |    |  Node 3      |
|  Windows OS  |    |  Linux OS    |    |  macOS       |
|  Edge        |    |  Firefox     |    |  Safari      |
+--------------+    +--------------+    +--------------+
```
* **Example Scenario:** Running 100 Google & Wikipedia test cases at once: 33 on Windows Edge, 33 on Linux Firefox, and 34 on Mac Safari, finishing in 5 minutes instead of 2 hours.

---

## 2. Selenium WebDriver Architecture

WebDriver uses a 4-layer client-server structure to communicate with browsers natively:

```
[ Your Java Test Code ] 
       │
       ▼ (JSON Wire Protocol / W3C HTTP Commands)
[ Browser Driver Executable (msedgedriver.exe / chromedriver.exe) ]
       │
       ▼ (Native Browser Commands)
[ Web Browser (Microsoft Edge / Google Chrome) ]
```

### Real-World Architectural Layer Example (Google Search):
1. **Layer 1 (Java Code):** You write `driver.findElement(By.name("q")).sendKeys("OpenAI");`.
2. **Layer 2 (HTTP Request):** Selenium converts this code into an HTTP POST request: `{"using": "name", "value": "q"}`.
3. **Layer 3 (Browser Driver):** `msedgedriver.exe` receives the HTTP request and translates it to native browser commands.
4. **Layer 4 (Browser Execution):** Microsoft Edge receives the command, finds the Google search box, and types `"OpenAI"`.

---

## 3. Element Locators (How to Find UI Elements)

To interact with a button, text box, or link, Selenium first needs to **locate** it on the page. Selenium provides 8 locator strategies using `driver.findElement(By...)`.

---

### The 8 Locator Strategies with Public Website Examples (Google, Wikipedia, GitHub):

#### 1. ID Locator (`By.id`)
* **Explanation:** Finds an element using its unique `id` attribute. This is the **fastest and safest** locator.
* **Public Website Example (Wikipedia Search Box):**
  * **HTML:** `<input id="searchInput" name="search" type="search">`
  * **Java Code:**
```java
driver.get("https://www.wikipedia.org");
WebElement searchInput = driver.findElement(By.id("searchInput"));
searchInput.sendKeys("Software Automation");
```

#### 2. Name Locator (`By.name`)
* **Explanation:** Finds an element using its `name` attribute.
* **Public Website Example (Google Search Box):**
  * **HTML:** `<textarea name="q" title="Search">`
  * **Java Code:**
```java
driver.get("https://www.google.com");
WebElement googleSearchBox = driver.findElement(By.name("q"));
googleSearchBox.sendKeys("Selenium WebDriver tutorial");
```

#### 3. Class Name Locator (`By.className`)
* **Explanation:** Finds an element by matching its CSS `class` attribute.
* **Public Website Example (Wikipedia Language Button):**
  * **HTML:** `<button class="pure-button pure-button-primary-progressive">`
  * **Java Code:**
```java
driver.get("https://www.wikipedia.org");
WebElement searchButton = driver.findElement(By.className("pure-button-primary-progressive"));
searchButton.click();
```

#### 4. Tag Name Locator (`By.tagName`)
* **Explanation:** Finds elements matching an HTML tag (like `a`, `button`, `input`, `img`).
* **Public Website Example (Counting All Links on Google Homepage):**
  * **HTML:** `<a href="https://mail.google.com">Gmail</a>`
  * **Java Code:**
```java
driver.get("https://www.google.com");
List<WebElement> allLinks = driver.findElements(By.tagName("a"));
System.out.println("Total hyperlinks on Google homepage: " + allLinks.size());
```

#### 5. Link Text Locator (`By.linkText`)
* **Explanation:** Finds a link (`<a>` tag) by matching its **exact visible text**.
* **Public Website Example (Google "Gmail" Link):**
  * **HTML:** `<a href="https://mail.google.com">Gmail</a>`
  * **Java Code:**
```java
driver.get("https://www.google.com");
WebElement gmailLink = driver.findElement(By.linkText("Gmail"));
gmailLink.click();
```

#### 6. Partial Link Text Locator (`By.partialLinkText`)
* **Explanation:** Finds a link (`<a>` tag) by matching **part of its visible text**.
* **Public Website Example (Google "How Search Works" Link):**
  * **HTML:** `<a href="...">How Search works</a>`
  * **Java Code:**
```java
driver.get("https://www.google.com");
WebElement searchWorksLink = driver.findElement(By.partialLinkText("How Search"));
searchWorksLink.click();
```

#### 7. CSS Selector Locator (`By.cssSelector`)
* **Explanation:** Finds elements using CSS selector rules (`#` for ID, `.` for Class, `tag[attr=val]`).
* **Public Website Example (GitHub Search Input):**
  * **HTML:** `<input id="query-builder-test" class="search-input">`
  * **Java Examples:**
```java
driver.get("https://github.com");

// Target by Tag and ID (#)
driver.findElement(By.cssSelector("input#query-builder-test"));

// Target by Tag and Attribute
driver.findElement(By.cssSelector("button[aria-label='Search or jump to...']"));
```

#### 8. XPath Locator (`By.xpath`)
* **Explanation:** Finds elements using XML path expressions. Highly flexible for complex web pages.
* **Public Website Example (Google "Google Search" Button):**
  * **HTML:** `<input value="Google Search" name="btnK" type="submit">`
  * **Java Example:**
```java
driver.get("https://www.google.com");
WebElement searchBtn = driver.findElement(By.xpath("//input[@name='btnK' and @value='Google Search']"));
```

---

## 4. Deep Dive: XPath Expressions

XPath stands for **XML Path Language**. It searches through the HTML tree structure to find elements.

### Standard Relative XPath Formula:
$$\text{//tagname}[@\text{attribute} = \text{'value'}]$$

* `//` means search anywhere in the entire web page.
* `tagname` is the HTML tag (e.g., `input`, `button`, `div`, `span`, `a`).
* `@` signifies an attribute name (e.g., `@id`, `@name`, `@type`, `@class`).
* `'value'` is the exact text value of that attribute.

### Real Examples on Public Websites:

#### Example 1: Google Search Box by Name
* **URL:** `https://www.google.com`
* **HTML:** `<textarea name="q"></textarea>`
* **XPath:** `//textarea[@name='q']`

#### Example 2: Wikipedia Search Button by Type
* **URL:** `https://www.wikipedia.org`
* **HTML:** `<button type="submit">Search</button>`
* **XPath:** `//button[@type='submit']`

#### Example 3: Finding an Element by Exact Text (Google "Images" link)
* **URL:** `https://www.google.com`
* **HTML:** `<a href="...">Images</a>`
* **XPath:** `//a[text()='Images']`

#### Example 4: Finding an Element containing Partial Text (Wikipedia)
* **URL:** `https://www.wikipedia.org`
* **HTML:** `<span>The Free Encyclopedia</span>`
* **XPath:** `//span[contains(text(), 'Free Encyclopedia')]`

---

## 5. Interacting with Form & UI Web Elements

---

### A. Text Boxes (`sendKeys` & `clear`)
* `sendKeys("text")` types characters into an input field.
* `clear()` deletes existing text inside an input field.

#### Public Example (Wikipedia Search):
```java
driver.get("https://www.wikipedia.org");
WebElement searchBox = driver.findElement(By.id("searchInput"));
searchBox.clear();                  // Erases text
searchBox.sendKeys("Java Programming"); // Types new query
searchBox.sendKeys(Keys.ENTER);     // Hits enter key
```

---

### B. Buttons, Checkboxes, & Radio Buttons (`click`)
* `click()` clicks a button, selects a radio button, or toggles a checkbox.

#### State Verification Methods:
* `isSelected()` checks if a checkbox/radio button is checked (returns `true` or `false`).
* `isDisplayed()` checks if an element is visible on screen.
* `isEnabled()` checks if a button or input is active/clickable.

#### Public Example (The Internet HerokuApp Checkboxes):
```java
driver.get("https://the-internet.herokuapp.com/checkboxes");

// Find first checkbox
WebElement checkbox1 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));

if (checkbox1.isDisplayed() && checkbox1.isEnabled()) {
    if (!checkbox1.isSelected()) {
        checkbox1.click(); // Check option 1
        System.out.println("Checkbox 1 is now selected!");
    }
}
```

---

### C. Dropdowns & Select Operations (`Select` Class)
For standard HTML `<select>` dropdown menus, Selenium provides a helper class: `org.openqa.selenium.support.ui.Select`.

#### Public Example (The Internet HerokuApp Dropdown):
```java
import org.openqa.selenium.support.ui.Select;

driver.get("https://the-internet.herokuapp.com/dropdown");

// Step 1: Find the select dropdown element
WebElement dropdownElement = driver.findElement(By.id("dropdown"));

// Step 2: Wrap inside Select class
Select selectOption = new Select(dropdownElement);

// Method 1: Select by visible text
selectOption.selectByVisibleText("Option 2");

// Method 2: Select by value attribute
selectOption.selectByValue("1");

// Method 3: Select by option index (0-based)
selectOption.selectByIndex(2); // Selects Option 2
```

---

## 6. Advanced Selenium Features with Public Examples

---

### A. Capturing Webpage Screenshots
Screenshots are used to save image files when a test case fails or to document automated steps.

#### Public Example (Capturing Google Homepage):
```java
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;

driver.get("https://www.google.com");

// Step 1: Cast driver to TakesScreenshot
TakesScreenshot ts = (TakesScreenshot) driver;

// Step 2: Capture raw screenshot file
File sourceImage = ts.getScreenshotAs(OutputType.FILE);

// Step 3: Save to target folder
File destinationFile = new File("target/screenshots/google_homepage.png");
FileUtils.copyFile(sourceImage, destinationFile);
System.out.println("Google homepage screenshot saved to target/screenshots!");
```

---

### B. Handling JavaScript Alerts & Popups
Browsers display native JavaScript dialogs (Alerts) that block interaction until handled.

#### Public Example (The Internet HerokuApp JavaScript Alerts):
`https://the-internet.herokuapp.com/javascript_alerts`

1. **Simple Alert (Click OK):**
```java
driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
driver.switchTo().alert().accept(); // Clicks OK
```

2. **Confirmation Alert (Click Cancel):**
```java
driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
driver.switchTo().alert().dismiss(); // Clicks Cancel
```

3. **Prompt Alert (Type text and Click OK):**
```java
driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
driver.switchTo().alert().sendKeys("Hello Selenium!");
driver.switchTo().alert().accept(); // Submits input
```

---

### C. Advanced Mouse Interactions (`Actions` Class)
For mouse actions like hovering over menus, right-clicking, double-clicking, or drag-and-drop, use `org.openqa.selenium.interactions.Actions`.

#### Public Examples (The Internet HerokuApp Demos):

#### 1. Mouse Hover (`moveToElement`)
```java
driver.get("https://the-internet.herokuapp.com/hovers");

Actions actions = new Actions(driver);
WebElement userAvatar1 = driver.findElement(By.xpath("(//div[@class='figure'])[1]"));

// Hover mouse over first user avatar to reveal profile link
actions.moveToElement(userAvatar1).perform();
```

#### 2. Right-Click (`contextClick`)
```java
driver.get("https://the-internet.herokuapp.com/context_menu");

WebElement hotSpot = driver.findElement(By.id("hot-spot"));
actions.contextClick(hotSpot).perform(); // Opens context menu
```

#### 3. Drag and Drop (`dragAndDrop`)
```java
driver.get("https://the-internet.herokuapp.com/drag_and_drop");

WebElement boxA = driver.findElement(By.id("column-a"));
WebElement boxB = driver.findElement(By.id("column-b"));

actions.dragAndDrop(boxA, boxB).perform(); // Drags Box A onto Box B
```

---

### D. Synchronization: Implicit Wait vs Explicit Wait

Web pages use AJAX and JavaScript to load content asynchronously. If Selenium looks for an element before it finishes loading, the script crashes with `NoSuchElementException`.

```
Implicit Wait  ==> Global rule: Wait up to X seconds for ANY element to appear.
Explicit Wait  ==> Specific rule: Wait until a SPECIFIC element becomes visible/clickable.
```

#### 1. Implicit Wait Example:
```java
// Tell driver to wait up to 10 seconds for any element before giving up
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

#### 2. Explicit Wait Example (Google Search Results):
```java
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

driver.get("https://www.google.com");
driver.findElement(By.name("q")).sendKeys("Selenium WebDriver" + Keys.ENTER);

// Create wait object with 15-second timeout
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

// Wait until the search results container becomes visible
WebElement searchResults = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("search"))
);
System.out.println("Search results loaded successfully!");
```

---

### E. Handling Multiple Windows & Tabs

#### Public Example (Opening & Switching Tabs):
```java
driver.get("https://the-internet.herokuapp.com/windows");

// Get current window handle ID
String parentWindow = driver.getWindowHandle();

// Click link that opens new window tab
driver.findElement(By.linkText("Click Here")).click();

// Get all open window handles
Set<String> allWindows = driver.getWindowHandles();

// Switch to the new tab
for (String windowID : allWindows) {
    if (!windowID.equals(parentWindow)) {
        driver.switchTo().window(windowID);
        System.out.println("New Tab Title: " + driver.getTitle());
        break;
    }
}

// Close new tab and switch back to original window
driver.close();
driver.switchTo().window(parentWindow);
```

---

## 7. Code Walkthrough of Executable Programs

All executable code files are located in `src/main/java/com/axess/automation/`:

1. **`TC001_GoogleSearchPublicDemo.java`**
   * Opens `https://www.google.com`, searches `"Selenium WebDriver Java"`, verifies title.
2. **`TC002_WikipediaNavigationPublicDemo.java`**
   * Opens `https://www.wikipedia.org`, searches `"Artificial Intelligence"`, tests browser `back()`, `forward()`, and `refresh()`.
3. **`TC003_FormSubmissionPublicDemo.java`**
   * Demonstrates input fields and form handling on public demo forms.
4. **`TC007_XPathLocatorsPublicDemo.java`**
   * Locates Google/Wikipedia buttons and inputs using relative XPath expressions.
5. **`TC009_MouseActionsPublicDemo.java`**
   * Uses `Actions` class (`moveToElement`) for mouse hovers on public demo sites.
6. **`TC011_DropdownCalculatorPublicDemo.java`**
   * Demonstrates `<select>` dropdown handling using `selectByIndex`, `selectByValue`, `selectByVisibleText`.

---

## Summary Cheatsheet

| Task | Public Website Example | Selenium Java Command |
| :--- | :--- | :--- |
| **Launch Edge Browser** | Edge | `WebDriver driver = new EdgeDriver();` |
| **Open Website** | Google | `driver.get("https://www.google.com");` |
| **Search Field by Name** | Google | `driver.findElement(By.name("q"))` |
| **Search Input by ID** | Wikipedia | `driver.findElement(By.id("searchInput"))` |
| **Type & Hit Enter** | Google | `element.sendKeys("Selenium" + Keys.ENTER);` |
| **Link by Exact Text** | Google | `driver.findElement(By.linkText("Gmail"))` |
| **Dropdown Selection** | HerokuApp | `new Select(element).selectByVisibleText("Option 2");` |
| **Mouse Hover** | HerokuApp | `new Actions(driver).moveToElement(element).perform();` |
| **Take Screenshot** | Google | `((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE)` |
| **Close Browser** | Edge | `driver.quit();` |
