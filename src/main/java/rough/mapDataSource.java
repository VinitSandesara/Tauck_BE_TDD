package rough;

import FeedContent.feedContent;
import Util.Config;
import org.apache.commons.collections.list.TreeList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static TemplateImplementation.HomePage.counter;

public class mapDataSource {
    public static WebDriver driver;
    static String maxTimeOut = "25";

    public static void main(String arg[]) throws IOException, InterruptedException, ParseException {


        Date date1 = new Date();
        System.out.println(date1);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
// or pass in a command line arg: -Duser.timezone="UTC"

        Date date2 = new Date();
        System.out.println(date2);



        String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "/src/DriverExe/MacChromeDriver/chromedriver";


        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);
        driver = new ChromeDriver();

        driver.get("http://qa2017.tauck.com/sitecore/login");

        driver.findElement(By.id("UserName")).sendKeys("v");
        driver.findElement(By.id("Password")).sendKeys("v");
        driver.findElement(By.id("Password")).sendKeys(Keys.ENTER);
      //  driver.findElement(By.linkText("FeedContent Editor")).click();

        driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/Home/ships/leponant_thru_automation");
        driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click();
        driver.findElement(By.id("TreeSearch")).clear();

//====================================================================================

        List<WebElement> totalSelectedList;

        List<WebElement> totalSelectedListBox = driver.findElements(By.xpath("//div[@class='scContentControlSelectedList']"));

        totalSelectedList =   totalSelectedListBox.get(1).findElements(By.tagName("option"));

        for(int i=totalSelectedList.size()-1;i>=0;i--) {

            Actions actionOne = new Actions(driver);
            actionOne.doubleClick(totalSelectedList.get(i)).perform();
           totalSelectedList =   totalSelectedListBox.get(1).findElements(By.tagName("option"));
        }


        List<WebElement> totalTreeList = driver.findElements(By.xpath("//div[@class='scScrollbox scContentControlTree']"));

        List<WebElement> TreeListValues = driver.findElements(By.xpath("//div[@class='scScrollbox scContentControlTree']//span[@class='scContentTreeNodeTitle']"));

        List<WebElement> TreeListValuesTopNode = driver.findElements(By.xpath("//div[@class='scScrollbox scContentControlTree']//div[@class='scContentTreeNode']"));

        for(int i=0;i< TreeListValues.size();i++) {


            String value = TreeListValues.get(i).getText();

            if(TreeListValues.get(i).getText().equalsIgnoreCase("Isabela II"))  {



                TreeListValuesTopNode.get(i).findElements(By.className("scContentTreeNodeGlyph")).get(0).click();

                WebElement element = TreeListValuesTopNode.get(i).findElements(By.className("scContentTreeNodeGlyph")).get(1);

                element.click();



                List<WebElement> totalDecks =  TreeListValuesTopNode.get(i).findElements(By.className("scContentTreeNode"));

                List<WebElement> totalLinks = totalDecks.get(0).findElements(By.tagName("a"));

                for(int j=1;j<totalLinks.size();j++) {

                    String linkValue = totalLinks.get(j).getText();
                    totalLinks.get(j).click();
                    Actions actionOne = new Actions(driver);
                    actionOne.doubleClick(totalLinks.get(j)).perform();
                }

            }


        }






//====================================================================================
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
