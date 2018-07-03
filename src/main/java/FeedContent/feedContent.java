package FeedContent;

import Locators.CommonLocators;
import Locators.HomePageLocators;
import TemplateImplementation.globalTemplateImplementation;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class feedContent extends globalTemplateImplementation {


    public feedContent(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }


    @FindBy(xpath = CommonLocators.TABLE)
    public List<WebElement> _table;

    @FindBy(xpath = CommonLocators.MULTIPLE_TABLES)
    public List<WebElement> _multipleTables;

    @FindBy(xpath = CommonLocators.CONTENT_TREELIST)
    public List<WebElement> _contentTreeList;


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

    @FindBy(xpath = HomePageLocators.CONTENT_SECTIONS_PANEL_TABLES)
    public List<WebElement> _homePageContentSectionsPanelTables;

    @FindBy(xpath = HomePageLocators.CONTENT_SECTIONS_PANEL_TABLES_ALTERNATIVE)
    public List<WebElement> _homePageContentSectionsPanelTotalTables;


    public feedContent feed_HomePage_Content_Sections_Panel(String inputData, int counter) throws IOException {

        try {
            Thread.sleep(3000);
            List<WebElement> totalInput = _homePageContentSectionsPanelTables.get(counter).findElements(By.tagName("table"));

            fillContentFields(totalInput, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
            _save.click();

            // After Filling content screenshot
            // highlightElement(_contentArea);
            //  test.addScreenCaptureFromPath(captureScreen());

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

    public feedContent clear_And_feed_HomePage_Content_Sections_Panel(String inputData, int counter) throws IOException {

        try {
            Thread.sleep(2000);

            List<WebElement> totalInput = _homePageContentSectionsPanelTables.get(counter).findElements(By.tagName("table"));

            clearAndFillContentFields(totalInput, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
            Thread.sleep(5000);
            _save.click();

            // After Filling content screenshot
            // highlightElement(_contentArea);
            //  test.addScreenCaptureFromPath(captureScreen());

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


    public feedContent feedContent_Fields_With_Data(String inputData, int whichTable) throws IOException {

        try {
            Thread.sleep(3000);

            List<WebElement> _tableWithPostition = _multipleTables.get(whichTable).findElements(By.tagName("table"));

            clearAndFillContentFields(_tableWithPostition, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
            _save.click();


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


    public feedContent fill_Component_Content_With_Data(String inputData) throws IOException {

        try {
            Thread.sleep(3000);
            // Before filling content screenshot
            //  scrollToElement(_table.get(_table.size()-1));
            //    highlightElement(_contentArea);
            //   test.addScreenCaptureFromPath(captureScreen());

            clearAndFillContentFields(_table, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);
            _save.click();

            // After Filling content screenshot
            //   highlightElement(_contentArea);
            //  test.addScreenCaptureFromPath(captureScreen());

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

    public feedContent MultiListSelection(List<String> value) throws InterruptedException {

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

    public feedContent mediaCarouselAndHalfWidthMedia_content_MultiListSelection(List<String> value) throws InterruptedException {

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


    public feedContent select_From_Content_TreeList(int index, List<String> treeListValues) {

        List<WebElement> _contentTreeListValues = _contentTreeList.get(index).findElements(By.xpath(CommonLocators.CONTENT_TREELIST_VALUES));

        for (int i = 0; i < treeListValues.size(); i++) {

            for (int j = 0; j < _contentTreeListValues.size(); j++) {

                if (_contentTreeListValues.get(j).getText().equalsIgnoreCase(treeListValues.get(i))) {

                    Actions treeListAction = new Actions(driver);
                    treeListAction.doubleClick(_contentTreeListValues.get(j)).perform();
                    break;
                }

            }

        }

        _save.click();
        return this;
    }


}
