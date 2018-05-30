package rough;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;

public class mapDataSource {
    public static WebDriver driver;
    static String maxTimeOut = "25";

    public static void main(String arg[]) throws IOException, InterruptedException {

        String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "/src/DriverExe/chromedriver";


        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);
        driver = new ChromeDriver();

        driver.get("http://dev2017.tauck.com/sitecore/login");

        driver.findElement(By.id("UserName")).sendKeys("vinit");
        driver.findElement(By.id("Password")).sendKeys("vinit");
        driver.findElement(By.id("Password")).sendKeys(Keys.ENTER);
        driver.findElement(By.linkText("FeedContent Editor")).click();

        driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/Home/automation-editorial");
        driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click();
        driver.findElement(By.id("TreeSearch")).clear();

        // presentation click
        driver.findElement(By.xpath("//div[@class='scRibbonNavigatorButtonsGroupButtons']/a[text()='Presentation']")).click();

        // Details link
        driver.findElement(By.linkText("Details")).click();

        // Layout details dialog

        driver.switchTo().frame("jqueryModalDialogsFrame");
        driver.switchTo().frame("scContentIframeId0");

        driver.findElement(By.id("Tabs_tab_1")).click();

        // EditorialLayout click

        List<WebElement> editorialTemplateList = driver.findElements(By.xpath("//span[text() = 'EditorialLayout']"));

        editorialTemplateList.get(1).click();

        // Controls click

        driver.switchTo().defaultContent();

        driver.switchTo().frame("jqueryModalDialogsFrame");
        driver.switchTo().frame("scContentIframeId1");

        driver.findElement(By.linkText("Controls")).click();

        // List of Controls

        List<WebElement> list = driver.findElements(By.xpath("//div[@id='Renderings']/div"));

        list.get(list.size() - 1).click();

        // Remove button click

        WebElement element = driver.findElement(By.xpath("//button[@id='btnRemove']"));

        while (element.getAttribute("disabled") == null) {

            element.click();

        }

        driver.findElement(By.xpath("//button[@class='scButton']")).click();

        driver.switchTo().defaultContent();

        driver.switchTo().frame("jqueryModalDialogsFrame");
        driver.switchTo().frame("scContentIframeId2");

        WebElement selectControl = driver.findElement(By.linkText("Header Module"));

        selectControl.click();

        WebElement checkbox = driver.findElement(By.id("OpenProperties"));

        checkbox.click();

        driver.findElement(By.id("OK")).click();

        driver.switchTo().defaultContent();

        driver.switchTo().frame("jqueryModalDialogsFrame");
        driver.switchTo().frame("scContentIframeId2");


        List<WebElement> textbox = driver.findElements(By.xpath("//input[@class='scContentControl']"));

        for(int i=0;i<textbox.size();i++) {

            textbox.get(i).sendKeys("hello");
        }



        driver.findElement(By.xpath("//input[@value='OK']")).click();


} // Main method is closing here

} // mapDataSource class is closing here.
