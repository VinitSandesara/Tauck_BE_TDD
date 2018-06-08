package TemplateImplementation;

import FeedContent.feedContent;
import Locators.CommonLocators;
import Locators.HomePageLocators;
import com.aventstack.extentreports.ExtentTest;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends globalTemplateImplementation {

    public static int counter=0;


    public HomePage(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }

    @FindBy(xpath = CommonLocators.TEMPLATE_CONTENT_SECTIONS)
    public List<WebElement> _templateContentSections;

    @FindBy(xpath = HomePageLocators.CONTENT_SECTIONS_PANEL_TABLES)
    public List<WebElement> _contentSectionsPanelTables;

    @FindBy(xpath = HomePageLocators.CONTENT_HERO_SETTINGS)
    public WebElement _contentHeroSettings;

    @FindBy(xpath = HomePageLocators.CONTENT_FEATURED_CONTENT)
    public WebElement _contentFeaturedContent;

    @FindBy(xpath = HomePageLocators.CONTENT_TRAVELLING_WITH_TAUCK)
    public WebElement _contentTravellingWithTauck;

    @FindBy(xpath = HomePageLocators.CONTENT_TAUCK_EXPERIENCE_COMPONENT)
    public WebElement _contentTauckExperienceComponent;


    @FindBy(xpath = HomePageLocators.ALREADY_EXPANDED_CONTENT_SECTIONS_PANEL)
    public List<WebElement> _collapsedAlreadyExpandedSections;

    @FindBy(xpath = HomePageLocators.TOTAL_CONTENT_SECTIONS_PANEL)
    public List<WebElement> _howManyContentSectionsPanelAreThere;


    public HomePage collapsedHomePageContentSections() {

        for(int i=0;i<_templateContentSections.size();i++) {

            _templateContentSections.get(i).click();

        }

        return this;
    }

    public HomePage checkAndCollapsedAlreadyExpandedContentSectionsPanel() throws Exception {

        waitForPageLoad(10);


       // Note : Here i tried the loop thru i=0 but somehow it just collapsed 1st panel only but if i try this way it does collapsed all panels
        for(int i=_collapsedAlreadyExpandedSections.size();i>0;i--) {
            waitForPageLoad(10);
            _collapsedAlreadyExpandedSections.get(i-1).click();
        }

        return this;
    }


    public feedContent expandHeroSettingsSection() throws InterruptedException {

        counter = 0;

        waitForPageLoad(10);

        while(!_howManyContentSectionsPanelAreThere.get(counter).getAttribute("id").startsWith("Section_Hero_Settings")) {
            counter++;
        }

        if(_contentHeroSettings.getAttribute("class").equalsIgnoreCase("scEditorSectionCaptionCollapsed")) {
            _contentHeroSettings.click();
        }

        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;


    }

    public feedContent expandTravellingWithTauckContentSection() throws InterruptedException {

        counter = 0;

        waitForPageLoad(10);

        while(!_howManyContentSectionsPanelAreThere.get(counter).getAttribute("id").startsWith("Section_Traveling_With_Tauck")) {
            counter++;
        }


        if(_contentTravellingWithTauck.getAttribute("class").equalsIgnoreCase("scEditorSectionCaptionCollapsed")) {
            _contentTravellingWithTauck.click();
        }

        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;


    }

    public feedContent expandTauckExperienceComponentSection() throws InterruptedException {

        counter = 0;

        waitForPageLoad(10);
        while(!_howManyContentSectionsPanelAreThere.get(counter).getAttribute("id").startsWith("Section_Tauck_Experience_Component")) {
            counter++;
        }

        if(_contentTauckExperienceComponent.getAttribute("class").equalsIgnoreCase("scEditorSectionCaptionCollapsed")) {
            _contentTauckExperienceComponent.click();
        }

        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;


    }

    public feedContent expandFeaturedContentSection() throws InterruptedException {

        counter = 0;

        waitForPageLoad(10);
        while(!_howManyContentSectionsPanelAreThere.get(counter).getAttribute("id").startsWith("Section_Featured_Content")) {
            counter++;
        }

        if(_contentFeaturedContent.getAttribute("class").equalsIgnoreCase("scEditorSectionCaptionCollapsed")) {
            _contentFeaturedContent.click();
        }

        feedContent feedcontent = new feedContent(driver, test);
        PageFactory.initElements(driver, feedcontent);
        return feedcontent;


    }







}
