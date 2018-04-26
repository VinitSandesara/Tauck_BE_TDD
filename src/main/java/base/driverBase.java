package base;

import Util.utility;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class driverBase {


    public WebDriver driver;

    public driverBase() {

    }

    public driverBase(WebDriver driver){
        this.driver=driver;

    }



}
