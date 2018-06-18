package Template.EditorialTemplate;

import NodeAndComponentConfig.navigateToNode;
import NodeAndComponentConfig.rightClickInsert;
import NodeAndComponentConfig.whatIsTheComponentName;
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

public class Editorial_WhyWeTravel extends testBase {

    String testSheetName = "Editorial_WhyWeTravel";
    // public mapControlWithDataSource mapcontrolWithDataSource;
    String TemplateName;
    String topNodePath;

    @Test
    public void testMethod() {


    }


   @Test
    public void mapDataSourceWithFrontEndControls() throws Exception {

       Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

       // if (DataUtil.readSpecificTestDataFromExcel(xls, "MapControlWithDataSource", testSheetName, "Runmode").get("Runmode").equalsIgnoreCase("Y")) {

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

       List<String> listOfComponentToMapWithDataSource = DataUtil.grabControlListForMapping(xls, testSheetName, "Template_Control");

       for (int outerloop = 0; outerloop < listOfComponentToMapWithDataSource.size(); outerloop++) {

           //  Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);
           Hashtable<String, String> data = DataUtil.getControlDatasourcePlaceholderValueFromXls(xls, listOfComponentToMapWithDataSource.get(outerloop), testSheetName);

           List<String> splitControlString = Arrays.asList(data.get("Control").split("\\|"));
           List<String> splitPlaceholderString = Arrays.asList(data.get("PlaceHolder").split("\\|"));
           List<String> splitDatasourceString = Arrays.asList(data.get("DataSource").split("\\|"));

           for (int i = 0; i < splitControlString.size(); i++) {
               controls
                       // This function wll check and remove pre-feeded controls, this is required when if any updates made in specific component later and run the script.
                       .checkAndRemovePreAddedControlsBeforeMappingIfPresent(splitControlString);
           }

           for (int innerloop = 0; innerloop < splitControlString.size(); innerloop++) {

               controls
                       .addNewControls()
                       .selectWhichControlsToAdd()
                       .addEditorialTemplateFEControl(splitControlString.get(innerloop))
                       .openPropertyDialogBoxCheckbox()
                       .clickSelectButton()

                       .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), topNodePath + "/" + splitDatasourceString.get(innerloop));

           }

       }


       controls
               .saveAndCloseDeviceEditorAndLayoutDetails()
               .logOut();
       // }




   }





    @Test(dataProvider = "readTestData")
    public void create_EditorialSubTemplate_WhyWeTravel(Hashtable<String, String> data) throws Exception {

       if (!DataUtil.isTestExecutable(xls, testSheetName)) {
           throw new SkipException("Skipping the test as Rnumode is N");
       }

       if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
           throw new SkipException("Skipping the test as Rnumode is N");
       }

       TemplateName = data.get("Templatename");
       topNodePath = "/sitecore/content/Tauck/Home" + "/" + TemplateName.replaceAll(" ", "-").toLowerCase();

       invokeBrowser();

       globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
       PageFactory.initElements(driver, sitecore);

       sitecore.launchSitecore()
               .login()
               .goToContentEditorIfNotKickOffUser();

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


    @Test(dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    public void verifyPreFeededSubCategoriesInsideTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .goToContentEditorIfNotKickOffUser()
                .verifyPreFeededSubComponent(topNodePath , Arrays.asList(data.get("CategoriesList").split("\\|")))
                .logOut();

    }



    @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void fill_Content_Of_Editorial_Title_Component(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                 .goToContentEditorIfNotKickOffUser()
                 .navigateToWhichTauckNode(topNodePath , "/" + data.get("preFeededComponentName"))
                 .fill_Component_Content_With_Data(data.get("Content"))
                 .logOut();


    }


    @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void fill_Content_Of_Header_Hero_Component(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                 .goToContentEditorIfNotKickOffUser()
                 .navigateToWhichTauckNode(topNodePath , "/" + data.get("preFeededComponentName"))
                 .fill_Component_Content_With_Data(data.get("Content"))
                 .logOut();


    }


    @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_Rich_Text_Copy_Inside_Text_Copy_Folder_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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
               .goToContentEditorIfNotKickOffUser()

               // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
               .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath + "/" + data.get("preFeededComponentName") + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

               .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
               .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
               .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
               .createTemplateOrTemplateComponent(data.get("ComponentName"))
               .feedContent_Fields_With_Data(data.get("Content"), 2)
               .logOut();

    }





    @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_MediaCarouselCards_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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


         try {
             // Before updating existing component you first need to move "Half width media segment" from left to right that is already moved from right to left or else
             // it will force you to delete its link "Breaking links dialog".
             sitecore
                     .navigateToWhichTauckNode(topNodePath, "/" + data.get("preFeededComponentName"))
                     .MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")));
         } catch (Throwable throwable) {

         }

         // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
         sitecore
                 .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath + "/" + data.get("preFeededComponentName") + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                 .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
                 .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                 .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                 .createTemplateOrTemplateComponent(data.get("ComponentName"))
                 .fill_Component_Content_With_Data(data.get("Content"))

                 .navigateToWhichTauckNode(topNodePath, "/" + data.get("preFeededComponentName"))
                 .MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")))

                 .logOut();
    }



    @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_GridMediaCards_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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
                   .goToContentEditorIfNotKickOffUser()

                   // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                   .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath + "/" + data.get("preFeededComponentName") + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                   .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
                   .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                   .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                   .createTemplateOrTemplateComponent(data.get("ComponentName"))
                   .fill_Component_Content_With_Data(data.get("Content"))
                   .logOut();

    }



    @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_AuthorProfile_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(topNodePath , "/" + data.get("preFeededComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();


    }



    @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_EditorialQuote_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath + "/" + data.get("preFeededComponentName") + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }

     @Test( dependsOnMethods = {"create_EditorialSubTemplate_WhyWeTravel"}, dataProvider = "readTestData")
   // @Test( dataProvider = "readTestData")
    public void add_FeaturedBrand_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath + "/" + data.get("preFeededComponentName") + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(topNodePath + "/" + data.get("preFeededComponentName"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();


    }


    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("verifyPreFeededSubCategoriesInsideTemplate")) {
            return DataUtil.getData(xls, "PreFeededSubCategories", testSheetName);

        } else if (method.getName().equals("fill_Content_Of_Editorial_Title_Component")) {
            return DataUtil.getData(xls, "EditorialTitle", testSheetName);

        } else if (method.getName().equals("fill_Content_Of_Header_Hero_Component")) {
            return DataUtil.getData(xls, "HeaderHero", testSheetName);

        } else if (method.getName().equals("add_MediaCarouselCards_And_fill_Content")) {
            return DataUtil.getData(xls, "MediaCarousel", testSheetName);

        } else if (method.getName().equals("add_Rich_Text_Copy_Inside_Text_Copy_Folder_And_fill_Content")) {
            return DataUtil.getData(xls, "TextCopyFolder", testSheetName);

        } else if (method.getName().equals("add_GridMediaCards_And_fill_Content")) {
            return DataUtil.getData(xls, "GridMediaFolder", testSheetName);

        } else if (method.getName().equals("add_AuthorProfile_And_fill_Content")) {
            return DataUtil.getData(xls, "AuthorProfiles", testSheetName);

        } else if (method.getName().equals("add_EditorialQuote_And_fill_Content")) {
            return DataUtil.getData(xls, "EditoriaQuotes", testSheetName);

        } else if (method.getName().equals("add_FeaturedBrand_And_fill_Content")) {
            return DataUtil.getData(xls, "FeaturedBrand", testSheetName);

        } else if (method.getName().equals("create_EditorialSubTemplate_WhyWeTravel")) {
            return DataUtil.getData(xls, "TemplateName", testSheetName);
        }





        return null;
    }


}
