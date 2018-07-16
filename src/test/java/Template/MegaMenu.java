package Template;

import FeedContent.feedContent;
import GoogleDriveConfigration.GDriveSpreedSheetUtil;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import Util.Xls_Reader;
import Util.excelConfig;
import base.testBase;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;

public class MegaMenu extends testBase {

    String testSheetName = "MegaMenu";
    // public mapControlWithDataSource mapcontrolWithDataSource;
    String subMenuRightClickInsertFEControl = "/sitecore/templates/Project/Common/Content Types/Global Content Types/Menu Section";
    //  public mapControlWithDataSource mapcontrolWithDataSource;
    String TemplateName;
    String topNodePath;
    String destinationsMenuNodePath;
    String ToursandCruisesMenuNodePath;
    String WhyTauckMenuNodePath;


   /* @Test
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

            //  Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);
            Hashtable<String, String> data = GDriveSpreedSheetUtil.getFEControlDatasourceAndPlaceholderValueFromSpecificSheetToMap(listOfComponentToMapWithDataSource.get(outerloop), testSheetName);

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



    }*/


    @Test(dataProvider = "readTestData")
    public void create_MegaMenu(Hashtable<String, String> data) throws Exception {

        TemplateName = data.get("MegaMenuName");
        topNodePath = "/sitecore/content/Tauck/Global/navigation/header" + "/" + TemplateName.replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot(topNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            // Creating EditorialTemplate template
            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Global/navigation/header")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("MegaMenuName"));

        }
        sitecore.logOut();




      /*  for(int i=0;i<Arrays.asList(data.get("RightClickInsert").split("\\|")).size();i++) {

            sitecore
                    .navigateToWhichTauckNode(Arrays.asList(data.get("NavigateToNodePath").split("\\|")).get(i))
                    .rightClickInsertTemplateOrComponent(Arrays.asList(data.get("RightClickInsert").split("\\|")).get(i))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(Arrays.asList(data.get("ComponentToBeInsert").split("\\|")).get(i),Arrays.asList(data.get("SubMenus").split("\\|")).get(i) );


        }*/

    }


    @Test(dependsOnMethods = {"create_MegaMenu"}, dataProvider = "readTestData")
    //  @Test(dataProvider = "readTestData")
    public void Create_Destinations_Menu(Hashtable<String, String> data) throws InterruptedException {

        destinationsMenuNodePath = topNodePath + "/" + data.get("MenuName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot(destinationsMenuNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            // Creating EditorialTemplate template
            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(subMenuRightClickInsertFEControl, data.get("MenuName"));

        }
        sitecore.logOut();

    }

    @Test(dependsOnMethods = {"Create_Destinations_Menu"}, dataProvider = "readTestData")
    //  @Test(dataProvider = "readTestData")
    public void Create_Destinations_SubMenus(Hashtable<String, String> data) throws InterruptedException, IOException {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(destinationsMenuNodePath + "/" + data.get("SubMenuName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(destinationsMenuNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("SubMenuName"));


        for (int i = 0; i < Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).size(); i++) {

            sitecore
                    .navigateToWhichTauckNode(destinationsMenuNodePath + "/" + data.get("SubMenuName").replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(Arrays.asList(data.get("SubComponent_RightClickInsert").split("\\|")).get(i))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
            feedContent feedcontent = sitecore.createTemplateOrTemplateComponent(Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).get(i));

            if (Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).get(i).equalsIgnoreCase("Overview")) {

                String Content = data.get("Overview Title") + "|" +
                        data.get("Overview Text") + "|" +
                        data.get("Overview Link");

                feedcontent.feedContent_Fields_With_Data(Content, 1);

            } else if (Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).get(i).equalsIgnoreCase("Links")) {

                String Content = data.get("Menu Links Title") + "|" +
                        data.get("Menu Links All") + "|" +
                        data.get("Menu Links Special Links All") + "|" +
                        data.get("Menu Links CTA") + "|" +
                        data.get("Special Links Title");

                feedcontent.feedContent_Fields_With_Data(Content, 1);

            } else {

                String Content = data.get("Menu Featured Title") + "|" +
                        data.get("Menu Featured Tours All");

                feedcontent.feedContent_Fields_With_Data(Content, 1);

            }


        }


