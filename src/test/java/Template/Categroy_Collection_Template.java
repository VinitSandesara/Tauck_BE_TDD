package Template;

import FeedContent.feedContent;
import GoogleDriveConfigration.GDriveSpreedSheetUtil;
import TemplateImplementation.HomePage;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import Util.Xls_Reader;
import Util.excelConfig;
import base.testBase;
import mapDataSourceWithFE.editorialTemplateControls;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import static TemplateImplementation.HomePage.counter;

public class Categroy_Collection_Template extends testBase {


    String testSheetName = "Category";
    String TemplateName;
    String topNodePath;

    String PortraitHighlightIntraCopyPath;
    String LandScapeHighlightIntraCopyPath;


    @Test(dependsOnMethods = {"Create_Category_Collection_Template"})
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
                        .searchForControlFolderAndSelectControlFromFolder(splitControlFolders, splitControlString.get(innerloop))

                        .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), topNodePath + "/" + splitDatasourceString.get(innerloop));

            }

        }

        controls
                .saveAndCloseDeviceEditorAndLayoutDetails()
                .logOut();

    }






    @Test(dataProvider = "readTestData")
    public void Create_Category_Collection_Template(Hashtable<String, String> data) throws InterruptedException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        TemplateName = data.get("TemplateName");
        topNodePath = "/sitecore/content/Tauck/Home/destinations" + "/" + TemplateName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot(topNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Home/destinations")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("TemplateName"));

        }
        sitecore.logOut();

    }


   /*   @Test(dependsOnMethods = {"Create_Category_Collection_Template"}, dataProvider = "readTestData")
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


      @Test(dependsOnMethods = {"Create_Category_Collection_Template"}, dataProvider = "readTestData")
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


    @Test(dependsOnMethods = {"Create_Category_Collection_Template"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void CreateAndInputHighlightIntroCopy(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        PortraitHighlightIntraCopyPath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(PortraitHighlightIntraCopyPath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(PortraitHighlightIntraCopyPath)

                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));
        }

        sitecore.logOut();


    }


    @Test(dependsOnMethods = {"CreateAndInputHighlightIntroCopy"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void CreatePortraitHighlightsCards(Hashtable<String, String> data) throws Exception {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        if(data.get("RightClickInsert").equalsIgnoreCase("HighlightImage")) {

            List<String> HighlightImagesAndPortraitOnlyData = Arrays.asList(data.get("Content").split("\\_"));

            sitecore

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(PortraitHighlightIntraCopyPath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    .navigateToWhichTauckNode(PortraitHighlightIntraCopyPath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .feedContent_Fields_With_Data(HighlightImagesAndPortraitOnlyData.get(0), 0)
                    .feedContent_Fields_With_Data(HighlightImagesAndPortraitOnlyData.get(1), 2);

        } else if(data.get("RightClickInsert").equalsIgnoreCase("HighlightQuote")) {

            sitecore

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(PortraitHighlightIntraCopyPath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    .navigateToWhichTauckNode(PortraitHighlightIntraCopyPath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        } else {

            sitecore

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(PortraitHighlightIntraCopyPath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    .navigateToWhichTauckNode(PortraitHighlightIntraCopyPath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));
        }

            sitecore.logOut();
    }



    @Test(dependsOnMethods = {"Create_Category_Collection_Template"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void CreateAndInputLandScapeHighlightIntroCopy(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        LandScapeHighlightIntraCopyPath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(LandScapeHighlightIntraCopyPath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(LandScapeHighlightIntraCopyPath)

                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));
        }

        sitecore.logOut();


    }

    @Test(dependsOnMethods = {"CreateAndInputLandScapeHighlightIntroCopy"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void CreateLandScapeHighlightsCards(Hashtable<String, String> data) throws Exception {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        List<String> HighlightImagesAndPortraitOnlyData = Arrays.asList(data.get("Content").split("\\_"));

        sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(LandScapeHighlightIntraCopyPath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    .navigateToWhichTauckNode(LandScapeHighlightIntraCopyPath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .feedContent_Fields_With_Data(HighlightImagesAndPortraitOnlyData.get(0), 0)
                    .feedContent_Fields_With_Data(HighlightImagesAndPortraitOnlyData.get(1), 1);

        }


*/



    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) throws IOException {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("Create_Category_Collection_Template")) {

            return GDriveSpreedSheetUtil.getData("TemplateName", testSheetName);

        } else if (method.getName().equals("fillHeroSettings")) {

            return GDriveSpreedSheetUtil.getData("HeroSettings", testSheetName);

        } else if (method.getName().equals("fillFeaturedContent")) {

            return GDriveSpreedSheetUtil.getData("FeaturedContent", testSheetName);

        } else if (method.getName().equals("CreateAndInputHighlightIntroCopy")) {

            return GDriveSpreedSheetUtil.getData("Portrait Highlights and Introduction Copy", testSheetName);

        } else if (method.getName().equals("CreatePortraitHighlightsCards")) {

            return GDriveSpreedSheetUtil.getData("Portrait Highlights Cards", testSheetName);

        } else if (method.getName().equals("CreateAndInputLandScapeHighlightIntroCopy")) {

            return GDriveSpreedSheetUtil.getData("Landscape Highlights and Introduction Copy", testSheetName);

        } else if (method.getName().equals("CreateLandScapeHighlightsCards")) {

            return GDriveSpreedSheetUtil.getData("Landscape Highlights Cards", testSheetName);
        }





        return null;
    }


}
