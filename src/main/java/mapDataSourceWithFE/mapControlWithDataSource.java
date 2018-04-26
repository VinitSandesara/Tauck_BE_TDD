package mapDataSourceWithFE;

import Locators.MapDataSourceWithFEControl.editorial_FE_Control;
import Locators.MapDataSourceWithFEControl.mapDataSourceWithControlLocators;
import TemplateImplementation.Editorial;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;

public class mapControlWithDataSource extends globalTemplateImplementation {


    public mapControlWithDataSource(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = mapDataSourceWithControlLocators.PRESENTATION)
    public WebElement _presentations;

    @FindBy(linkText = mapDataSourceWithControlLocators.DETAILS)
    public WebElement _details;

    @FindBy(id = mapDataSourceWithControlLocators.FINAL_LAYOUT)
    public WebElement _finalLayout;

    @FindBy(xpath = mapDataSourceWithControlLocators.EDITORIAL_LAYOUT)
    public List<WebElement> _editorialLayout;

    @FindBy(linkText = mapDataSourceWithControlLocators.DEVICE_EDITOR_CONTROLS)
    public WebElement _deviceEditorControls;

    @FindBy(xpath = mapDataSourceWithControlLocators.PRE_ADDED_CONTROLS)
    public List<WebElement> _preAddedControls;

    @FindBy(xpath = mapDataSourceWithControlLocators.DEVICE_EDITOR_CONTROLS_REMOVE_BUTTON)
    public WebElement _deviceEditorControlsRemoveButton;

    @FindBy(xpath = mapDataSourceWithControlLocators.DEVICE_EDITOR_CONTROLS_ADD_BUTTON)
    public WebElement _deviceEditorControlsAddButton;

    @FindBy(id = mapDataSourceWithControlLocators.SELECT_A_RENDERING_SELECT_BUTTON)
    public WebElement _selectARenderingSelectButton;

    @FindBy(id = mapDataSourceWithControlLocators.DEVICE_EDITOR_OK_BUTTON)
    public WebElement _deviceEditorOkButton;

    @FindBy(id = mapDataSourceWithControlLocators.LAYOUT_DETAILS_OK_BUTTON)
    public WebElement _layoutDetailsOkButton;

    @FindBy(xpath = mapDataSourceWithControlLocators.PLACEHOLDER_DATASOURCE)
    public List<WebElement> _placeholderAndDataSource;

    @FindBy(xpath = mapDataSourceWithControlLocators.PLACEHOLDER_DATASOURCE_OK_BUTTON)
    public WebElement _placeHolderDataSourceOkButton;


    public mapControlWithDataSource clickPresentationLink() throws Exception {
        waitForElementToBeDisplay(_presentations,10,true);
        _presentations.click();
        return this;

    }


    public mapControlWithDataSource clickDetailsLink() throws Exception {
        waitForElementToBeDisplay(_details,10,true);
        _details.click();
        return this;

    }

    public mapControlWithDataSource clickFinalLayoutTabInsideLayoutDetailsDialog() throws Exception {
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
        waitForElementToBeVisible(_finalLayout);
        _finalLayout.click();
        return this;

    }

    public mapControlWithDataSource navigateToDeviceEditor() throws Exception {
        //waitForPageLoad(30);
        waitForElementToBeVisible(_editorialLayout.get(1));
        _editorialLayout.get(1).click();
        return this;

    }

    public mapControlWithDataSource clickControlsInsideDeviceEditor() throws InterruptedException {
        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME_TWO);
        waitForElementToBeVisible(_deviceEditorControls);
        _deviceEditorControls.click();
        return this;

    }

    public editorialTemplateControls clickControlsInsideDeviceEditorForMappingDataSourceSequentially() throws InterruptedException {
        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME_TWO);
        waitForElementToBeVisible(_deviceEditorControls);
        _deviceEditorControls.click();

        editorialTemplateControls editorialControls = new editorialTemplateControls(driver);
        PageFactory.initElements(driver, editorialControls);
        return editorialControls;

    }

    public mapControlWithDataSource removePreAddedFEControls() throws InterruptedException {
        waitForElementToBeVisible(_deviceEditorControlsRemoveButton);
        _preAddedControls.get(_preAddedControls.size() - 1).click();

        while (_deviceEditorControlsRemoveButton.getAttribute("disabled") == null) {

            _deviceEditorControlsRemoveButton.click();

        }

        // Save the changes
        _deviceEditorOkButton.click();
        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
        waitForElementToBeVisible(_layoutDetailsOkButton);
        _layoutDetailsOkButton.click();

        return this;

    }

    public mapControlWithDataSource addNewControls() {
        waitForElementToBeVisible(_deviceEditorControlsAddButton);
        _deviceEditorControlsAddButton.click();
        return this;

    }




    public editorialTemplateControls selectWhichControlsToAdd() throws InterruptedException {

        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME_THREE);

        editorialTemplateControls editorialControls = new editorialTemplateControls(driver);
        PageFactory.initElements(driver, editorialControls);
        return editorialControls;

    }

    public mapControlWithDataSource inputPlaceHolderAndDataSource(String placeholder, String datasource) throws InterruptedException {
        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME_THREE);

       // List<String> splitPlaceHolderDataSource = Arrays.asList(input.split("\\|"));

        waitForPageLoad(20);
        /*for(int i=0;i<_placeholderAndDataSource.size();i++) {
            _placeholderAndDataSource.get(i).sendKeys(splitPlaceHolderDataSource.get(i));
        }*/

        _placeholderAndDataSource.get(0).sendKeys(placeholder);
        _placeholderAndDataSource.get(1).sendKeys(datasource);

        _placeHolderDataSourceOkButton.click();

        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME_TWO);

      /*  waitForElementToBeVisible(_deviceEditorOkButton);
        _deviceEditorOkButton.click();


        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
        waitForElementToBeVisible(_layoutDetailsOkButton);
        _layoutDetailsOkButton.click();*/



        return this;
    }

    public mapControlWithDataSource saveAndCloseDeviceEditorAndLayoutDetails() throws InterruptedException {

        waitForElementToBeVisible(_deviceEditorOkButton);
        _deviceEditorOkButton.click();
        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
        waitForElementToBeVisible(_layoutDetailsOkButton);
        _layoutDetailsOkButton.click();

        return this;
    }

}
