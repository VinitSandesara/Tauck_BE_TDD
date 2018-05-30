package base;

import Util.utility;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class driverBase {


    public WebDriver driver;
    public ExtentTest test;

    public driverBase() {

    }

    public driverBase(WebDriver driver, ExtentTest test){
        this.driver=driver;
        this.test=test;
    }



}
