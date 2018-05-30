package rough;

import base.driverBase;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class rough_test2 {

    public static WebDriver driver;

    // to run this on stand alone
    public static void main(String arg[]) throws IOException, InterruptedException {


        String CHROME_DRIVER_EXE = System.getProperty("user.dir") + "/src/DriverExe/chromedriver";


        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--kiosk");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");


        driver = new ChromeDriver(options);

        driver.get("http://dev2017.tauck.com/sitecore/login");

        driver.findElement(By.id("UserName")).sendKeys("admin");
        driver.findElement(By.id("Password")).sendKeys("b");
        driver.findElement(By.id("Password")).sendKeys(Keys.ENTER);
        driver.findElement(By.linkText("FeedContent Editor")).click();

        // Tempo

        driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/Home/editorial_onlywithtauck");
        driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click();
        driver.findElement(By.id("TreeSearch")).clear();

        String preFeededValue = "EditorialTemplate Title|Header Hero|Half Width Media Module 1|Half Width Media Module 2|Text Copy Folder";
        List<String> listOfValue= Arrays.asList(preFeededValue.split("\\|"));

        for(int i=0;i<listOfValue.size();i++) {

            driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/Home/editorial_onlywithtauck/"+listOfValue.get(i)+"");
            driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
            driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click();
            driver.findElement(By.id("TreeSearch")).clear();



        }


        // Tempo end



        driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/Home");
        driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click();
        driver.findElement(By.id("TreeSearch")).clear();

      /*  driver.findElement(By.id("Tree_Glyph_0DE95AE441AB4D019EB067441B7C2450")).click();
        driver.findElement(By.id("Tree_Glyph_5AC6CF7A26B847A1A3268CD790317BE0")).click();*/

        // WebElement menu = driver.findElement(By.xpath("//span[text() = 'Home']"));

        panList("EditorialTemplate");

        switchToFrame("jqueryModalDialogsFrame", "scContentIframeId0");
        driver.findElement(By.id("Value")).sendKeys("Automation EditorialTemplate");
        driver.findElement(By.id("Value")).sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();

        panList("EditorialTemplate Hero");

        switchToFrame("jqueryModalDialogsFrame", "scContentIframeId0");
        driver.findElement(By.id("Value")).sendKeys("EditorialTemplate Hero");
        driver.findElement(By.id("Value")).sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();

       /* driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/Home/automation-editorial");
        driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click();

        panList("Media Carousel");

        switchToFrame("jqueryModalDialogsFrame", "scContentIframeId0");
        driver.findElement(By.id("Value")).sendKeys("Media Carousel");
        driver.findElement(By.id("Value")).sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();

       /* driver.findElement(By.id("TreeSearch")).sendKeys("/sitecore/content/Tauck/Home/destinations/antarctica/antarctica/whytauck/exclusivity");
        driver.findElement(By.id("TreeSearch")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//div[@id='SearchHeader']/div[1]/a")).click(); */


      //  List<WebElement> parentTable = driver.findElements(By.xpath("//div[@class='scEditorSections']/table/tbody/tr/td/table"));

//        tableData(parentTable, "td", "tr", "input");

        WebElement menu = driver.findElement(By.xpath("//span[text() = 'Home']"));


        try {
            Thread.sleep(3000);

            Actions action = new Actions(driver);
            action.moveToElement(menu).build().perform();

            JavascriptExecutor js = (JavascriptExecutor) driver;

            String javaScript = "var evt = document.createEvent('MouseEvents');"
                    + "var RIGHT_CLICK_BUTTON_CODE = 2;"
                    + "evt.initMouseEvent('contextmenu', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, RIGHT_CLICK_BUTTON_CODE, null);"
                    + "arguments[0].dispatchEvent(evt)";

            js.executeScript(javaScript, menu);

            System.out.println("Sucessfully Right clicked on the element");
            Thread.sleep(3000);
            driver.findElement(By.xpath("//td[text() = 'Insert']")).click();
            Thread.sleep(3000);


            //Get number of rows In table.
            int Row_count = driver.findElements(By.xpath("//*[@id='Popup2']/table/tbody/tr")).size();
            System.out.println("Number Of Rows = " + Row_count);

            //Get number of columns In table.
            int Col_count = driver.findElements(By.xpath("//*[@id='Popup2']/table/tbody/tr[1]/td")).size();
            System.out.println("Number Of Columns = " + Col_count);

            //divided xpath In three parts to pass Row_count and Col_count values.
            String first_part = "//*[@id='post-body-6522850981930750493']/table/tbody/tr[";
            String second_part = "]/td[";
            String third_part = "]";

            driver.findElement(By.xpath("//*[@id='Popup2']/table/tbody/tr[1]")).click();

            //Used for loop for number of rows.
            for (int i = 1; i <= Row_count; i++) {
                //Used for loop for number of columns.
                for (int j = 1; j <= Col_count; j++) {
                    //Prepared final xpath of specific cell as per values of i and j.
                    String final_xpath = first_part + i + second_part + j + third_part;
                    //Will retrieve value from located cell and print It.
                    String Table_data = driver.findElement(By.xpath(final_xpath)).getText();
                    System.out.print(Table_data + "  ");
                }
                System.out.println("");
                System.out.println("");
            }


            driver.findElement(By.xpath("//img[@src='/temp/iconcache/applications/16x16/document.png']")).click();
            Thread.sleep(3000);

        } catch (StaleElementReferenceException e) {
            System.out.println("Element is not attached to the page document "
                    + e.getStackTrace());
        } catch (NoSuchElementException e) {
            System.out.println("Element " + menu + " was not found in DOM "
                    + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Element " + menu + " was not clickable "
                    + e.getStackTrace());
        }


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
            driver.switchTo().frame(parentFrame);
            driver.switchTo().frame(childFrame);

        } catch (NoSuchFrameException e) {
            System.out.println("Your result : - " + e.getMessage());
        }

    }

    public static void tableData(List<WebElement> parentTable, String col, String row, String input) {

        for (int i = 0; i < parentTable.size(); i++) {

            List<WebElement> column = parentTable.get(i).findElements(By.tagName(col));
            System.out.println("Total Col are :- " + column.size());

            List<WebElement> rows = parentTable.get(i).findElements(By.tagName(row));
            System.out.println("Total rows are :- " + rows.size());

            if(column.get(1).findElements(By.tagName("input")).size()!=0) {
                //its input box
                column.get(1).findElement(By.tagName("input")).sendKeys("Hello");
            }else {
                column.get(1).findElement(By.xpath("//textarea[@class='scContentControlMemo']")).sendKeys("Hello");

            }
            int value = column.get(1).findElements(By.tagName("input")).size();

            column.get(1).findElement(By.tagName("input")).sendKeys("Hello");


        }


    }


}
