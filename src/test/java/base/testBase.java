package base;

import ExtentReport.ExtentManager;
import ExtentReport.ExtentTestManager;
import ParallelTest.LocalDriverManager;
import Util.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.util.*;

public class testBase extends TestListenerAdapter {

    public static WebDriver driver;
    public InheritableThreadLocal<ExtentTest> parentTest = new InheritableThreadLocal<ExtentTest>();
    public InheritableThreadLocal<ExtentTest> test = new InheritableThreadLocal<ExtentTest>();
    public static ExtentReports extent = ExtentManager.getExtent(); // this is extra

    public Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);



   /* public void invokeBrowser() {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println("Hashcode of webDriver instance = " + LocalDriverManager.getDriver().hashCode());
        LocalDriverManager.getDriver().get(Config.getEnvDetails().get("url"));
        this.driver = LocalDriverManager.getDriver();


    }*/

     public void invokeBrowser() {

         // Mac version
         String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "/src/DriverExe/MacChromeDriver/chromedriver";


         // Window Version
      //  String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "\\src\\DriverExe\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        driver.get(Config.getEnvDetails().get("url"));


    }



    public List<String> returnTestDataFetchedFromExcel(String testCaseName, String sheetName, String whichColName) {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);
        Hashtable<String, String> data = DataUtil.readSpecificTestDataFromExcel(xls,  testCaseName,  sheetName,whichColName );
        List<String> totalContent = DataUtil.splitStringBasedOnUnderscore( data.get("FeedContent") );

        return totalContent;

    }


    @BeforeClass
    public synchronized void beforeClass() throws Exception {
        ExtentTest parent = ExtentTestManager.createTest(getClass().getSimpleName());
        parentTest.set(parent);
        //throw new Exception("Failed ******* ");
    }

    @BeforeMethod
    public synchronized void beforeMethod(Method method) {
        String methodName = method.getName();
        String value = String.valueOf(parentTest.get());
        ExtentTest child = parentTest.get().createNode(method.getName());
        test.set(child);
    }

    @AfterMethod
    public synchronized void afterMethod(ITestResult result) {

         // this is extra
        if (driver != null) {
            driver.quit();
        }

        if (result.getStatus() == ITestResult.FAILURE)
            test.get().log(Status.FAIL,"<b><font size=\"2\" color=\"red\"> &nbsp Test Failed </font></b> ");

        else if (result.getStatus() == ITestResult.SKIP)
            test.get().skip(result.getThrowable());
        else
            test.get().pass(MarkupHelper.createLabel("Test passed", ExtentColor.GREEN));

        ExtentManager.getExtent().flush();
    }


    // this is extra
    @AfterClass
    public void killDriver() {
        if (driver != null) {
            driver.quit();
        }
    }


} // Main class is closing
