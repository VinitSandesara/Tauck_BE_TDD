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

    @FindBy(xpath = CommonLocators.SELECTED_TREELIST_OPTIONS)
    public List<WebElement> _selectedTreeListOptions;

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

    @FindBy(xpath = CommonLocators.DROPDOWN)
    public List<WebElement> _dropDown;

    @FindBy(xpath = HomePageLocators.CONTENT_SECTIONS_PANEL_TABLES)
    public List<WebElement> _homePageContentSectionsPanelTables;

    @FindBy(xpath = HomePageLocators.CONTENT_SECTIONS_PANEL_TABLES_ALTERNATIVE)
    public List<WebElement> _homePageContentSectionsPanelTotalTables;

    @FindBy(xpath = CommonLocators.TREELIST_LIST_OF_VALUES)
    public List<WebElement> _treeListValues;

    @FindBy(xpath = CommonLocators.TREELIST_TOP_NODE)
    public List<WebElement> _treeListTopNode;


   /* public feedContent feed_HomePage_Content_Sections_Panel(String inputData, int counter) throws IOException {

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

    }*/


    public feedContent input_Sections_Fields_Save_And_Logout(String inputData, int counter) throws IOException {

        try {
            Thread.sleep(2000);

            List<WebElement> totalInput = _multipleTables.get(counter).findElements(By.tagName("table"));

            clearAndFillContentFields(totalInput, "td", "tr", "input", CommonLocators.TEXTAREA_TEXTBOX, inputData);

            Thread.sleep(2000);
            _save.click();

            logOut();

            Thread.sleep(2000);
            new Actions(driver).sendKeys(Keys.RETURN).build().perform();


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

    public feedContent Deselect_TreeList_Selected_Options_To_Reselect(String value) throws InterruptedException {

      /*  List<String> multiListValue = new ArrayList<String>();

        for(int c=0;c<value.size();c++) {
            multiListValue.add(value.get(c));
        }*/


        Actions actionOne = new Actions(driver);

        WebElement element = driver.findElement(By.xpath("//option[text() = '" + value + "']"));
        element.click();
        actionOne.doubleClick(element).perform();
        Thread.sleep(3000);

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

        //How this method works and can apply for similar scenario here is an example link "/sitecore/content/Tauck/data/ships" listed Ship inside Ships folder, click on any ship
        // Say "Le Laperouse" - expand "Decks" - click on any listed deck inside Decks folder - "Deck Cabin Category" inside section "Deck Information" is an example for this scenario

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

    public feedContent Select_List_Option_From_Multi_Tree_List(String DataShipsShipName) throws InterruptedException {

        //How this method works and can apply for similar scenario here is an example link "/sitecore/content/Tauck/Home/ships" listed ships inside Ships folder
        // click on any ship say "Le Ponant" - Expand "Entity Reference" that's an example for this scenario

        waitForPageLoad(10);

        for (int i = 0; i < _treeListValues.size(); i++) {


            String value = _treeListValues.get(i).getText();

            if (_treeListValues.get(i).getText().equalsIgnoreCase(DataShipsShipName)) {

                // Click on DataShipsShipName
                _treeListTopNode.get(i).findElements(By.className("scContentTreeNodeGlyph")).get(0).click();

                waitForPageLoad(10);
                // Click on Decks which is inside  DataShipsShipName
                _treeListTopNode.get(i).findElements(By.className("scContentTreeNodeGlyph")).get(1).click();

                waitForPageLoad(10);
                List<WebElement> totalDecks = _treeListTopNode.get(i).findElements(By.className("scContentTreeNode"));

                List<WebElement> totalLinks = totalDecks.get(0).findElements(By.tagName("a"));

                for (int j = 1; j < totalLinks.size(); j++) {

                    String linkValue = totalLinks.get(j).getText();
                    // totalLinks.get(j).click();
                    Actions actionOne = new Actions(driver);
                    actionOne.doubleClick(totalLinks.get(j)).perform();

                }

                break;

            }


        }

        _save.click();
        return this;

    }

    public feedContent Select_Option_From_DropDown(int index, String nameOfOption) {

        _dropDown.get(index).sendKeys(nameOfOption);

        return this;

    }


    public feedContent Deselect_TreeList_Selected_Options_To_Reselect() throws InterruptedException {

        //How this method works and can apply for similar scenario here is an example link "/sitecore/content/Tauck/Home/ships" listed ships inside Ships folder
        // click on any ship say "Le Ponant" - Expand "Entity Reference" that's an example for this scenario

        waitForPageLoad(10);
        List<WebElement> totalSelectedList;
        totalSelectedList = _selectedTreeListOptions.get(1).findElements(By.tagName("option"));

        for (int i = totalSelectedList.size() - 1; i >= 0; i--) {

            Actions actionOne = new Actions(driver);
            actionOne.doubleClick(totalSelectedList.get(i)).perform();
            totalSelectedList = _selectedTreeListOptions.get(1).findElements(By.tagName("option"));
        }

        _save.click();
        return this;
    }


}
