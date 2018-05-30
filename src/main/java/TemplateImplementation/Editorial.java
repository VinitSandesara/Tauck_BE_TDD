package TemplateImplementation;

import Locators.CommonLocators;
import NodeAndComponentConfig.navigateToNode;
import Util.Config;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class Editorial extends globalTemplateImplementation {

    public Editorial(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    @FindBy(xpath = CommonLocators.TABLE)
    public List<WebElement> _table;

    @FindBy(xpath = CommonLocators.CONTAINER_OF_CONTENT)
    public WebElement _contentArea;

    @FindBy(xpath = CommonLocators.TEXTAREA_TEXTBOX)
    public WebElement _textAreaTextBox;

    @FindBy(linkText = CommonLocators.SHOW_EDITOR_LINK)
    public WebElement _showEditorLink;

    @FindBy(id = CommonLocators.RICH_TEXT_EDITOR_TEXT_AREA)
    public WebElement _richTextEditorTextArea;

    @FindBy(id = CommonLocators.HTML_EDITOR_ACCEPT_BUTTON)
    public WebElement _htmlEditorAcceptButton;

    @FindBy(linkText = CommonLocators.EDIT_HTML_LINK)
    public WebElement _editHtmlLink;

    @FindBy(xpath = CommonLocators.HTML_EDITOR_TEXT_AREA)
    public WebElement _htmlEditorTextArea;

    @FindBy(linkText = CommonLocators.SAVE_CONTENT_CHANGE)
    public WebElement _save;

    public Editorial editorialHero_content_HeroSettings(String inputData) throws InterruptedException, IOException {

        try {
            Thread.sleep(3000);
         // Before filling content screenshot
            scrollToElement(_table.get(_table.size()-1));
            highlightElement(_contentArea);
            test.addScreenCaptureFromPath(captureScreen());

            fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
            _save.click();
            navigateToWhichTauckNode(navigateToNode.EDITORIAL);

         // After Filling content screenshot
            highlightElement(_contentArea);
            test.addScreenCaptureFromPath(captureScreen());

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

    public Editorial headerMedia_content_HeaderMediaSettings(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        navigateToWhichTauckNode(navigateToNode.EDITORIAL);

       /* mapControlWithDataSource mapDataSource = new mapControlWithDataSource(driver);
        PageFactory.initElements(driver, mapDataSource);
        return mapDataSource;*/

        return this;
    }

    public Editorial textCopyFolder_Content_SingleRichText(String inputData) throws InterruptedException {

        // Thread.sleep(2000);
        waitForPageLoad(20);
        _editHtmlLink.click();
        //  Thread.sleep(2000);
        waitForPageLoad(20);
        switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
        _htmlEditorTextArea.sendKeys(inputData);
        _htmlEditorAcceptButton.click();
        waitForPageLoad(20);
        _save.click();
        return this;
    }

    public Editorial authorProfiles_content_AuthorProfile(String inputData) throws InterruptedException {

        Thread.sleep(3000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        waitForPageLoad(20);
        _save.click();
        return this;
    }

    public Editorial editorialQuotes_content_Quotes(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        return this;
    }

    public Editorial flexCard_Content_CardSettings(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        return this;
    }


    public Editorial gridMediaFolder_content_GridMedia(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        return this;
    }

    public Editorial featuredBrand_Content_FeaturedBrandInfo(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        return this;
    }

    public Editorial halfWidthMeida_Content_Segment(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        return this;
    }


    public Editorial mediaCarousel_content_CardSettings(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        return this;
    }

    public Editorial categoryCards_content_CategoryCardInfo(String inputData) throws InterruptedException {

        Thread.sleep(2000);
        fillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
        _save.click();
        return this;
    }

    public Editorial mediaCarouselAndHalfWidthMedia_content_MultiListSelection(List<String> value) throws InterruptedException {

      /*  List<String> multiListValue = new ArrayList<String>();

        for(int c=0;c<value.size();c++) {
            multiListValue.add(value.get(c));
        }*/


        Actions actionOne = new Actions(driver);
        for (int i = 0; i < value.size(); i++) {
            WebElement element = driver.findElement(By.xpath("//option[text() = '" + value.get(i) + "']"));
            element.click();
            actionOne.doubleClick(element).perform();
            Thread.sleep(3000);
        }
        _save.click();
        return this;
    }




   /* @FindBy(id = CommonLocators.USERNAME)
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


    public EditorialTemplate launchSitecore() {
        driver.get(Config.getEnvDetails().get("url"));
        return this;
    }

    public EditorialTemplate login() throws InterruptedException {
        waitForPageLoad(30);
        _username.sendKeys("vinit");
        _password.sendKeys("vinit");
        _password.sendKeys(Keys.ENTER);
        return this;
    }

    public EditorialTemplate goToContentEditor() throws InterruptedException {
        waitForPageLoad(30);
        _contentEditor.click();
        return this;
    }

    public EditorialTemplate navigateToTemplateOrTemplateComponent() throws InterruptedException {
        waitForPageLoad(30);
        _searchTextBox.sendKeys(navigateToTemplateOrComponent.NAVIGATE_TO_HOME_TEMPLATE);
        _searchTextBox.sendKeys(Keys.ENTER);
        _searchResultCloseIcon.click();
        _searchTextBox.clear();
        return this;
    }

    public EditorialTemplate homeInsertEditorial() {

        waitForElementToBeVisible(_newPaneList);
        List<WebElement> listofComponent = _newPaneList.findElements(By.tagName("a"));
        System.out.println("Total length is :- " + listofComponent.size());

        int panCounter = 0;
        while (listofComponent.get(panCounter).getAttribute("title").equalsIgnoreCase(navigateToTemplateOrComponent.CREATE_EDITORIAL_TEMPLATE) != true) {
            System.out.println("Name is :- " + listofComponent.get(panCounter).getAttribute("title"));
            panCounter++;
        }

        listofComponent.get(panCounter).click();
        return this;

    }

    public EditorialTemplate switchToFrame() {

        try {
            waitForFrame(10,Config.CHILD_FRAME);
            driver.switchTo().frame(Config.PARENT_FRAME);
            driver.switchTo().frame(Config.CHILD_FRAME);

        } catch (NoSuchFrameException e) {
            System.out.println("Your result : - " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public EditorialTemplate createEditorialTemplateAndComponentInside() {

        _childFrameTextbox.sendKeys(navigateToTemplateOrComponent.NAME_IT_EDITORIAL_TEMPLATE);
        _childFrameTextbox.sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();
        return this;
    }  */


} // EditorialTemplate class is closing
