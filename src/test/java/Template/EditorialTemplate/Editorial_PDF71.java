package Template.EditorialTemplate;

import GoogleDriveConfigration.GDriveSpreedSheetUtil;
import NodeAndComponentConfig.navigateToNode;
import NodeAndComponentConfig.rightClickInsert;
import NodeAndComponentConfig.whatIsTheComponentName;
import ParallelTest.LocalDriverManager;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import Util.DataUtil;
import Util.Xls_Reader;
import Util.excelConfig;
import base.testBase;
import mapDataSourceWithFE.editorialTemplateControls;
import mapDataSourceWithFE.mapControlWithDataSource;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import static TemplateImplementation.globalTemplateImplementation.counter;

public class Editorial_PDF71 extends testBase {

    String testSheetName = "Editorial_PDF71";
    // public mapControlWithDataSource mapcontrolWithDataSource;
    String TemplateName;
    String topNodePath;


    @Test(dependsOnMethods = {"createEditorialSubTemplate_PDF71"})
    public void mapDataSourceWithFrontEndControls() throws Exception {

        invokeBrowser();

        editorialTemplateControls controls = new editorialTemplateControls(driver, test.get());
        PageFactory.initElements(driver, controls);

        controls
                .launchSitecore()
                .login()
           //     .goToContentEditorIfNotKickOffUser()
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

                // Here i am checking if datasource whose value is empty like an example for "Text Size Component" whose value is empty for that i dont need to input  topNodePath, it should be left blank
                if (splitDatasourceString.get(innerloop).equalsIgnoreCase(" ")) {
                    controls
                            .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), splitDatasourceString.get(innerloop));
                } else {
                    controls
                            .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), topNodePath + "/" + splitDatasourceString.get(innerloop));
                }

            }

        }

        controls
                .saveAndCloseDeviceEditorAndLayoutDetails()
                .logOut();


    }





    @Test(dataProvider = "readTestData")
    public void createEditorialSubTemplate_PDF71(Hashtable<String, String> data) throws Exception {

     /*   if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }*/

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        TemplateName = data.get("Templatename");
        topNodePath = "/sitecore/content/Tauck/Home" + "/" + TemplateName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();


        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore.launchSitecore()
                .login();
            //    .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot("/sitecore/content/Tauck/Home" + "/" + data.get("Templatename").replaceAll(" ", "-").toLowerCase()) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            // Creating EditorialTemplate template
            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Home")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


            // Delete pre-added FE Controls before mapping datasource.
            Object mapControlswithDataSource = sitecore.createTemplateOrTemplateComponent(data.get("Templatename"), mapControlWithDataSource.class.getSimpleName());
            if (mapControlswithDataSource instanceof mapControlWithDataSource)
                ((mapControlWithDataSource) mapControlswithDataSource)
                        .clickPresentationLink()
                        .clickDetailsLink()
                        .clickFinalLayoutTabInsideLayoutDetailsDialog()
                        .navigateToDeviceEditor()
                        .clickControlsInsideDeviceEditor()
                        .removePreAddedFEControls();
        }
        sitecore.logOut();


    }


    @Test(dependsOnMethods = {"createEditorialSubTemplate_PDF71"}, dataProvider = "readTestData")
    public void verifyPreFeededSubCategoriesInsideTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        /*   if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }*/

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()
                .verifyPreFeededSubComponent(topNodePath , Arrays.asList(data.get("CategoriesList").split("\\|")))
                .logOut();

    }


    @Test(dependsOnMethods = {"createEditorialSubTemplate_PDF71"}, dataProvider = "readTestData")
    public void Verify_And_Feed_HeaderMedia_Content_Section(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
              //  .goToContentEditorIfNotKickOffUser()

                .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"), " ")
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }



    @Test(dependsOnMethods = {"createEditorialSubTemplate_PDF71"}, dataProvider = "readTestData")
    public void Verify_And_Feed_EditorialTitle_Content_Section(Hashtable<String, String> data) throws Exception {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
            //    .goToContentEditorIfNotKickOffUser()

                .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandSections("Section_Hero_Settings")

                .input_Sections_Fields_Save_And_Logout(data.get("Content"), counter);


    }


    @Test(dependsOnMethods = {"createEditorialSubTemplate_PDF71"}, dataProvider = "readTestData")
    public void Verify_FeaturedContent(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
           //     .goToContentEditorIfNotKickOffUser()

                .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
                .logOut();

    }




    @Test(dependsOnMethods = {"createEditorialSubTemplate_PDF71"}, dataProvider = "readTestData")
    public void Verify_PreFeeded_TextCopyFolder_Add_SubComponent_And_Feed_Content_Section(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
             //   .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +  "/" + data.get("preFeededComponentName") + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .feedContent_Fields_With_Data(data.get("Content"),2)
                .logOut();

    }




    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) throws IOException {

        if (method.getName().equals("verifyPreFeededSubCategoriesInsideTemplate")) {
            // return DataUtil.getData(xls, "PreFeededSubCategories", testSheetName);
            return GDriveSpreedSheetUtil.getData("Verify_PreFeeded_SubCategories", testSheetName);

        } else if (method.getName().equals("createEditorialSubTemplate_PDF71")) {
            // return DataUtil.getData(xls, "TemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getData( "TemplateName", testSheetName);

        } else if (method.getName().equals("Verify_And_Feed_HeaderMedia_Content_Section")) {
            // return DataUtil.getData(xls, "TemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel( "Verify_And_Feed_HeaderMedia_Content_Section", testSheetName);

        } else if (method.getName().equals("Verify_And_Feed_EditorialTitle_Content_Section")) {
            // return DataUtil.getData(xls, "TemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel( "Verify_And_Feed_EditorialTitle_Content_Section", testSheetName);

        } else if (method.getName().equals("Verify_FeaturedContent")) {
            // return DataUtil.getData(xls, "TemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel( "Verify_FeaturedContent", testSheetName);

        } else if (method.getName().equals("Verify_PreFeeded_TextCopyFolder_Add_SubComponent_And_Feed_Content_Section")) {
            // return DataUtil.getData(xls, "TemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel( "Verify_PreFeeded_TextCopyFolder_Add_SubComponent_And_Feed_Content_Section", testSheetName);

        }




        return null;
    }




} // Main class is closing
