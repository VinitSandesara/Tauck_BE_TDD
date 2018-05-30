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

public class Editorial_PDF71 extends testBase {

    String testSheetName = "Editorial_PDF71";
    public mapControlWithDataSource mapcontrolWithDataSource;



     @AfterClass
    public void mapDataSourceWithFrontEndControls() throws Exception {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        invokeBrowser();
       // editorialComponentList();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        editorialTemplateControls editorialtemplateControl = sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNodeForMappingDataSourceWithFrontEndControl(navigateToNode.EDITORIAL_PADF71)

                .clickPresentationLink()
                .clickDetailsLink()
                .clickFinalLayoutTabInsideLayoutDetailsDialog()
                .navigateToDeviceEditor()
                .clickControlsInsideDeviceEditorForMappingDataSourceSequentially();
        List<String> listOfComponentToMapWithDataSource =  DataUtil.grabControlListForMapping(xls,testSheetName,"Template_Control");

        for (int outerloop = 0; outerloop < listOfComponentToMapWithDataSource.size(); outerloop++) {

          //  Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);
            Hashtable<String, String> data = DataUtil.getControlDatasourcePlaceholderValueFromXls(xls, listOfComponentToMapWithDataSource.get(outerloop), testSheetName);

            List<String> splitControlString = Arrays.asList(data.get("Control").split("\\|"));
            List<String> splitPlaceholderString = Arrays.asList(data.get("PlaceHolder").split("\\|"));
            List<String> splitDatasourceString = Arrays.asList(data.get("DataSource").split("\\|"));

            for (int innerloop = 0; innerloop < splitControlString.size(); innerloop++) {

                mapcontrolWithDataSource = editorialtemplateControl
                        .addNewControls()
                        .selectWhichControlsToAdd()
                        .addEditorialTemplateFEControl(splitControlString.get(innerloop))
                        .openPropertyDialogBoxCheckbox()
                        .clickSelectButton()

                        .inputPlaceHolderAndDataSource(splitPlaceholderString.get(innerloop), splitDatasourceString.get(innerloop));


            }


        }

        mapcontrolWithDataSource
                .saveAndCloseDeviceEditorAndLayoutDetails();


    }







    @Test
    public void createEditorialSubTemplate_PDF71() throws Exception {

        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()

                // Creating EditorialTemplate template
                .navigateToWhichTauckNode(navigateToNode.HOME)
                .rightClickInsertTemplateOrComponent(rightClickInsert.EDITORIAL_SUB_TEMPLATE_PDF71)
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


        // Delete pre-added FE Controls before mapping datasource.
        Object mapControlswithDataSource = sitecore.createTemplateOrTemplateComponent(whatIsTheComponentName.EDITORIAL_SUB_TEMPLATE_PDF71, mapControlWithDataSource.class.getSimpleName());
        if (mapControlswithDataSource instanceof mapControlWithDataSource)
            ((mapControlWithDataSource) mapControlswithDataSource)
                    .clickPresentationLink()
                    .clickDetailsLink()
                    .clickFinalLayoutTabInsideLayoutDetailsDialog()
                    .navigateToDeviceEditor()
                    .clickControlsInsideDeviceEditor()
                    .removePreAddedFEControls();


    }


    @Test(dependsOnMethods = {"createEditorialSubTemplate_PDF71"}, dataProvider = "readTestData")
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
                    .verifyPreFeededSubComponent(navigateToNode.EDITORIAL_PADF71, Arrays.asList(data.get("CategoriesList").split("\\|")));

    }


    @Test( dependsOnMethods = {"createEditorialSubTemplate_PDF71", "verifyPreFeededSubCategoriesInsideTemplate"}, dataProvider = "readTestData")
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
                .navigateToWhichTauckNode(navigateToNode.EDITORIAL_PADF71 , "/" + data.get("preFeededComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"));


    }


    @Test( dependsOnMethods = {"createEditorialSubTemplate_PDF71", "verifyPreFeededSubCategoriesInsideTemplate"}, dataProvider = "readTestData")
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
                .navigateToWhichTauckNode(navigateToNode.EDITORIAL_PADF71, "/" + data.get("preFeededComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"));


    }




    @Test( dependsOnMethods = {"createEditorialSubTemplate_PDF71", "verifyPreFeededSubCategoriesInsideTemplate"}, dataProvider = "readTestData")
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
                .goToContentEditorIfNotKickOffUser();

        for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore
                        .navigateToWhichTauckNode(navigateToNode.EDITORIAL_PADF71, "/" +  data.get("preFeededComponentName"))
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                        .createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i))
                        .fill_Component_Content_With_Data(DataUtil.splitStringBasedOnUnderscore(data.get("Content")).get(i));

        }

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

        } else if (method.getName().equals("add_Rich_Text_Copy_Inside_Text_Copy_Folder_And_fill_Content")) {
            return DataUtil.getData(xls, "TextCopyFolder", testSheetName);
        }


        return null;
    }




} // Main class is closing
