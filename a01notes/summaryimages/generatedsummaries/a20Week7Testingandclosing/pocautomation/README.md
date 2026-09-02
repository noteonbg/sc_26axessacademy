# Selenium Automation Suite - IntelliJ IDEA & Maven Setup Guide

This directory contains a complete **Maven-based Java Selenium Test Automation Suite** implementing all hands-on exercises and concepts from the **"Automating Testing"** course module, along with runnable public website demos (Google, Wikipedia, HerokuApp).

---

## 🛠️ Prerequisites

Ensure the following tools are installed on your machine before running the test suite:

1. **IntelliJ IDEA** (Community or Ultimate Edition).
2. **Java Development Kit (JDK):** JDK 17 or JDK 21 configured in IntelliJ.
   * Check local installation: `java -version`
3. **Apache Maven:** Maven 3.8+ (bundled with IntelliJ IDEA or system-installed).
   * Check local installation: `mvn -version`
4. **Web Browser:** Microsoft Edge (or Google Chrome / Mozilla Firefox).
5. **Internet Access:** Required on first build so Maven can download dependencies.

---

## 📁 Project Directory Structure

```
pocautomation/
├── drivers/                                  # Place msedgedriver.exe here for offline/corporate execution
│   └── msedgedriver.exe
├── pom.xml                                   # Maven dependencies & build configuration
├── README.md                                 # IntelliJ IDEA setup & execution guide
├── automatedtesting.md                       # Comprehensive reference notes with public website examples
└── src/
    └── main/
        └── java/
            └── com/
                └── axess/
                    └── automation/
                        ├── TC001_LaunchBrowser.java                  # Hands-on 1: Edge launch & title print
                        ├── TC001_GoogleSearchPublicDemo.java         # Public Demo 1: Google Search automation
                        ├── TC002_NavigationAndTitle.java              # Hands-on 2: Browser navigation APIs
                        ├── TC002_WikipediaNavigationPublicDemo.java  # Public Demo 2: Wikipedia Search & Navigation
                        ├── TC003_FormSubmission.java                  # Hands-on 3: Form field entry & submit
                        ├── TC006_HerokuAppDropdownPublicDemo.java     # Public Demo 3: Select dropdown handling
                        ├── TC007_XPathLocators.java                   # Hands-on 4: Relative XPath locators
                        ├── TC009_MouseActions.java                    # Hands-on 5: Actions class mouse hover
                        ├── TC011_DropdownEmiCalculator.java           # Hands-on 6: Select dropdown handling
                        └── ScreenshotUtils.java                       # Screenshot capture utility helper
```

---

## 🛑 How to Fix `UnknownHostException: msedgedriver.azureedge.net`

### Why does this error happen?
When running `WebDriverManager.edgedriver().setup()`, the library tries to contact `https://msedgedriver.azureedge.net` over the Internet. If your corporate proxy/firewall blocks `azureedge.net`, or because Microsoft decommissioned that URL, Java throws `UnknownHostException: msedgedriver.azureedge.net`.

### Solution (3 Easy Ways):

#### Option 1: Place `msedgedriver.exe` inside the project `drivers/` folder (Recommended)
1. Download `msedgedriver.exe` matching your installed Microsoft Edge version from Microsoft's official site:
   👉 **[Microsoft Edge Driver Download Page](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/)**
2. Extract `msedgedriver.exe` and place it inside the `drivers/` folder in this project:
   `F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a20Week7Testingandclosing\pocautomation\drivers\msedgedriver.exe`
3. All Java test files automatically detect `drivers/msedgedriver.exe` and execute instantly without needing any internet lookup!

#### Option 2: Pass `webdriver.edge.driver` path in Terminal
Pass your local driver path when running Maven:
```bash
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC001_GoogleSearchPublicDemo" "-Dwebdriver.edge.driver=C:\Users\YourName\Downloads\msedgedriver.exe"
```

