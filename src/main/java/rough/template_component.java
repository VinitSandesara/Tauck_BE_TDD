package rough;

import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

public class template_component {

    public static WebDriver driver;
    static String maxTimeOut = "25";

    // to run this on stand alone
    public static void main(String arg[]) throws IOException, InterruptedException {


        String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "/src/DriverExe/chromedriver";


        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--kiosk");

     //   ChromeOptions options = new ChromeOptions();
     //   options.addArguments("--incognito");


        driver = new ChromeDriver();

        driver.get("http://dev2017.tauck.com/sitecore/login");

        driver.findElement(By.id("UserName")).sendKeys("vinit");
        driver.findElement(By.id("Password")).sendKeys("vinit");
        driver.findElement(By.id("Password")).sendKeys(Keys.ENTER);

        try {
            driver.findElement(By.linkText("FeedContent Editor")).click();

        }catch (Throwable t) {


            /* kick off user logic */


           // waitForElementToBeVisible(By.xpath("//button[@data-sc-id='KcikUserButton']"));
            waitForPageLoad(20);
            WebElement kickOffbutton = driver.findElement(By.xpath("//button[@data-sc-id='KcikUserButton']"));

            kickOffbutton.click();

            // List<WebElement> parentTable = driver.findElements(By.xpath("//div[@class='mCSB_container']/table/tbody"));

            //waitForElementToBeVisible(By.xpath("//button[@data-sc-id='KickButton']"));
            waitForPageLoad(20);
            Thread.sleep(3000);


            List<WebElement> parentTable = driver.findElements(By.xpath("//td[text() = 'sitecore\\vinit']"));

            System.out.println("Total rows are :- " + parentTable.size());
            parentTable.get(4).click();

            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@data-sc-id='KickButton']")).click();

           // driver.findElement(By.linkText("FeedContent Editor")).click();
        }

        WebElement searchTextbox = driver.findElement(By.id("TreeSearch"));
        WebElement searchResultCloseIcon = driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a"));

        navigateToTemplateOrTemplateComponent(searchTextbox, "/sitecore/content/Tauck/Home",searchResultCloseIcon);

        panList("EditorialTemplate");

        switchToFrame("jqueryModalDialogsFrame", "scContentIframeId0");
        componentName("Automation EditorialTemplate", "Value");

        panList("Header Media");

        switchToFrame("jqueryModalDialogsFrame", "scContentIframeId0");
        componentName("header media hero", "Value");

        navigateToTemplateOrTemplateComponent(searchTextbox, "/sitecore/content/Tauck/Home/automation-editorial",searchResultCloseIcon);

        panList("Media Carousel");

        switchToFrame("jqueryModalDialogsFrame", "scContentIframeId0");
        componentName("Media Carousel", "Value");

        navigateToTemplateOrTemplateComponent(searchTextbox, "/sitecore/content/Tauck/Home/automation-editorial/media-carousel",searchResultCloseIcon);

        panList("Media Carousel Card");

        switchToFrame("jqueryModalDialogsFrame", "scContentIframeId0");
        componentName("Media Carousel Card one", "Value");


    }


    public static void panList(String value) {

        WebElement newpanList = driver.findElement(By.id("NewPanelList"));

        java.util.List<WebElement> list = newpanList.findElements(By.tagName("a"));
        System.out.println("Total length is :- " + list.size());

        int panCounter = 0;
        while (list.get(panCounter).getAttribute("title").equalsIgnoreCase(value) != true) {
            System.out.println("Name is :- " + list.get(panCounter).getAttribute("title"));
            panCounter++;
        }

        list.get(panCounter).click();

    }

    public static void switchToFrame(String parentFrame, String childFrame) {

        try {
            Thread.sleep(2000);
            driver.switchTo().frame(parentFrame);
            driver.switchTo().frame(childFrame);

        } catch (NoSuchFrameException e) {
            System.out.println("Your result : - " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void navigateToTemplateOrTemplateComponent(WebElement searchTextbox,String path, WebElement searchResultCloseIcon) {

        searchTextbox.sendKeys(path);
        searchTextbox.sendKeys(Keys.ENTER);
        searchResultCloseIcon.click();
        searchTextbox.clear();
    }

    public static void componentName(String compomentname, String locator) {

        driver.findElement(By.id(locator)).sendKeys(compomentname);
        driver.findElement(By.id(locator)).sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();

    }


    public static void waitForElementToBeVisible(
            WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver,

                Integer.parseInt(maxTimeOut)); //Need to Give Global Timeout Value

        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToBeVisible(By locator) {
        try {

            WebDriverWait wait = new WebDriverWait(driver,

                    Integer.parseInt(maxTimeOut));      //Need to Give Global Timeout Value

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {

        }
    }

    public static boolean waitForPageLoad(int iTimeOut)
            throws InterruptedException {
        boolean isLoaded = false;

        Thread.sleep(2000);
        try {

            ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript(
                            "return document.readyState").equals("complete");
                }
            };
            WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
            wait.until(pageLoadCondition);
            isLoaded = true;
        } catch (Exception e) {

        }
        return isLoaded;
    }







} // class template_component is ending
