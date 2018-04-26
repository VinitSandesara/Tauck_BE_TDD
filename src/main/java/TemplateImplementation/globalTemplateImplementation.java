package TemplateImplementation;

import Locators.CommonLocators;
import Util.Config;
import Util.utility;
import mapDataSourceWithFE.mapControlWithDataSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class globalTemplateImplementation extends utility {

    int maxTimeInSeconds = 10;


    public globalTemplateImplementation(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = CommonLocators.USERNAME)
    public WebElement _username;

    @FindBy(id = CommonLocators.PASSWORD)
    public WebElement _password;

    @FindBy(id = CommonLocators.SEARCHTEXTBOX)
    public WebElement _searchTextBox;

    @FindBy(xpath = CommonLocators.SEARCHRESULTCLOSEICON)
    public WebElement _searchResultCloseIcon;

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

    @FindBy(xpath = CommonLocators.HTML_EDITOR_TEXT_AREA)
    public WebElement _htmlEditorTextArea;

    @FindBy(linkText = CommonLocators.EDIT_HTML_LINK)
    public WebElement _editHtmlLink;

    public globalTemplateImplementation launchSitecore() {
        driver.get(Config.getEnvDetails().get("url"));
        return this;
    }


    public globalTemplateImplementation login() throws InterruptedException {
        waitForPageLoad(30);
        _username.sendKeys(Config.getEnvDetails().get("username"));
        _password.sendKeys(Config.getEnvDetails().get("password"));
        _password.sendKeys(Keys.ENTER);
        return this;
    }

    public globalTemplateImplementation goToContentEditorIfNotKickOffUser() throws InterruptedException {

        try {

            waitForPageLoad(30);
            _contentEditor.click();
            return this;

        } catch (Throwable t) {
            kickOffUser();
            waitForPageLoad(30);
            _contentEditor.click();
            return this;

        }


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

        /*Editorial editorial = new Editorial(driver);
        PageFactory.initElements(driver, editorial);
        return editorial;*/

        return this;
    }

    public mapControlWithDataSource navigateToWhichTauckNodeForMappingDataSourceWithFrontEndControl(String nodeName) throws InterruptedException {
        waitForPageLoad(30);
        _searchTextBox.sendKeys(nodeName);
        _searchTextBox.sendKeys(Keys.ENTER);
        _searchResultCloseIcon.click();
        _searchTextBox.clear();

        mapControlWithDataSource mapDataSource = new mapControlWithDataSource(driver);
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

    public Object createTemplateOrTemplateComponent(String templateOrComponentName, String className) throws InterruptedException {


        Thread.sleep(3000);
        _childFrameTextbox.sendKeys(templateOrComponentName);
        _childFrameTextbox.sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();

        if (className.equalsIgnoreCase("testEditorial")) {
            Editorial editorial = new Editorial(driver);
            PageFactory.initElements(driver, editorial);
            return editorial;
        } else if (className.equalsIgnoreCase("mapControlWithDataSource")) {
            mapControlWithDataSource mapControls = new mapControlWithDataSource(driver);
            PageFactory.initElements(driver, mapControls);
            return mapControls;
        }

        return this;
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
                    column.get(1).findElement(By.xpath(_textAreaTextBox)).sendKeys(temp.get(i));
                } catch (Throwable t) {
                    waitForPageLoad(20);
                    _editHtmlLink.click();
                    waitForPageLoad(20);
                    switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
                    _htmlEditorTextArea.sendKeys(temp.get(i));
                    _htmlEditorAcceptButton.click();
                    waitForPageLoad(20);
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
