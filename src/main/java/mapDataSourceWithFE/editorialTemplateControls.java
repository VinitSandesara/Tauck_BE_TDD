package mapDataSourceWithFE;

import Locators.MapDataSourceWithFEControl.editorial_FE_Control;
import Locators.MapDataSourceWithFEControl.mapDataSourceWithControlLocators;
import Util.DataUtil;
import Util.Xls_Reader;
import Util.excelConfig;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class editorialTemplateControls extends mapControlWithDataSource {

    public editorialTemplateControls(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    @FindBy(linkText = editorial_FE_Control.HEADER_MODULE)
    public WebElement _headerModule;

    @FindBy(id = editorial_FE_Control.OK_BUTTON)
    public WebElement _okButton;

    @FindBy(id = editorial_FE_Control.CHECKBOX)
    public WebElement _checkbox;

    public editorialTemplateControls addEditorialTemplateFEControl(String controlName) {
        waitForElementToBeVisible(_headerModule);
        driver.findElement(By.linkText(controlName)).click();
        return this;
    }

    public editorialTemplateControls openPropertyDialogBoxCheckbox() {
        waitForElementToBeVisible(_checkbox);
        _checkbox.click();
        return this;
    }

    public mapControlWithDataSource clickSelectButton() {
        waitForElementToBeVisible(_okButton);
        _okButton.click();

        mapControlWithDataSource mapControls = new mapControlWithDataSource(driver , test);
        PageFactory.initElements(driver, mapControls);
        return mapControls;

    }

}

