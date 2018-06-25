package Template.EditorialTemplate;

import GoogleDriveConfigration.GDriveSpreedSheetUtil;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import Util.Xls_Reader;
import Util.excelConfig;
import base.testBase;
import mapDataSourceWithFE.mapControlWithDataSource;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;

public class Editorial_SEM extends testBase {


    String testSheetName = "Editorial_PDF71";
    // public mapControlWithDataSource mapcontrolWithDataSource;
    String TemplateName;
    String topNodePath;





    @Test(dataProvider = "readTestData")
    public void Create_Editorial_SubTemplate_SEM(Hashtable<String, String> data) throws Exception {

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

    @Test(dependsOnMethods = {"Create_Editorial_SubTemplate_SEM"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void verifyPreFeededSubCategoriesInsideTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()
                .verifyPreFeededSubComponent(topNodePath, Arrays.asList(data.get("CategoriesList").split("\\|")))
                .logOut();
    }


    @Test( dependsOnMethods = {"Create_Editorial_SubTemplate_SEM"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_EditorialQuote_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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



    @Test( dependsOnMethods = {"Create_Editorial_SubTemplate_SEM"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_Rich_Text_Copy_Inside_Text_Copy_Folder_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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


    @Test( dependsOnMethods = {"Create_Editorial_SubTemplate_SEM"}, dataProvider = "readTestData")
    // @Test( dataProvider = "readTestData")
    public void add_FeaturedBrand_And_fill_Content(Hashtable<String, String> data) throws
            InterruptedException, IOException {

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
    public Object[][] getData(Method method) throws IOException {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("verifyPreFeededSubCategoriesInsideTemplate")) {
            // return DataUtil.getData(xls, "PreFeededSubCategories", testSheetName);
            return GDriveSpreedSheetUtil.getData("PreFeededSubCategories", testSheetName);

        } else if (method.getName().equals("Create_Editorial_SubTemplate_SEM")) {
            //  return DataUtil.getData(xls, "EditorialTitle", testSheetName);
            return GDriveSpreedSheetUtil.getData("TemplateName", testSheetName);

        } else if (method.getName().equals("add_EditorialQuote_And_fill_Content")) {
            // return DataUtil.getData(xls, "TextCopyFolder", testSheetName);
            return GDriveSpreedSheetUtil.getData("EditoriaQuotes", testSheetName);

        } else if (method.getName().equals("add_Rich_Text_Copy_Inside_Text_Copy_Folder_And_fill_Content")) {
            // return DataUtil.getData(xls, "TemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getData("TextCopyFolder", testSheetName);

        } else if (method.getName().equals("add_FeaturedBrand_And_fill_Content")) {
            // return DataUtil.getData(xls, "HalfWidthMedia", testSheetName);
            return GDriveSpreedSheetUtil.getData("FeaturedBrand", testSheetName);
        }

        return null;
    }




}