        sitecore.logOut();


    }


    @Test(dependsOnMethods = {"create_MegaMenu"}, dataProvider = "readTestData")
    //  @Test(dataProvider = "readTestData")
    public void Create_Tours_and_Cruises_Menu(Hashtable<String, String> data) throws InterruptedException {

        ToursandCruisesMenuNodePath = topNodePath + "/" + data.get("MenuName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot(ToursandCruisesMenuNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            // Creating EditorialTemplate template
            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(subMenuRightClickInsertFEControl, data.get("MenuName"));

        }
        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"Create_Tours_and_Cruises_Menu"}, dataProvider = "readTestData")
    //  @Test(dataProvider = "readTestData")
    public void Create_Tours_and_Cruises_SubMenus(Hashtable<String, String> data) throws InterruptedException, IOException {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(ToursandCruisesMenuNodePath + "/" + data.get("SubMenuName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(ToursandCruisesMenuNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("SubMenuName"));

        for (int i = 0; i < Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).size(); i++) {

            sitecore
                    .navigateToWhichTauckNode(ToursandCruisesMenuNodePath + "/" + data.get("SubMenuName").replaceAll(" ", "-").toLowerCase())
                    .rightClickInsertTemplateOrComponent(Arrays.asList(data.get("SubComponent_RightClickInsert").split("\\|")).get(i))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
            feedContent feedcontent = sitecore.createTemplateOrTemplateComponent(Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).get(i));

            if (Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).get(i).equalsIgnoreCase("Overview")) {

                String Content = data.get("Overview Title") + "|" +
                        data.get("Overview Text") + "|" +
                        data.get("Overview Link");

                feedcontent.feedContent_Fields_With_Data(Content, 1);

            } else if (Arrays.asList(data.get("SubMenu_SubComponent").split("\\|")).get(i).equalsIgnoreCase("Links")) {

                String Content = data.get("Menu Links Title") + "|" +
                        data.get("Menu Links All") + "|" +
                        data.get("Menu Links Special Links All") + "|" +
                        data.get("Menu Links CTA") + "|" +
                        data.get("Special Links Title");

                feedcontent.feedContent_Fields_With_Data(Content, 1);

            } else {

                String Content = data.get("Menu Featured Title") + "|" +
                        data.get("Menu Featured Tours All");

                feedcontent.feedContent_Fields_With_Data(Content, 1);

            }


        }


        sitecore.logOut();


    }



    @Test(dependsOnMethods = {"create_MegaMenu"}, dataProvider = "readTestData")
    //  @Test(dataProvider = "readTestData")
    public void Create_Why_Tauck_Menu(Hashtable<String, String> data) throws InterruptedException {

        WhyTauckMenuNodePath = topNodePath + "/" + data.get("MenuName").replaceAll(" ", "-").toLowerCase();


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot(WhyTauckMenuNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            // Creating EditorialTemplate template
            sitecore
                    .navigateToWhichTauckNode(topNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(subMenuRightClickInsertFEControl, data.get("MenuName"));

        }
        sitecore.logOut();

    }


    @Test(dependsOnMethods = {"Create_Why_Tauck_Menu"}, dataProvider = "readTestData")
    //  @Test(dataProvider = "readTestData")
    public void Create_Why_Tauck_SubMenus(Hashtable<String, String> data) throws InterruptedException, IOException {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(WhyTauckMenuNodePath + "/" + data.get("SubMenuName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(WhyTauckMenuNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("SubMenuName"))
                .feedContent_Fields_With_Data(data.get("Content"), 1);
    }


    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) throws IOException {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("create_MegaMenu")) {
            //return DataUtil.getData(xls, "Menus", testSheetName);
            return GDriveSpreedSheetUtil.getData("Menus", testSheetName);

        } else if (method.getName().equals("add_Destinations_SubMenus_And_SubMenus_SubItems")) {
            // return DataUtil.getData(xls, "Destinations", testSheetName);
            return GDriveSpreedSheetUtil.getData("Destinations", testSheetName);

        } else if (method.getName().equals("add_ToursAndCruises_SubMenus_And_SubMenus_SubItems")) {
            // return DataUtil.getData(xls, "Tours and Cruises", testSheetName);
            return GDriveSpreedSheetUtil.getData("Tours and Cruises", testSheetName);

        } else if (method.getName().equals("add_WhyTauck_SubMenus")) {
            // return DataUtil.getData(xls, "WhyTauck", testSheetName);
            return GDriveSpreedSheetUtil.getData("WhyTauck", testSheetName);

        } else if (method.getName().equals("Create_Destinations_Menu")) {
            // return DataUtil.getData(xls, "WhyTauck", testSheetName);
            return GDriveSpreedSheetUtil.getData("Create_Destinations_Node_Inside_Global_Navigation_Header_MegaMenu_Node", testSheetName);

        } else if (method.getName().equals("Create_Destinations_SubMenus")) {
            // return DataUtil.getData(xls, "WhyTauck", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_Destinations_Sub_Component", testSheetName);

        } else if (method.getName().equals("Create_Tours_and_Cruises_Menu")) {
            // return DataUtil.getData(xls, "WhyTauck", testSheetName);
            return GDriveSpreedSheetUtil.getData("Create_Tours and Cruises_Node_Inside_Global_Navigation_Header_MegaMenu_Node", testSheetName);

        } else if (method.getName().equals("Create_Tours_and_Cruises_SubMenus")) {
            // return DataUtil.getData(xls, "WhyTauck", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_Tours_and_Cruises_Sub_Component", testSheetName);

        } else if (method.getName().equals("Create_Why_Tauck_Menu")) {
            // return DataUtil.getData(xls, "WhyTauck", testSheetName);
            return GDriveSpreedSheetUtil.getData("Create_Why_Tauck_Node_Inside_Global_Navigation_Header_MegaMenu_Node", testSheetName);

        } else if (method.getName().equals("Create_Why_Tauck_SubMenus")) {
            // return DataUtil.getData(xls, "WhyTauck", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Add_Why_Tauck_Sub_Component", testSheetName);
        }





            return null;
    }


}
