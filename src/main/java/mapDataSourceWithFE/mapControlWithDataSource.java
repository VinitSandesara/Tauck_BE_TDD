package mapDataSourceWithFE;

import Locators.MapDataSourceWithFEControl.editorial_FE_Control;
import Locators.MapDataSourceWithFEControl.mapDataSourceWithControlLocators;
import TemplateImplementation.Editorial;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;

public class mapControlWithDataSource extends globalTemplateImplementation {


    public mapControlWithDataSource(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }


    @FindBy(xpath = mapDataSourceWithControlLocators.PRESENTATION)
    public WebElement _presentations;

    @FindBy(linkText = mapDataSourceWithControlLocators.DETAILS)
    public WebElement _details;

    @FindBy(id = mapDataSourceWithControlLocators.FINAL_LAYOUT)
    public WebElement _finalLayout;

    @FindBy(xpath = mapDataSourceWithControlLocators.EDITORIAL_LAYOUT)
    public List<WebElement> _editorialLayout;

    @FindBy(xpath = mapDataSourceWithControlLocators.FINALLAYOUT_DEFAULT_LINK)
    public List<WebElement> _finalLayoutDefaultLink;

    @FindBy(linkText = mapDataSourceWithControlLocators.SEARCH_FOR_CONTROL_FOLDER)
    public WebElement _searchForControlFolder;

    @FindBy(linkText = mapDataSourceWithControlLocators.DEVICE_EDITOR_CONTROLS)
    public WebElement _deviceEditorControls;

    @FindBy(xpath = mapDataSourceWithControlLocators.PRE_ADDED_CONTROLS)
    public List<WebElement> _preAddedControls;

    @FindBy(xpath = mapDataSourceWithControlLocators.DEVICE_EDITOR_CONTROLS_REMOVE_BUTTON)
    public WebElement _deviceEditorControlsRemoveButton;

    @FindBy(xpath = mapDataSourceWithControlLocators.DEVICE_EDITOR_CONTROLS_EDIT_BUTTON)
    public WebElement _deviceEditorControlsEditButton;

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

    @FindBy(id = mapDataSourceWithControlLocators.SELECT_RENDERING_CHECKBOX)
    public WebElement _selectRenderingcheckbox;

    @FindBy(id = mapDataSourceWithControlLocators.SELECT_RENDERING_RENDERING_DIALOG)
    public WebElement _renderingDialog;

    @FindBy(xpath = mapDataSourceWithControlLocators.MAXIMIZE_SELECT_RENDERING_DIALOG)
    public List<WebElement> _maximizeSelectARenderingDialog;


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
       /* waitForElementToBeVisible(_editorialLayout.get(1));
        _editorialLayout.get(1).click();*/

        waitForElementToBeVisible(_finalLayoutDefaultLink.get(1));
        Actions actionOne = new Actions(driver);
      //  _finalLayoutDefaultLink.get(1).click();
        actionOne.doubleClick(_finalLayoutDefaultLink.get(1)).perform();


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

        editorialTemplateControls editorialControls = new editorialTemplateControls(driver, test);
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

    public mapControlWithDataSource searchForControlFolderAndClick() {

        _searchForControlFolder.click();
        return this;
    }



    public editorialTemplateControls selectWhichControlsToAdd() throws InterruptedException {

        driver.switchTo().defaultContent();
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME_THREE);

        editorialTemplateControls editorialControls = new editorialTemplateControls(driver, test);
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

        clearTextboxPreFeededData(_placeholderAndDataSource.get(0));
        _placeholderAndDataSource.get(0).sendKeys(placeholder);
        clearTextboxPreFeededData(_placeholderAndDataSource.get(1));
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

    public mapControlWithDataSource checkAndRemovePreAddedControlsBeforeMappingIfPresent(List<String> listOfControls) {

        for (int i = 0; i < listOfControls.size(); i++) {

            for (int j = 0; j < _preAddedControls.size(); j++) {

                String value = _preAddedControls.get(j).findElement(By.tagName("b")).getText();

                if (_preAddedControls.get(j).findElement(By.tagName("b")).getText().equalsIgnoreCase(listOfControls.get(i))) {
                    _preAddedControls.get(j).click();
                   _deviceEditorControlsRemoveButton.click();
                }
            }
        }

        return this;
    }


    public mapControlWithDataSource checkAndMapPreAddedControlsIfPresent(String listOfControls) {



            for (int j = 0; j < _preAddedControls.size(); j++) {

                String value = _preAddedControls.get(j).findElement(By.tagName("b")).getText();

                if (_preAddedControls.get(j).findElement(By.tagName("b")).getText().equalsIgnoreCase(listOfControls)) {
                    _preAddedControls.get(j).click();
                    _deviceEditorControlsEditButton.click();
                    return this;
                }
            }


        return this;
    }


    public mapControlWithDataSource searchForControlFolderAndSelectControlFromFolder(List<String> listOfFolders, String nameOfControl) throws InterruptedException {

        driver.switchTo().defaultContent();
        driver.switchTo().frame(Config.PARENT_FRAME);

        // this is required to maximize select a rendering dialog window or else it doesn't click on folder.
        _maximizeSelectARenderingDialog.get(2).findElement(By.linkText("maximize")).click();
        driver.switchTo().frame(Config.CHILD_FRAME_THREE);

       // switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME_THREE);

        if(listOfFolders.size()!=2) {
            driver.findElement(By.linkText(listOfFolders.get(0))).click();
            _renderingDialog.findElement(By.linkText(nameOfControl)).click();
        }else {
            driver.findElement(By.linkText(listOfFolders.get(0))).click();
            _renderingDialog.findElement(By.linkText(listOfFolders.get(1))).click();
            _renderingDialog.findElement(By.linkText(nameOfControl)).click();
        }

        waitForElementToBeVisible(_selectRenderingcheckbox);
        _selectRenderingcheckbox.click();

        waitForElementToBeVisible(_deviceEditorOkButton);
        _deviceEditorOkButton.click();


        return this;
    }





} // Main class is closing...
