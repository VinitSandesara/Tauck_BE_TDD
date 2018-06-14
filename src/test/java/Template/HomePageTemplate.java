package Template;

import NodeAndComponentConfig.navigateToNode;
import TemplateImplementation.HomePage;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import Util.DataUtil;
import Util.Xls_Reader;
import Util.excelConfig;
import base.testBase;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.mail.Folder;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import static TemplateImplementation.HomePage.counter;

public class HomePageTemplate extends testBase {

    /* Note : I kept the template name in excel as "HomePage" but somehow it didn't work while executing script so i renamed it
    to Home
     */

    String testSheetName = "Home";


    @Test(dataProvider = "readTestData")
    public void createHomeTemplate(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if(sitecore.checkWhetherParentNodeIsPresentOrNot("/sitecore/content/Tauck" +"/"+data.get("TemplateName").replaceAll(" ", "-").toLowerCase())!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert("/sitecore/templates/Project/Common/Page Types/HomePage", data.get("TemplateName"));

        }
        sitecore.logOut();

 }



    @Test(dataProvider = "readTestData")
    public void createTravellingWithTaucksFolderInsideGlobal(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        if(sitecore.checkWhetherParentNodeIsPresentOrNot(data.get("NavigateToNodePath") +"/"+data.get("FolderName").replaceAll(" ", "-").toLowerCase())!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode(data.get("NavigateToNodePath"))
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("FolderName"));
        }
        sitecore .logOut();


    }


     @Test(dataProvider = "readTestData")
    public void createLeadGenerationFolderInsideGlobal(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        if(sitecore.checkWhetherParentNodeIsPresentOrNot(data.get("NavigateToNodePath") +"/"+data.get("FolderName").replaceAll(" ", "-").toLowerCase())!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode(data.get("NavigateToNodePath"))
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert("/sitecore/templates/Project/Common/Content Types/Global Folder Types/Lean Generation CTA Folder", data.get("FolderName"));
        }

        sitecore.logOut();


    }


    @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillHeroSettings(Hashtable<String, String> data) throws Exception {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(data.get("NavigateToNodePath"));

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandHeroSettingsSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();

    }

    @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillTravellingWithTauck(Hashtable<String, String> data) throws Exception {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(data.get("NavigateToNodePath"));

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandTravellingWithTauckContentSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();


    }

    @Test(dependsOnMethods = {"createTravellingWithTaucksFolderInsideGlobal"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void createTravellingWithTauckPortraitCards(Hashtable<String, String> data) throws InterruptedException, IOException {



        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(data.get("NavigateToNodePath")+"/"+data.get("CardsName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(data.get("NavigateToNodePath"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(	"/sitecore/templates/Project/Common/Content Types/Global Content Types/Portrait Trip Image Card" ,data.get("CardsName"), this.getClass().getSimpleName())
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content_PortraitTripImageCard"), 0)
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content_PortraitTripImageCardHoover"), 1)
                .logOut();


    }






    @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillTauckExperienceComponent(Hashtable<String, String> data) throws Exception {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(data.get("NavigateToNodePath"));

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandTauckExperienceComponentSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();


    }



    @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillFeaturedContent(Hashtable<String, String> data) throws Exception {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(data.get("NavigateToNodePath"));

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandFeaturedContentSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();


    }

    @Test(dependsOnMethods = {"createLeadGenerationFolderInsideGlobal"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void createLeadGenerationCards(Hashtable<String, String> data) throws InterruptedException, IOException {



        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(data.get("NavigateToNodePath")+"/"+data.get("CardsName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(data.get("NavigateToNodePath"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("CardsName"))
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), 0)
                .logOut();


    }



    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equalsIgnoreCase("createHomeTemplate")) {
            return DataUtil.getData(xls, "HomeTemplateName", testSheetName);

        }else if (method.getName().equals("fillHeroSettings")) {
            return DataUtil.getData(xls, "HeroSettings", testSheetName);

        }else if (method.getName().equals("fillTravellingWithTauck")) {
            return DataUtil.getData(xls, "TravellingWithTauck", testSheetName);

        }else if (method.getName().equals("fillTauckExperienceComponent")) {
            return DataUtil.getData(xls, "TauckExperienceComponent", testSheetName);

        }else if (method.getName().equals("fillFeaturedContent")) {
            return DataUtil.getData(xls, "FeaturedContent", testSheetName);

        }else if (method.getName().equals("createTravellingWithTauckPortraitCards")) {
            return DataUtil.getData(xls, "TravellingWithTauckPortraitCards", testSheetName);

        }else if (method.getName().equals("createTravellingWithTaucksFolderInsideGlobal")) {
            return DataUtil.getData(xls, "TravellingWithTauckPortraitCardsFolder", testSheetName);

        }else if (method.getName().equals("createLeadGenerationFolderInsideGlobal")) {
            return DataUtil.getData(xls, "LeadGenerationFolder", testSheetName);

        }else if (method.getName().equals("createLeadGenerationCards")) {
            return DataUtil.getData(xls, "LeadGenerationCards", testSheetName);
        }





        return null;
    }



}
