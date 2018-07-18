package Template.EditorialTemplate;

import FeedContent.feedContent;
import GoogleDriveConfigration.GDriveSpreedSheetUtil;
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
    String AuthorProfilesNodePath;
    String FlexCardsNodePath;
    String TextCopyFolderNodePath;
    String EditorialQuotesNodePath;
    String GridMediaFolderNodePath;
    String MediaCarouselNodePath;
    String CategoryCardModuleNodePath;
    String FeaturedBrandNodePath;
    String HalfWidthMediaNodePath;

    @Test(dependsOnMethods = {"createEditorialTemplate"})
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
    public void createEditorialTemplate(Hashtable<String, String> data) throws Exception {



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
    public void add_AuthorProfiles_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        AuthorProfilesNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(AuthorProfilesNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_AuthorProfiles_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_AuthorProfiles_SubComponent_Inside_AuthorProfiles(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(AuthorProfilesNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(AuthorProfilesNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }



    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_FlexCards_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        FlexCardsNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(FlexCardsNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_FlexCards_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_FlexCards_SubComponent_Inside_FlexCards(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(FlexCardsNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(FlexCardsNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }




    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_TextCopyFolder_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        TextCopyFolderNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(TextCopyFolderNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_TextCopyFolder_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_TextCopyFolder_SubComponent_Inside_TextCopyFolder(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(TextCopyFolderNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(TextCopyFolderNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .feedContent_Fields_With_Data(data.get("Content"),2)
                .logOut();

    }



    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_EditoriaQuotes_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        EditorialQuotesNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(EditorialQuotesNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_EditoriaQuotes_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_EditoriaQuotes_SubComponent_Inside_EditoriaQuotes(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(EditorialQuotesNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(EditorialQuotesNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }




    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_GridMediaFolder_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        GridMediaFolderNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(GridMediaFolderNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_GridMediaFolder_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_GridMediaFolder_SubComponent_Inside_GridMediaFolder(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(GridMediaFolderNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(GridMediaFolderNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }



    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_HeaderMedia_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase()) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_EditorialHero_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase()) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(data.get("Content"));

        }

        sitecore.logOut();

    }




    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_FeaturedContent_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase()) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));
        }

        sitecore.logOut();

    }



    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_MediaCarousel_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        MediaCarouselNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(MediaCarouselNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_MediaCarousel_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_MediaCarousel_SubComponent_Inside_MediaCarousel(Hashtable<String, String> data) throws InterruptedException, IOException {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

                .navigateToWhichTauckNode(MediaCarouselNodePath, " ")
                .DeSelectAll_TreeList_Options()

        // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(MediaCarouselNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

               // .navigateToWhichTauckNode(MediaCarouselNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))

                .navigateToWhichTauckNode(MediaCarouselNodePath, " ")
                .SelectAll_TreeList_Options()
                .logOut();

    }




    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_CategoryCardModule_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        CategoryCardModuleNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(CategoryCardModuleNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_CategoryCardModule_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_CategoryCardModule_SubComponent_Inside_CategoryCardModule(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(CategoryCardModuleNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(CategoryCardModuleNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }




    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_FeaturedBrand_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        FeaturedBrandNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(FeaturedBrandNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_FeaturedBrand_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_FeaturedBrand_SubComponent_Inside_FeaturedBrand(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(FeaturedBrandNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(FeaturedBrandNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();

    }




    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_HalfWidthMedia_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        HalfWidthMediaNodePath = topNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        if (sitecore.checkWhetherParentNodeIsPresentOrNot(HalfWidthMediaNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"));

        }

        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"add_HalfWidthMedia_Component_Inside_EditorialTemplate"}, dataProvider = "readTestData")
    public void add_HalfWidthMedia_SubComponent_Inside_HalfWidthMedia(Hashtable<String, String> data) throws InterruptedException, IOException {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

                .navigateToWhichTauckNode(HalfWidthMediaNodePath, " ")
                .DeSelectAll_TreeList_Options()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(HalfWidthMediaNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                // .navigateToWhichTauckNode(MediaCarouselNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))

                .navigateToWhichTauckNode(HalfWidthMediaNodePath, " ")
                .SelectAll_TreeList_Options()
                .logOut();

    }

















    /* ==================================================================================== */

  /*  @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void add_FlexCard_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException, IOException {

        String TopMostComponentName = DataUtil.returnTestDataForSpecificColumnFromSpecificSheet(xls,"FlexCard",testSheetName,"ComponentName").get(0);



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
*/
    /* ==================================================================================== */


    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) throws IOException {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("createEditorialTemplate")) {
           // return DataUtil.getData(xls, "EditorialTemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getData("EditorialTemplateName", testSheetName);

        } else if (method.getName().equals("add_AuthorProfiles_Component_Inside_EditorialTemplate")) {
           // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_AuthorProfiles_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_AuthorProfiles_SubComponent_Inside_AuthorProfiles")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_AuthorProfiles_Sub_Component", testSheetName);

        } else if (method.getName().equals("add_FlexCards_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_FlexCards_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_FlexCards_SubComponent_Inside_FlexCards")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_FlexCards_Sub_Component", testSheetName);


        } else if (method.getName().equals("add_TextCopyFolder_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_TextCopyFolder_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_TextCopyFolder_SubComponent_Inside_TextCopyFolder")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_TextCopyFolder_Sub_Component", testSheetName);


        } else if (method.getName().equals("add_EditoriaQuotes_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_EditoriaQuotes_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_EditoriaQuotes_SubComponent_Inside_EditoriaQuotes")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_EditoriaQuotes_Sub_Component", testSheetName);


        } else if (method.getName().equals("add_GridMediaFolder_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_GridMediaFolder_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_GridMediaFolder_SubComponent_Inside_GridMediaFolder")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_GridMediaFolder_Sub_Component", testSheetName);

        } else if (method.getName().equals("add_HeaderMedia_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_HeaderMedia_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_EditorialHero_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_EditorialHero_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_FeaturedContent_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_FeaturedContent_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_MediaCarousel_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_MediaCarousel_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_MediaCarousel_SubComponent_Inside_MediaCarousel")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_MediaCarousel_Sub_Component", testSheetName);

        } else if (method.getName().equals("add_CategoryCardModule_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_CategoryCardModule_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_CategoryCardModule_SubComponent_Inside_CategoryCardModule")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_CategoryCardModule_Sub_Component", testSheetName);


        } else if (method.getName().equals("add_FeaturedBrand_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_FeaturedBrand_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_FeaturedBrand_SubComponent_Inside_FeaturedBrand")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_FeaturedBrand_Sub_Component", testSheetName);


        } else if (method.getName().equals("add_HalfWidthMedia_Component_Inside_EditorialTemplate")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Create_HalfWidthMedia_Node_Inside_Home_Editorial_Node", testSheetName);

        } else if (method.getName().equals("add_HalfWidthMedia_SubComponent_Inside_HalfWidthMedia")) {
            // return DataUtil.getData(xls, "AuthorProfiles", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_HalfWidthMedia_Sub_Component", testSheetName);
        }










            return null;
    }


}
