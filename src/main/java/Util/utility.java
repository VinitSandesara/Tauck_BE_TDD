package Util;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class utility extends AssertUtil {

    public utility(WebDriver driver) {
        super(driver);
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

        parentTable = driver.findElements(By.xpath("//td[text() = 'sitecore\\admin']"));

        System.out.println("Total rows are :- " + parentTable.size());
        Random r = new Random();
        int n = r.nextInt((5 - 1) + 1) + 1;
        System.out.println("Random number is : - -- " + n);
        parentTable.get(n).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@data-sc-id='KickButton']")).click();

    }



}
