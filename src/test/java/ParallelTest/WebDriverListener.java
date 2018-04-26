package ParallelTest;

import org.openqa.selenium.WebDriver;
import org.testng.*;

public class WebDriverListener implements IInvokedMethodListener, ITestListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

       if(method.isConfigurationMethod()) {
           System.out.println("Inside beforeInvocation isConfigurationMethod to initialize the driver");
           WebDriver driver = LocalDriverFactory.createInstance("chrome");
           LocalDriverManager.setWebDriver(driver);
       }

        if (method.isTestMethod()) {
            String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("browserName");
            WebDriver driver = LocalDriverFactory.createInstance(browserName);
            LocalDriverManager.setWebDriver(driver);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            WebDriver driver = LocalDriverManager.getDriver();
            if (driver != null) {
                driver.quit();
            }
        }

        if(method.isConfigurationMethod()) {
            System.out.println("Inside afterInvocation isConfigurationMethod to kill the driver");
            WebDriver driver = LocalDriverManager.getDriver();
            if (driver != null) {
                driver.quit();
            }
        }
    }


    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {



    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}