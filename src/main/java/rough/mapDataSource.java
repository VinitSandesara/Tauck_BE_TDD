package rough;

import Util.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class mapDataSource {
    public static WebDriver driver;
    static String maxTimeOut = "25";

    public static void main(String arg[]) throws IOException, InterruptedException {

        String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "/src/DriverExe/MacChromeDriver/chromedriver";


        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);
        driver = new ChromeDriver();

        driver.get("http://qa2017.tauck.com/sitecore/login");

        driver.findElement(By.id("UserName")).sendKeys("vinit");
        driver.findElement(By.id("Password")).sendKeys("vinit");
        driver.findElement(By.id("Password")).sendKeys(Keys.ENTER);
      //  driver.findElement(By.linkText("FeedContent Editor")).click();

        driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/data/ships/le-boreal/decks/deck-2");
        driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click();
        driver.findElement(By.id("TreeSearch")).clear();

        String value = "Deluxe Cabin,Deluxe Suite,Prestige Cabin,Prestige Suite,Superior Cabin";
        List<String> inputSt = Arrays.asList(value.split(","));

        List<WebElement> totalTreeList = driver.findElements(By.xpath("//div[@class='scContentControl scContentControlTreelist']"));

        List<WebElement> totalTreeListAct1 = totalTreeList.get(0).findElements(By.xpath("//span[@class='scContentTreeNodeTitle']"));


        for(int i=0;i<inputSt.size();i++) {

            for(int j=0;j<totalTreeListAct1.size();j++) {

                String linkValue2 = totalTreeListAct1.get(0).getText();
                if(totalTreeListAct1.get(j).findElement(By.linkText("a")).toString().equalsIgnoreCase(inputSt.get(i))) {

                    System.out.println("Matched : - " );

                }

            }

        }



        // presentation click
        driver.findElement(By.xpath("//div[@class='scRibbonNavigatorButtonsGroupButtons']/a[text()='Presentation']")).click();

        // Details link
        driver.findElement(By.linkText("Details")).click();

        // Layout details dialog

        driver.switchTo().frame("jqueryModalDialogsFrame");
        driver.switchTo().frame("scContentIframeId0");

        driver.findElement(By.id("Tabs_tab_1")).click();

        // EditorialLayout click

        Actions actionOne = new Actions(driver);

        List<WebElement> editorialTemplateList = driver.findElements(By.xpath("//span[@title='Default']"));

        actionOne.doubleClick(editorialTemplateList.get(1)).perform();

        // Controls click

        driver.switchTo().defaultContent();

        driver.switchTo().frame("jqueryModalDialogsFrame");
        driver.switchTo().frame("scContentIframeId1");

        driver.findElement(By.linkText("Controls")).click();

        // add button

        WebElement addBtn = driver.findElement(By.xpath("//button[@class='scButton']"));
        addBtn.click();

        driver.switchTo().defaultContent();
        driver.switchTo().frame(Config.PARENT_FRAME);

        List<WebElement> total = driver.findElements(By.xpath("//div[@class='ui-dialog-titlebar-buttonpane']"));
        total.get(2).findElement(By.linkText("maximize")).click();

     //   driver.switchTo().defaultContent();
     //   driver.switchTo().frame(Config.PARENT_FRAME);
        driver.switchTo().frame(Config.CHILD_FRAME_THREE);



     //   driver.findElement(By.xpath("//span[text() = 'Common']")).click();

        driver.findElement(By.linkText("Common")).click();


        List<WebElement> totalLinks = driver.findElements(By.id("Renderings"));

        int size = totalLinks.size();

        totalLinks.get(0).findElement(By.linkText("Highlights")).click();

        driver.findElement(By.linkText("Highlights Portrait Component")).click();








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
