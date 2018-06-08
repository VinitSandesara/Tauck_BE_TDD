package Util;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class utility extends AssertUtil {

    public static List<WebElement> webelements = null;

    public utility(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    public void kickOffUser() throws InterruptedException {


        List<WebElement> parentTable = new ArrayList<WebElement>();

        waitForPageLoad(20);
        WebElement kickOffbutton = driver.findElement(By.xpath("//button[@data-sc-id='KcikUserButton']"));
        kickOffbutton.click();

        // List<WebElement> parentTable = driver.findElements(By.xpath("//div[@class='mCSB_container']/table/tbody"));

        //waitForElementToBeVisible(By.xpath("//button[@data-sc-id='KickButton']"));
        waitForPageLoad(20);
        Thread.sleep(3000);

        //  List<WebElement> parentTable = driver.findElements(By.xpath("//div[@class='mCSB_container']/table/tbody/tr"));

        parentTable = driver.findElements(By.xpath("//td[text() = 'sitecore\\"+Config.getEnvDetails().get("username")+"']"));

        System.out.println("Total rows are :- " + parentTable.size());
        Random r = new Random();
        int n = r.nextInt((5 - 1) + 1) + 1;
        System.out.println("Random number is : - -- " + n);
        parentTable.get(n).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@data-sc-id='KickButton']")).click();

    }

    public boolean isElementPresent(String element, String by) {

        try {
            if (by != null) {

                if (by.equalsIgnoreCase("id")) {
                    webelements = driver.findElements(By.id(element));
                } else if (by.equalsIgnoreCase("name")) {
                    webelements = driver.findElements(By.name(element));
                } else if (by.equalsIgnoreCase("xpath")) {
                    webelements = driver.findElements(By.xpath(element));
                } else if (by.equalsIgnoreCase("linkText")) {
                    webelements = driver.findElements(By.linkText(element));
                } else if (by.equalsIgnoreCase("class")) {
                    webelements = driver.findElements(By.className(element));
                } else {
                    System.out.println("Please Check the Locator Syntax Given :"
                            + by);
                }
            }

            if (webelements.size() > 0 && webelements != null) {

                return true;
            } else {
                System.out.println("Element not found");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            test.fail("*****Error occurred while checking the presence of an element on the page*****"
                    + e.getMessage());
            return false;
        }


    }

    public boolean isElementPresent(List<WebElement> element) {

        try {

            if (element.size() > 0 && element != null) {

                return true;
            } else {
                System.out.println("Element not found");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


    public void captureErrorWithScreenShotForReporting(String errorMsg) throws IOException {


        test.fail(MarkupHelper.createLabel(errorMsg, ExtentColor.RED));
        test.addScreenCaptureFromPath(captureScreen());
        Assert.fail();

    }


    public String captureScreen() {
        String fileName =  System.currentTimeMillis() + ".png";
        try {
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File("./reports/ScreenShots/" + fileName));
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return "./ScreenShots/" + fileName;
    }

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 2px solid DeepPink;");
        js.executeScript("arguments[0].style.border='3px solid red'", element);


    }

    public void scrollToElement(WebElement element) throws Exception {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }


    public void clearTextboxPreFeededData(WebElement element) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", element);
    }


}