#### Option 3: Configure `webdriver.edge.driver` System Property in Java Code
Specify your local driver path in `System.setProperty()` at the top of your test file:
```java
System.setProperty("webdriver.edge.driver", "C:\\Users\\PSID\\msedgedriver.exe");
```

---

## 💡 Step-by-Step Instructions for IntelliJ IDEA

### Step 1: Open Project in IntelliJ IDEA
1. Launch **IntelliJ IDEA**.
2. Click **File -> Open...** (or select **Open** on the Welcome Screen).
3. Navigate to and select the folder:
   `F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a20Week7Testingandclosing\pocautomation`
4. Click **OK** (and select **Trust Project** if asked).

---

### Step 2: Sync Maven Project
IntelliJ IDEA automatically detects `pom.xml`. To trigger a manual sync:
1. Open the **Maven Tool Window** on the right sidebar (**View -> Tool Windows -> Maven**).
2. Click the **Reload All Maven Projects** icon (the double circular arrows icon 🔄).
3. Wait for IntelliJ to download dependencies (`selenium-java`, `commons-io`).

---

### Step 3: Configure Project SDK / JDK
1. Go to **File -> Project Structure...** (Shortcut: `Ctrl + Alt + Shift + S`).
2. Under **Project Settings -> Project**:
   * **SDK:** Select JDK 17 or JDK 21.
   * **Language Level:** Set to `17` or higher.
3. Click **Apply** and **OK**.

---

### Step 4: Run Selenium Test Programs in IntelliJ IDEA

#### Option A: Direct Run via Code Editor (Fastest)
1. In the Project tool window (`Alt + 1`), expand `src -> main -> java -> com.axess.automation`.
2. Double-click any test file (e.g., `TC001_GoogleSearchPublicDemo.java`).
3. Click the green **Play ▶ icon** next to `public class TC001_GoogleSearchPublicDemo` or next to `public static void main(String[] args)`.
4. Select **Run 'TC001_GoogleSearchPublicDemo.main()'**.
5. *Keyboard Shortcut:* Press `Ctrl + Shift + F10` while inside the editor.

#### Option B: Run via Terminal Tab in IntelliJ IDEA (`Alt + F12`)

##### 🌐 Public Website Demos:
```bash
# Public Demo 1: Google Search
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC001_GoogleSearchPublicDemo"

# Public Demo 2: Wikipedia Search & Navigation
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC002_WikipediaNavigationPublicDemo"

# Public Demo 3: Select Dropdown Automation (HerokuApp)
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC006_HerokuAppDropdownPublicDemo"
```

##### 📚 Course Hands-on Programs:
```bash
# Hands-on 1: Browser Launch & Title
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC001_LaunchBrowser"

# Hands-on 2: Navigation APIs & Title Assertion
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC002_NavigationAndTitle"

# Hands-on 3: Form Credentials Input & Submit
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC003_FormSubmission"

# Hands-on 4: Relative XPath Locators
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC007_XPathLocators"

# Hands-on 5: Actions Class Mouse Hover
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC009_MouseActions"

# Hands-on 6: Select Dropdown EMI Calculator
mvn exec:java "-Dexec.mainClass=com.axess.automation.TC011_DropdownEmiCalculator"
```

---

## ⚡ Key IntelliJ IDEA Shortcuts Reference

| Action | Windows Shortcut | Description |
| :--- | :--- | :--- |
| **Run Current Main File** | `Ctrl + Shift + F10` | Executes the `main()` method of the currently active editor file. |
| **Rerun Last Program** | `Shift + F10` | Re-executes the most recent run configuration. |
| **Toggle Project View** | `Alt + 1` | Opens/closes the left Project panel. |
| **Open Built-in Terminal** | `Alt + F12` | Opens the embedded terminal window in IntelliJ. |
| **Open Project Structure** | `Ctrl + Alt + Shift + S` | Configures JDK and Module settings. |
| **Reformat Code** | `Ctrl + Alt + L` | Formats Java code to standard style guidelines. |
