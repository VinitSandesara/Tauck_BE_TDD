package Template.EditorialTemplate;

import FeedContent.feedContent;
import NodeAndComponentConfig.navigateToNode;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;


public class EditorialTemplate extends testBase {

    String testSheetName = "Editorial";
    // public mapControlWithDataSource mapcontrolWithDataSource;
    String TemplateName;
    String topNodePath;


    @Test(dependsOnMethods = {"createEditorialTemplate"})
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
    public void createEditorialTemplate(Hashtable<String, String> data) throws Exception {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        TemplateName = data.get("TemplateName");
        topNodePath = "/sitecore/content/Tauck/Home" + "/" + TemplateName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot("/sitecore/content/Tauck/Home" + "/" + data.get("TemplateName").replaceAll(" ", "-").toLowerCase()) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            // Creating EditorialTemplate template
            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Home")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


            // Delete pre-added FE Controls before mapping datasource.
            Object mapControlswithDataSource = sitecore.createTemplateOrTemplateComponent(data.get("TemplateName"), mapControlWithDataSource.class.getSimpleName());
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

    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
  //  @Test(dataProvider = "readTestData")
    public void add_AuthorProfiles_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"AuthorProfiles",testSheetName,"ComponentName").get(0);

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver,test.get());
        PageFactory.initElements(driver, sitecore);

        if (data.get("RightClickInsert").equalsIgnoreCase("Author Profiles")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();
    }

    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_FlexCard_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"FlexCard",testSheetName,"ComponentName").get(0);

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("RightClickInsert").equalsIgnoreCase("Editorial Flex Cards")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();
    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
   //  @Test(dataProvider = "readTestData")
    public void add_CategoryCardModule_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"CategoryCardModule",testSheetName,"ComponentName").get(0);

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("RightClickInsert").equalsIgnoreCase("Category Card Module")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();
    }



     @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_HeaderMedia_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws Exception {


        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+data.get("ComponentName").replaceAll(" ", "-").toLowerCase())


                // Text copy folder component
                .navigateToWhichTauckNode(topNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();


    }



    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_EditorialHero_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws Exception {


        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+data.get("ComponentName").replaceAll(" ", "-").toLowerCase())


                // Text copy folder component
                .navigateToWhichTauckNode(topNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }


   @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_TextCopyFolder_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

       String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"TextCopyFolder",testSheetName,"ComponentName").get(0);

       if (!DataUtil.isTestExecutable(xls, testSheetName)) {
           throw new SkipException("Skipping the test as Rnumode is N");
       }

       if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
           throw new SkipException("Skipping the test as Rnumode is N");
       }


       invokeBrowser();
       globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
       PageFactory.initElements(driver, sitecore);


       if (data.get("RightClickInsert").equalsIgnoreCase("Text Copy Folder")) {
           sitecore
                   .login()
                   .goToContentEditorIfNotKickOffUser()

                   // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                   .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                   // Author Profiles component
                   .navigateToWhichTauckNode(topNodePath)
                   .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                   .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                   .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
       } else {
           sitecore
                   .login()
                   .goToContentEditorIfNotKickOffUser()

                   // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                   .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                   // Author Profiles component
                   .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                   .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                   .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                   .createTemplateOrTemplateComponent(data.get("ComponentName"))
                   .feedContent_Fields_With_Data(data.get("Content"), 2);

       }

       sitecore.logOut();

    }


     @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_FeaturedContent_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {


        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

         sitecore
                 .login()
                 .goToContentEditorIfNotKickOffUser()

                 // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                 .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+data.get("ComponentName").replaceAll(" ", "-").toLowerCase())


                 // Text copy folder component
                 .navigateToWhichTauckNode(topNodePath)
                 .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                 .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                 .createTemplateOrTemplateComponent(data.get("ComponentName"))
                 .logOut();

    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_Editorial_Quotes_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"EditoriaQuotes",testSheetName,"ComponentName").get(0);

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("RightClickInsert").equalsIgnoreCase("Editorial Quotes")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();

    }



    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_GridMediaFolder_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"GridMediaFolder",testSheetName,"ComponentName").get(0);

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("RightClickInsert").equalsIgnoreCase("Grid Media Folder")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();

    }


   @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_HalfWidthMedia_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

       String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"HalfWidthMedia",testSheetName,"ComponentName").get(0);

       if (!DataUtil.isTestExecutable(xls, testSheetName)) {
           throw new SkipException("Skipping the test as Rnumode is N");
       }

       if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
           throw new SkipException("Skipping the test as Rnumode is N");
       }


       invokeBrowser();
       globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
       PageFactory.initElements(driver, sitecore);

       try {
           // Before updating existing component you first need to move "Half width media segment" from left to right that is already moved from right to left or else
           // it will force you to delete its link "Breaking links dialog".
           sitecore
                   .login()
                   .goToContentEditorIfNotKickOffUser()
                   .navigateToWhichTauckNode(topNodePath , "/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                   .MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")));
       } catch (Throwable throwable) {

       }

       if (data.get("RightClickInsert").equalsIgnoreCase("Half Width Media Module")) {
           sitecore

                   // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                   .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                   // Author Profiles component
                   .navigateToWhichTauckNode(topNodePath)
                   .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                   .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                   .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
       } else {
           sitecore

                   // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                   .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                   // Author Profiles component
                   .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                   .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                   .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                   .createTemplateOrTemplateComponent(data.get("ComponentName"))
                   .fill_Component_Content_With_Data(data.get("Content"))

                   .navigateToWhichTauckNode(topNodePath , "/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                   .MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")));

       }

       sitecore.logOut();

   }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_FeaturedBrand_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"FeaturedBrand",testSheetName,"ComponentName").get(0);

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("RightClickInsert").equalsIgnoreCase("Featured Brand Module")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    // Author Profiles component
                    .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();
    }


  @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_MediaCarousel_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

      String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"MediaCarousel",testSheetName,"ComponentName").get(0);

      if (!DataUtil.isTestExecutable(xls, testSheetName)) {
          throw new SkipException("Skipping the test as Rnumode is N");
      }

      if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
          throw new SkipException("Skipping the test as Rnumode is N");
      }


      invokeBrowser();
      globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
      PageFactory.initElements(driver, sitecore);

      try {
          // Before updating existing component you first need to move "Half width media segment" from left to right that is already moved from right to left or else
          // it will force you to delete its link "Breaking links dialog".
          sitecore
                  .login()
                  .goToContentEditorIfNotKickOffUser()
                  .navigateToWhichTauckNode(topNodePath , "/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                  .MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")));
      } catch (Throwable throwable) {

      }

      if (data.get("RightClickInsert").equalsIgnoreCase("Media Carousel")) {
          sitecore

                  // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                  .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())

                  // Author Profiles component
                  .navigateToWhichTauckNode(topNodePath)
                  .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                  .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                  .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
      } else {
          sitecore

                  // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                  .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase()+ "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                  // Author Profiles component
                  .navigateToWhichTauckNode(topNodePath +"/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                  .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                  .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                  .createTemplateOrTemplateComponent(data.get("ComponentName"))
                  .fill_Component_Content_With_Data(data.get("Content"))

                  .navigateToWhichTauckNode(topNodePath , "/"+ TopMostComponentName.replaceAll(" ", "-").toLowerCase())
                  .MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")));

      }

      sitecore.logOut();
    }


    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("createEditorialTemplate")) {
            return DataUtil.getData(xls, "EditorialTemplateName", testSheetName);

        } else if (method.getName().equals("add_TextCopyFolder_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "TextCopyFolder", testSheetName);

        } else if (method.getName().equals("add_Editorial_Quotes_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "EditoriaQuotes", testSheetName);

        } else if (method.getName().equals("add_GridMediaFolder_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "GridMediaFolder", testSheetName);

        } else if (method.getName().equals("add_HeaderMedia_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "HeaderMedia", testSheetName);

        } else if (method.getName().equals("add_EditorialHero_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "EditorialHero", testSheetName);

        } else if (method.getName().equals("add_FeaturedContent_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "FeaturedContent", testSheetName);

        } else if (method.getName().equals("add_MediaCarousel_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "MediaCarousel", testSheetName);

        } else if (method.getName().equals("add_CategoryCardModule_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "CategoryCardModule", testSheetName);

        } else if (method.getName().equals("add_FlexCard_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "FlexCard", testSheetName);

        } else if (method.getName().equals("add_FeaturedBrand_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "FeaturedBrand", testSheetName);

        } else if (method.getName().equals("add_HalfWidthMedia_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "HalfWidthMedia", testSheetName);

        } else if (method.getName().equals("add_AuthorProfiles_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
        }


        return null;
    }


}
