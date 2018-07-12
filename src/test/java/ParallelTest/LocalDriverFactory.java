package ParallelTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

class LocalDriverFactory {

    static String browser = "chrome";

    static WebDriver createInstance(String browserName) {
        WebDriver driver = null;
        if (browser.toLowerCase().contains("firefox")) {
            driver = new FirefoxDriver();
            return driver;
        }
        if (browser.toLowerCase().contains("internet")) {
            driver = new InternetExplorerDriver();
            return driver;
        }
        if (browser.toLowerCase().contains("chrome")) {


        // Mac version
             String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "/src/DriverExe/MacChromeDriver/chromedriver";


       // Window Version
            // String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "\\src\\DriverExe\\chromedriver.exe";

            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            driver = new ChromeDriver(options);
            driver.manage().deleteAllCookies();

            return driver;
        }
        return driver;
    }
}
