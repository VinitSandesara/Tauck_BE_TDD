package Template;

import GoogleDriveConfigration.GDriveSpreedSheetUtil;
import NodeAndComponentConfig.navigateToNode;
import TemplateImplementation.HomePage;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import Util.DataUtil;
import Util.Xls_Reader;
import Util.excelConfig;
import base.testBase;
import mapDataSourceWithFE.editorialTemplateControls;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.mail.Folder;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import static TemplateImplementation.HomePage.counter;

public class HomePageTemplate extends testBase {

    /* Note : I kept the template name in excel as "HomePage" but somehow it didn't work while executing script so i renamed it
    to Home
     */

    String testSheetName = "Home";
    String TemplateName;
    String topNodePath;
    String TravellingWithTauckFolderName;
    String TravellingWithTauckNodePath;
    String LeadGenerationFolderName;
    String LeadGenerationFolderNodePath;
    String LeadGenerationCopyNodePath;
    String LeadGenerationCopyFolderName;


    @Test(dependsOnMethods = {"createHomeTemplate"})
    public void mapDataSourceWithFrontEndControls() throws Exception {


        invokeBrowser();

        editorialTemplateControls controls = new editorialTemplateControls(driver, test.get());
        PageFactory.initElements(driver, controls);

        controls
                .launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNodeForMappingDataSourceWithFrontEndControl(topNodePath)

                .clickPresentationLink()
                .clickDetailsLink()
                .clickFinalLayoutTabInsideLayoutDetailsDialog()
                .navigateToDeviceEditor()
                .clickControlsInsideDeviceEditorForMappingDataSourceSequentially();

        List<String> listOfComponentToMapWithDataSource = GDriveSpreedSheetUtil.getListOfControlsForMapping(testSheetName, "Template_Control");

        for (int outerloop = 0; outerloop < listOfComponentToMapWithDataSource.size(); outerloop++) {

            Hashtable<String, String> data = GDriveSpreedSheetUtil.getFEControlDatasourceAndPlaceholderValueFromSpecificSheetToMap(listOfComponentToMapWithDataSource.get(outerloop), testSheetName);

            List<String> splitControlString = Arrays.asList(data.get("Control").split("\\|"));
            List<String> splitPlaceholderString = Arrays.asList(data.get("PlaceHolder").split("\\|"));
            List<String> splitDatasourceString = Arrays.asList(data.get("DataSource").split("\\|"));
            List<String> splitControlFolders = Arrays.asList(data.get("ControlFolder").split("\\/"));

            controls
                    // This function wll check and remove pre-feeded controls, this is required when if any updates made in specific component later and run the script.
                    .checkAndRemovePreAddedControlsBeforeMappingIfPresent(splitControlString);

            for (int innerloop = 0; innerloop < splitControlString.size(); innerloop++) {

                controls
                        .addNewControls()
                        .searchForControlFolderAndSelectControlFromFolder(splitControlFolders, splitControlString.get(innerloop));

                        if(listOfComponentToMapWithDataSource.get(outerloop).equalsIgnoreCase("TravellingWithTauckPortraitCards")) {
                            controls
                                    .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), TravellingWithTauckNodePath + "/" + splitDatasourceString.get(innerloop));

                        } else {
                            if(listOfComponentToMapWithDataSource.get(outerloop).equalsIgnoreCase("LeadGenerationCards")) {
                                controls
                                        .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), LeadGenerationFolderNodePath + "/" + splitDatasourceString.get(innerloop));
                            } else {
                                controls
                                        .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), LeadGenerationCopyNodePath + "/" + splitDatasourceString.get(innerloop));
                            }
                        }


            }

        }

        controls
                .saveAndCloseDeviceEditorAndLayoutDetails()
                .logOut();

    }


    @Test(dataProvider = "readTestData")
    public void createHomeTemplate(Hashtable<String, String> data) throws InterruptedException {

       /* if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }*/

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        TemplateName = data.get("TemplateName");
        topNodePath = "/sitecore/content/Tauck" + "/" + TemplateName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if(sitecore.checkWhetherParentNodeIsPresentOrNot(topNodePath)!=true) {
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



 /* @Test(dataProvider = "readTestData")
    public void createTravellingWithTaucksFolderInsideGlobal(Hashtable<String, String> data) throws InterruptedException {



        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        TravellingWithTauckFolderName = data.get("FolderName");
        TravellingWithTauckNodePath = "/sitecore/content/Tauck/Global" + "/" + TravellingWithTauckFolderName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        if(sitecore.checkWhetherParentNodeIsPresentOrNot(TravellingWithTauckNodePath)!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Global")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("FolderName"));
        }
        sitecore .logOut();


    }*/

   /* @Test(dependsOnMethods = {"createTravellingWithTaucksFolderInsideGlobal"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void createTravellingWithTauckPortraitCards(Hashtable<String, String> data) throws InterruptedException, IOException {





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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(TravellingWithTauckNodePath+"/"+data.get("CardsName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(TravellingWithTauckNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(	"/sitecore/templates/Project/Common/Content Types/Global Content Types/Portrait Trip Image Card" ,data.get("CardsName"), this.getClass().getSimpleName())
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content_PortraitTripImageCard"), 0)
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content_PortraitTripImageCardHoover"), 1)
                .logOut();


    }
*/

    @Test(dataProvider = "readTestData")
    public void createLeadGenerationFolderInsideGlobal(Hashtable<String, String> data) throws InterruptedException {



        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        LeadGenerationFolderName = data.get("FolderName");
        LeadGenerationFolderNodePath = "/sitecore/content/Tauck/Global/lead-generation" + "/" + LeadGenerationFolderName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        if(sitecore.checkWhetherParentNodeIsPresentOrNot(LeadGenerationFolderNodePath)!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Global/lead-generation")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert("/sitecore/templates/Project/Common/Content Types/Global Folder Types/Lean Generation CTA Folder", data.get("FolderName"));
        }

        sitecore.logOut();


    }

   /*  @Test(dependsOnMethods = {"createLeadGenerationFolderInsideGlobal"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void createLeadGenerationCards(Hashtable<String, String> data) throws InterruptedException, IOException {





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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(LeadGenerationFolderNodePath+"/"+data.get("CardsName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(LeadGenerationFolderNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("CardsName"))
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), 0)
                .logOut();


    } */

    @Test(dataProvider = "readTestData")
    public void createLeadGenerationCopyFolderInsideGlobal(Hashtable<String, String> data) throws InterruptedException {



        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }



        LeadGenerationCopyFolderName = data.get("FolderName");
        LeadGenerationCopyNodePath = "/sitecore/content/Tauck/Global/lead-generation" + "/" + LeadGenerationCopyFolderName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        if(sitecore.checkWhetherParentNodeIsPresentOrNot(LeadGenerationCopyNodePath)!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Global/lead-generation")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert("/sitecore/templates/Project/Common/Content Types/Global Folder Types/Lead Generation Copy Folder", data.get("FolderName"));
        }

        sitecore.logOut();


    }


 /*   @Test(dependsOnMethods = {"createLeadGenerationCopyFolderInsideGlobal"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void createLeadGenerationCopy(Hashtable<String, String> data) throws InterruptedException, IOException {





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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(LeadGenerationCopyNodePath+"/"+data.get("CopyName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(LeadGenerationCopyNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("CopyName"))
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), 0)
                .logOut();


    }



 /*   @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillHeroSettings(Hashtable<String, String> data) throws Exception {



        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(topNodePath);

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandHeroSettingsSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();

    }

    @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillTravellingWithTauck(Hashtable<String, String> data) throws Exception {



        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(topNodePath);

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandTravellingWithTauckContentSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();


    }


    @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillTauckExperienceComponent(Hashtable<String, String> data) throws Exception {



        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(topNodePath);

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandTauckExperienceComponentSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();


    }



    @Test(dependsOnMethods = {"createHomeTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void fillFeaturedContent(Hashtable<String, String> data) throws Exception {



        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(topNodePath);

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandFeaturedContentSection()
                .clear_And_feed_HomePage_Content_Sections_Panel(data.get("Content"), counter)
                .logOut();


    }

    */




    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) throws IOException {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equalsIgnoreCase("createHomeTemplate")) {
           // return DataUtil.getData(xls, "HomeTemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getData("HomeTemplateName", testSheetName);

        }else if (method.getName().equals("fillHeroSettings")) {
           // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("HeroSettings", testSheetName);

        }else if (method.getName().equals("fillTravellingWithTauck")) {
           // return DataUtil.getData(xls, "TravellingWithTauck", testSheetName);
            return GDriveSpreedSheetUtil.getData("TravellingWithTauck", testSheetName);

        }else if (method.getName().equals("fillTauckExperienceComponent")) {
            //return DataUtil.getData(xls, "TauckExperienceComponent", testSheetName);
            return GDriveSpreedSheetUtil.getData("TauckExperienceComponent", testSheetName);

        }else if (method.getName().equals("fillFeaturedContent")) {
           // return DataUtil.getData(xls, "FeaturedContent", testSheetName);
            return GDriveSpreedSheetUtil.getData("FeaturedContent", testSheetName);

        }else if (method.getName().equals("createTravellingWithTauckPortraitCards")) {
          //  return DataUtil.getData(xls, "TravellingWithTauckPortraitCards", testSheetName);
            return GDriveSpreedSheetUtil.getData("TravellingWithTauckPortraitCards", testSheetName);

        }else if (method.getName().equals("createTravellingWithTaucksFolderInsideGlobal")) {
          //  return DataUtil.getData(xls, "TravellingWithTauckPortraitCardsFolder", testSheetName);
            return GDriveSpreedSheetUtil.getData("TravellingWithTauckPortraitCardsFolder", testSheetName);

        }else if (method.getName().equals("createLeadGenerationFolderInsideGlobal")) {
          //  return DataUtil.getData(xls, "LeadGenerationFolder", testSheetName);
            return GDriveSpreedSheetUtil.getData("LeadGenerationFolder", testSheetName);

        }else if (method.getName().equals("createLeadGenerationCards")) {
          //  return DataUtil.getData(xls, "LeadGenerationCards", testSheetName);
            return GDriveSpreedSheetUtil.getData("LeadGenerationCards", testSheetName);

        }else if (method.getName().equals("createLeadGenerationCopyFolderInsideGlobal")) {
          //  return DataUtil.getData(xls, "LeadGenerationCopyFolder", testSheetName);
            return GDriveSpreedSheetUtil.getData("LeadGenerationCopyFolder", testSheetName);

        }else if (method.getName().equals("createLeadGenerationCopy")) {
          //  return DataUtil.getData(xls, "LeadGenerationCopy", testSheetName);
            return GDriveSpreedSheetUtil.getData("LeadGenerationCopy", testSheetName);
        }





        return null;
    }



}
