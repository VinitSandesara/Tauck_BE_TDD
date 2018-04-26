package base;

import ParallelTest.LocalDriverManager;
import TemplateImplementation.globalTemplateImplementation;
import Util.BrowserExe;
import Util.Config;
import Util.Xls_Reader;
import Util.excelConfig;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class testBase {

    public static WebDriver driver;


    public Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

   // public HashMap<String,String> whichEditorialComponent = new HashMap<String, String>();

    public List<String> listOfEditorialComponent = new ArrayList<String>();


    public void invokeBrowser() {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println("Hashcode of webDriver instance = " + LocalDriverManager.getDriver().hashCode());
        LocalDriverManager.getDriver().get(Config.getEnvDetails().get("url"));
        this.driver = LocalDriverManager.getDriver();

    }

    public void editorialComponentList() {

      /*  whichEditorialComponent.put("AuthorProfiles","AuthorProfiles");
        whichEditorialComponent.put("TextCopyFolder","TextCopyFolder");
        whichEditorialComponent.put("EditoriaQuotes","EditoriaQuotes");
        whichEditorialComponent.put("GridMediaFolder","GridMediaFolder");
        whichEditorialComponent.put("FeaturedContent","FeaturedContent"); */

      /*  listOfEditorialComponent.add("HeaderMedia");
        listOfEditorialComponent.add("EditorialHero");
        listOfEditorialComponent.add("AuthorProfiles");
        listOfEditorialComponent.add("TextCopyFolder");
        listOfEditorialComponent.add("EditoriaQuotes");
        listOfEditorialComponent.add("GridMediaFolder");
        listOfEditorialComponent.add("FeaturedContent");
         listOfEditorialComponent.add("CategoryCardModule");
         listOfEditorialComponent.add("FlexCard");
          listOfEditorialComponent.add("HalfWidthMedia");
          listOfEditorialComponent.add("FeaturedBrand");
        listOfEditorialComponent.add("MediaCarousel");*/

        listOfEditorialComponent.add("HalfWidthMedia");




    }


  /*  @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        init("Chrome");
        System.out.print("START TEST: " + method.getName() + "\n");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {


        if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.print(result.getName() + ": Executed Successfully");
            //pNode.pass(result.getName() + ": Test Case Executed Successfully!");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            System.out.print(result.getName() + ": Execution Failed");
            String code = "Method: " + result.getName() + "\n" +
                    "Reason: " + result.getThrowable().toString();

            //pNode.fail(m);
        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.print(result.getName() + ": Execution Skipped");
            //pNode.skip(result.getName() + ":Test Case Executed Skipped!");
        }


        System.out.print("\nEND TEST: " + result.getName());

        if (driver != null)
            System.out.print("======== GOING TO QUIT AFTER METHOD DRIVER ============");
            driver.quit();

    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
       /* if (driver != null)
            System.out.print("======== GOING TO QUIT AFTER TEST DRIVER ============");
            driver.quit();

    }


    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        // todo write your code here
    }


    public void init(String bType) {
        //test.log(LogStatus.INFO, "Opening browser - "+ bType);

        if (!Config.GRID_RUN) {
            // local machine
            if (bType.equals("Mozilla")) {
                System.setProperty("webdriver.gecko.driver", BrowserExe.FIREFOX_DRIVER_EXE);
                driver = new FirefoxDriver();
            } else if (bType.equals("Chrome")) {
                System.setProperty("webdriver.chrome.driver", BrowserExe.CHROME_DRIVER_EXE);

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                driver = new ChromeDriver(options);

            }
        } else {
            // grid
            DesiredCapabilities cap = null;
            if (bType.equals("Mozilla")) {
                cap = DesiredCapabilities.firefox();
                cap.setBrowserName("firefox");
                cap.setJavascriptEnabled(true);
                cap.setPlatform(Platform.MAC);

            } else if (bType.equals("Chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                cap = DesiredCapabilities.chrome();
                cap.setBrowserName("chrome");
                cap.setCapability(ChromeOptions.CAPABILITY, options);
                cap.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
               // cap.setCapability("chrome.switches", Arrays.asList("--incognito"));
                cap.setPlatform(Platform.MAC);
            }

            try {
                driver = new RemoteWebDriver(new URL("http://192.168.0.102:4444/wd/hub"), cap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);


    }*/


}
