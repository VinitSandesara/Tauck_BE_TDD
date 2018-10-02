package TemplateImplementation;

import FeedContent.feedContent;
import Locators.CommonLocators;
import Locators.HomePageLocators;
import Util.Config;
import Util.utility;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import mapDataSourceWithFE.mapControlWithDataSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class globalTemplateImplementation extends utility {

    int maxTimeInSeconds = 10;
    public static int counter = 0;

    public globalTemplateImplementation(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    @FindBy(id = CommonLocators.USERNAME)
    public WebElement _username;

    @FindBy(id = CommonLocators.PASSWORD)
    public WebElement _password;

    @FindBy(id = CommonLocators.SEARCHTEXTBOX)
    public WebElement _searchTextBox;

    @FindBy(xpath = CommonLocators.SEARCHRESULTCLOSEICON)
    public WebElement _searchResultCloseIcon;

    @FindBy(xpath = CommonLocators.SEARCH_RESULT_NO_FOUND)
    public WebElement _noSearchResultFound;

    @FindBy(xpath = CommonLocators.IF_COMPONENT_ALREADY_MAPED_IT_FURTHER_REQUIRED_DELETE)
    public WebElement _breakingLinksDialog;

    @FindBy(linkText = CommonLocators.CONTENT_EDITOR)
    public WebElement _contentEditor;

    @FindBy(id = CommonLocators.NEW_PANE_LIST)
    public WebElement _newPaneList;

    @FindBy(id = CommonLocators.CHILD_FRAME_TEXTBOX)
    public WebElement _childFrameTextbox;

    @FindBy(linkText = CommonLocators.SHOW_EDITOR_LINK)
    public WebElement _showEditorLink;

    @FindBy(id = CommonLocators.RICH_TEXT_EDITOR_TEXT_AREA)
    public WebElement _richTextEditorTextArea;

    @FindBy(id = CommonLocators.HTML_EDITOR_ACCEPT_BUTTON)
    public WebElement _htmlEditorAcceptButton;

    @FindBy(id = CommonLocators.TEMPLATE_NAME)
    public WebElement _insertFromTemplate_templateNmae;

    @FindBy(id = CommonLocators.ITEM_NAME)
    public WebElement _insertFromTemplate_itemNmae;

    @FindBy(xpath = CommonLocators.HTML_EDITOR_TEXT_AREA)
    public WebElement _htmlEditorTextArea;

    @FindBy(linkText = CommonLocators.EDIT_HTML_LINK)
    public WebElement _editHtmlLink;

    @FindBy(linkText = CommonLocators.EDIT_HTML_LINK)
    public List<WebElement> _multipleEditHtmlLink;

    @FindBy(xpath = CommonLocators.LAUNCHPAD_ICON)
    public List<WebElement> _lunchPadIcon;

    @FindBy(xpath = CommonLocators.DELETE_LINK)
    public WebElement _deleteLink;

    @FindBy(xpath = CommonLocators.DELETE_OK_BUTTON)
    public WebElement _deleteOkButton;

    @FindBy(xpath = CommonLocators.LOG_OUT)
    public WebElement _logOut;

    @FindBy(xpath = CommonLocators.ALREADY_EXPANDED_CONTENT_SECTIONS_PANEL)
    public List<WebElement> _collapsedAlreadyExpandedSections;

    @FindBy(xpath = CommonLocators.TOTAL_CONTENT_SECTIONS_PANEL)
    public List<WebElement> _howManyContentSectionsPanelAreThere;

    public globalTemplateImplementation launchSitecore() {
        driver.get(Config.getEnvDetails().get("url"));
        return this;
    }

    public globalTemplateImplementation logOut() {
        waitForElementToBeVisible(_logOut);
        _logOut.click();
        return this;
    }

    public globalTemplateImplementation checkAndCollapsedAlreadyExpandedContentSectionsPanel() throws Exception {

        waitForPageLoad(10);
        // Note : Here i tried the loop thru i=0 but somehow it just collapsed 1st panel only but if i try this way it does collapsed all panels
        for (int i = _collapsedAlreadyExpandedSections.size(); i > 0; i--) {
            waitForPageLoad(10);
            _collapsedAlreadyExpandedSections.get(i - 1).click();
        }

        return this;
    }

    public feedContent expandSections(String sectionName) throws InterruptedException {

        counter = 0;

        waitForPageLoad(10);
        while (!_howManyContentSectionsPanelAreThere.get(counter).getAttribute("id").startsWith(sectionName)) {
            counter++;
        }

        WebElement element = driver.findElement(By.xpath(" //div[starts-with(@id,'" + sectionName + "')] "));

        if (element.getAttribute("class").equalsIgnoreCase("scEditorSectionCaptionCollapsed")) {
            element.click();
        }

        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;


    }


    public globalTemplateImplementation insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(String templateName, String itemName) throws InterruptedException {

        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", _insertFromTemplate_templateNmae);
        _insertFromTemplate_templateNmae.sendKeys(templateName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", _insertFromTemplate_itemNmae);
        _insertFromTemplate_itemNmae.sendKeys(itemName);
        _insertFromTemplate_itemNmae.sendKeys(Keys.ENTER);

        return this;

    }

    public feedContent insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(String templateName, String itemName, String className) throws InterruptedException {

        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", _insertFromTemplate_templateNmae);
        _insertFromTemplate_templateNmae.sendKeys(templateName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", _insertFromTemplate_itemNmae);
        _insertFromTemplate_itemNmae.sendKeys(itemName);
        _insertFromTemplate_itemNmae.sendKeys(Keys.ENTER);

        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;

    }


    public globalTemplateImplementation login() throws InterruptedException {
        // waitForPageLoad(30);
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(_username));

        int userEndsWith = ThreadLocalRandom.current().nextInt(1, 6);

        _username.sendKeys(Config.getEnvDetails().get("username") + userEndsWith);
        _password.sendKeys(Config.getEnvDetails().get("password") + userEndsWith);
        _password.sendKeys(Keys.ENTER);
         test.info("Login with user :-- >" + Config.getEnvDetails().get("username") + userEndsWith);

        try {
            if (isElementPresent(_lunchPadIcon) != true) {
                wait.until(ExpectedConditions.elementToBeClickable(_contentEditor));
                _contentEditor.click();
            }

        } catch (Throwable t) {
            kickOffUser(userEndsWith);
            wait.until(ExpectedConditions.elementToBeClickable(_contentEditor));
            try {
                _contentEditor.click();
            } catch (Exception e) {

            }

        }

        return this;
    }

    public globalTemplateImplementation goToContentEditorIfNotKickOffUser() throws InterruptedException {


        try {
            if (isElementPresent(_lunchPadIcon) != true) {
                waitForPageLoad(30);
                _contentEditor.click();
            }

        } catch (Throwable t) {
            kickOffUser(1);
            waitForPageLoad(30);
            try {
                _contentEditor.click();
            } catch (Exception e) {

            }

        }


        return this;
    }

    public globalTemplateImplementation goToContentEditor() throws InterruptedException {
        waitForPageLoad(30);
        _contentEditor.click();
        return this;
    }

    public globalTemplateImplementation navigateToWhichTauckNode(String nodeName) throws InterruptedException {
        waitForPageLoad(30);
        _searchTextBox.sendKeys(nodeName);
        _searchTextBox.sendKeys(Keys.ENTER);

        _searchResultCloseIcon.click();
        _searchTextBox.clear();

        /*EditorialTemplate editorial = new EditorialTemplate(driver);
        PageFactory.initElements(driver, editorial);
        return editorial;*/

        return this;
    }

    public HomePage navigateToWhichTauckNode_ForSectionInput(String nodeName) throws InterruptedException {
        waitForPageLoad(30);
        _searchTextBox.sendKeys(nodeName);
        _searchTextBox.sendKeys(Keys.ENTER);

        _searchResultCloseIcon.click();
        _searchTextBox.clear();

        HomePage homepage = new HomePage(driver, test);
        PageFactory.initElements(driver, homepage);
        return homepage;


    }


    public globalTemplateImplementation checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(String nodeName) throws InterruptedException {

        waitForPageLoad(30);
        _searchTextBox.sendKeys(nodeName);
        _searchTextBox.sendKeys(Keys.ENTER);

        try {
            _noSearchResultFound.getText().equalsIgnoreCase("There are no matches.");
            _searchResultCloseIcon.click();
            _searchTextBox.clear();
        } catch (Throwable t) {
            _searchResultCloseIcon.click();
            _searchTextBox.clear();
            _deleteLink.click();
            switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
            _deleteOkButton.click();
            //  switchBackIFrameToItsOriginalState();
            try {
                waitForPageLoad(30);
                switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
                _breakingLinksDialog.getText().equalsIgnoreCase("Breaking Links");
                _deleteOkButton.click();
                waitForPageLoad(30);
                switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
                _deleteOkButton.click();
            } catch (Throwable throwable) {
                System.out.println("The Component that you are trying to delete has not yet been mapped with FE Control....");
                switchBackIFrameToItsOriginalState();
            }

        }

        return this;
    }

    public boolean checkWhetherParentNodeIsPresentOrNot(String nodeName) throws InterruptedException {

        waitForPageLoad(30);
        _searchTextBox.sendKeys(nodeName);
        _searchTextBox.sendKeys(Keys.ENTER);

        try {
            _noSearchResultFound.getText().equalsIgnoreCase("There are no matches.");
            _searchResultCloseIcon.click();
            _searchTextBox.clear();
            return true;
        } catch (Throwable throwable) {
            _searchResultCloseIcon.click();
            _searchTextBox.clear();
            return false;
        }

    }


    // This overloaded method is implemented for editoial sub-templates.
    public feedContent navigateToWhichTauckNode(String nodeName, String nameOfPreFeededComponentName) throws InterruptedException {
        waitForPageLoad(30);
        _searchTextBox.sendKeys(nodeName + nameOfPreFeededComponentName);
        _searchTextBox.sendKeys(Keys.ENTER);
        _searchResultCloseIcon.click();
        _searchTextBox.clear();

        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;


    }


    public globalTemplateImplementation verifyPreFeededSubComponent(String nodeToRedirect, List<String> listOfPreFeededNodes) throws InterruptedException, IOException {


        try {
            for (int i = 0; i < listOfPreFeededNodes.size(); i++) {

                navigateToWhichTauckNode(nodeToRedirect + "/" + listOfPreFeededNodes.get(i));

                WebElement element = driver.findElement(By.linkText(listOfPreFeededNodes.get(i)));
                String actualText = element.getText();

                verifyAssertEquals(actualText, listOfPreFeededNodes.get(i), "Pre feeded component inside Sub-template");

                highlightElement(element);
                test.addScreenCaptureFromPath(captureScreen());
            }
        } catch (ElementNotVisibleException env) {
            captureErrorWithScreenShotForReporting("*****Element is present in DOM but not visible on the page*****" + env.getMessage());

        } catch (NoSuchElementException ne) {
            captureErrorWithScreenShotForReporting("*****The element could not be located on the page.*****" + ne.getMessage());
        } catch (StaleElementReferenceException se) {
            captureErrorWithScreenShotForReporting("*****Either the element has been deleted entirely or the element is no longer attached to DOM.*****" + se.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            test.fail(MarkupHelper.createLabel("***** Something went wrong with Hero Eyebrow text please check manually... *****", ExtentColor.RED));
            Assert.fail();
        }


        return this;
    }


    public mapControlWithDataSource navigateToWhichTauckNodeForMappingDataSourceWithFrontEndControl(String nodeName) throws InterruptedException {
        waitForPageLoad(30);
        _searchTextBox.sendKeys(nodeName);
        _searchTextBox.sendKeys(Keys.ENTER);
        _searchResultCloseIcon.click();
        _searchTextBox.clear();

        mapControlWithDataSource mapDataSource = new mapControlWithDataSource(driver, test);
        PageFactory.initElements(driver, mapDataSource);
        return mapDataSource;

    }

    public globalTemplateImplementation rightClickInsertTemplateOrComponent(String templateOrComponntName) {

        waitForElementToBeVisible(_newPaneList);
        List<WebElement> listofComponent = _newPaneList.findElements(By.tagName("a"));
        System.out.println("Total length is :- " + listofComponent.size());

        int panCounter = 0;
        while (listofComponent.get(panCounter).getAttribute("title").equalsIgnoreCase(templateOrComponntName) != true) {
            System.out.println("Name is :- " + listofComponent.get(panCounter).getAttribute("title"));
            panCounter++;
        }

        listofComponent.get(panCounter).click();
        return this;


    }

    public globalTemplateImplementation switchToContentIframeDialog(String parent, String child) throws InterruptedException {


        Thread.sleep(2000);
        try {

            while ((maxTimeInSeconds) != 0) {
                // driver.switchTo().frame("jqueryModalDialogsFrame");
                //  driver.switchTo().frame("scContentIframeId0");
                driver.switchTo().frame(parent);
                driver.switchTo().frame(child);
                break;
            }


        } catch (NoSuchFrameException e) {
            System.out.println("Your result : - " + e.getMessage());
        }
        return this;
    }

    public globalTemplateImplementation switchBackIFrameToItsOriginalState() {
        driver.switchTo().defaultContent();
        return this;
    }

    public Object createTemplateOrTemplateComponent(String templateOrComponentName, String className) throws InterruptedException {


        Thread.sleep(3000);
        _childFrameTextbox.sendKeys(templateOrComponentName);
        _childFrameTextbox.sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();

        if (className.equalsIgnoreCase("testEditorial")) {
            Editorial editorial = new Editorial(driver, test);
            PageFactory.initElements(driver, editorial);
            return editorial;
        } else if (className.equalsIgnoreCase("mapControlWithDataSource")) {
            mapControlWithDataSource mapControls = new mapControlWithDataSource(driver, test);
            PageFactory.initElements(driver, mapControls);
            return mapControls;
        }

        return this;
    }


    public feedContent createTemplateOrTemplateComponent(String templateOrComponentName) throws InterruptedException {


        Thread.sleep(2000);
        _childFrameTextbox.sendKeys(templateOrComponentName);
        _childFrameTextbox.sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();


        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;


    }


    public void fillContentFields(List<WebElement> parentTable, String col, String row, String input, String _textAreaTextBox, String inputData) throws InterruptedException {

        List<String> temp = new ArrayList<String>();
        if (!temp.isEmpty()) {
            temp.clear();
        }
        List<String> inputDataList = Arrays.asList(inputData.split("\\|"));

        for (int i = 0; i < inputDataList.size(); i++) {

            temp.add(inputDataList.get(i));

        }

        for (int i = 0; i < parentTable.size(); i++) {

            List<WebElement> column = parentTable.get(i).findElements(By.tagName(col));
            System.out.println("Total Col are :- " + column.size());

            List<WebElement> rows = parentTable.get(i).findElements(By.tagName(row));
            System.out.println("Total rows are :- " + rows.size());

            try {
                //its input box
                column.get(1).findElement(By.tagName("input")).sendKeys(temp.get(i));

            } catch (Throwable e) {
                try {
                    //column.get(1).findElement(By.xpath(_textAreaTextBox)).sendKeys(temp.get(i));
                    // Note : Above line commented because it was not helping for home template - Text Area. Example - Travelling with tauck sections
                    column.get(1).findElement(By.tagName("textarea")).sendKeys(temp.get(i));
                } catch (Throwable t) {

                    try {
                        waitForPageLoad(20);
                        _editHtmlLink.click();
                        waitForPageLoad(20);
                        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
                        _htmlEditorTextArea.sendKeys(temp.get(i));
                        _htmlEditorAcceptButton.click();
                        waitForPageLoad(20);
                    } catch (Throwable t1) {
                        continue;
                    }
                }

            }


          /*  if (column.get(1).findElements(By.tagName("input")).size() != 0) {
                //its input box
                column.get(1).findElement(By.tagName("input")).sendKeys(temp.get(i));
            } else {
                column.get(1).findElement(By.xpath(_textAreaTextBox)).sendKeys(temp.get(i));

            }*/

        }

    }


    public void clearAndFillContentFields(List<WebElement> parentTable, String col, String row, String input, String _textAreaTextBox, String inputData) throws InterruptedException {

        int totalEditLinks = 0;

        List<String> temp = new ArrayList<String>();
        if (!temp.isEmpty()) {
            temp.clear();
        }
        List<String> inputDataList = Arrays.asList(inputData.split("\\|"));

        for (int i = 0; i < inputDataList.size(); i++) {

            temp.add(inputDataList.get(i));

        }

        for (int i = 0; i < parentTable.size(); i++) {

            List<WebElement> column = parentTable.get(i).findElements(By.tagName(col));
            System.out.println("Total Col are :- " + column.size());

            List<WebElement> rows = parentTable.get(i).findElements(By.tagName(row));
            System.out.println("Total rows are :- " + rows.size());

            try {
                //its input box
                clearTextboxPreFeededData(column.get(1).findElement(By.tagName("input")));
                column.get(1).findElement(By.tagName("input")).sendKeys(temp.get(i));

                // Reason to enter here is for the scenario say you wanna click on "Edit HTML" link but before that feild there is a field where you need to input image path or any path
                // in this case what happens is after you enter path you need to enter to reflect path value (image) before going on to next field and input or click. Example URL is
                // "/sitecore/content/Tauck/data/ships/le-ponant/ship-partners/le-ponant-partners/ponant" (try in QA env) notice there is "Content" field right after "Logo" field

                column.get(1).findElement(By.tagName("input")).sendKeys(Keys.ENTER);

            } catch (Throwable e) {
                try {
                    //column.get(1).findElement(By.xpath(_textAreaTextBox)).sendKeys(temp.get(i));
                    // Note : Above line commented because it was not helping for home template - Text Area. Example - Travelling with tauck sections
                    clearTextboxPreFeededData(column.get(1).findElement(By.tagName("textarea")));
                    column.get(1).findElement(By.tagName("textarea")).sendKeys(temp.get(i));
                } catch (Throwable t) {

                    try {
                        column.get(1).findElement(By.tagName("select")).sendKeys(temp.get(i));

                    } catch (Throwable b) {


                        try {
                            waitForPageLoad(20);
                            _multipleEditHtmlLink.get(totalEditLinks).click();
                            totalEditLinks++;
                            waitForPageLoad(20);
                            switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
                            clearTextboxPreFeededData(_htmlEditorTextArea);
                            _htmlEditorTextArea.sendKeys(temp.get(i));
                            _htmlEditorAcceptButton.click();
                            waitForPageLoad(20);
                        } catch (Throwable t1) {
                            continue;
                        }
                    }
                } //

            }


          /*  if (column.get(1).findElements(By.tagName("input")).size() != 0) {
                //its input box
                column.get(1).findElement(By.tagName("input")).sendKeys(temp.get(i));
            } else {
                column.get(1).findElement(By.xpath(_textAreaTextBox)).sendKeys(temp.get(i));

            }*/

        }

    }


    public globalTemplateImplementation textCopyFolder_Content_SingleRichText(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        _showEditorLink.click();
        Thread.sleep(2000);
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
        driver.switchTo().frame("Editor_contentIframe");


        //_richTextEditorTextArea.click();
        _richTextEditorTextArea.sendKeys(inputData);
        return this;
    }


} // Class ending here
