package Template;

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
import java.util.Hashtable;

/*

- Here method "bind_Content_Fields_In_One_String("HeroSettings", testSheetName)" that can apply to only that Test where it has one row. In case of multiple rows it doesnt work.
    Example :

    ======== Valid ======

            bind_Content_Fields_In_One_String("HeroSettings", testSheetName)

    ======== In- Valid ======

            bind_Content_Fields_In_One_String("Create_DeckPlans_Decks_Inside_Decks_folder", testSheetName)

            Create_DeckPlans_Decks_Inside_Decks_folder : this test case has more then one rows in this case it doesnt work

 */


public class ShipDetail extends testBase {

    String testSheetName = "ShipDetail";
    String ShipName;
    String homeShipNodePath;
    String dataShipNodePath;
    String deckPlansCabinCatNodePath;
    String deckPlansDeckNodePath;
    String PortraitHighlightIntraCopyPath;
    String shipPartnersWithPartnersInsideNodePath;
    String shipPartnersNodePath;




  /*  @Test(dataProvider = "readTestData")
    public void Create_Ship_Inside_Home_Folder(Hashtable<String, String> data) throws InterruptedException, IOException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        ShipName =  data.get("ShipName");
        homeShipNodePath = "/sitecore/content/Tauck/Home/ships" + "/" + data.get("ShipName").replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if(sitecore.checkWhetherParentNodeIsPresentOrNot(homeShipNodePath)!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/Home/ships")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ShipName"));

        }
        sitecore.logOut();



    }


    @Test(dependsOnMethods = {"Create_Ship_Inside_Home_Folder"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void Create_And_Input_Highlight_Intro_Copy(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        PortraitHighlightIntraCopyPath = homeShipNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();


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

                    .navigateToWhichTauckNode(homeShipNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(bind_Content_Fields_In_One_String("Portrait Highlights and Introduction Copy", testSheetName));
        }

        sitecore.logOut();


    }


    @Test(dependsOnMethods = {"Create_And_Input_Highlight_Intro_Copy"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void Create_Portrait_Highlights_Cards(Hashtable<String, String> data) throws Exception {


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

            String HighLightImage = data.get("Highlight Title") + "|" +
                    data.get("Highlight Subtitle") + "|" +
                    data.get("Highlight Image");

            String PortraitOnly = data.get("Hover Title") + "|" +
                    data.get("Hover Subtitle") + "|" +
                    data.get("Hover Copy");

            sitecore

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(PortraitHighlightIntraCopyPath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    .navigateToWhichTauckNode(PortraitHighlightIntraCopyPath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .feedContent_Fields_With_Data(HighLightImage, 0)
                    .feedContent_Fields_With_Data(PortraitOnly, 2);

        } else if(data.get("RightClickInsert").equalsIgnoreCase("HighlightQuote")) {

            String TourHighlightQuote = data.get("Highlight Title") + "|" +
                    data.get("Highlight Subtitle") + "|" +
                    data.get("Profile Thumbnail");

            sitecore

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(PortraitHighlightIntraCopyPath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    .navigateToWhichTauckNode(PortraitHighlightIntraCopyPath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(TourHighlightQuote);

        } else {

            String TourHighlightVideo = data.get("Highlight Title") + "|" +
                    data.get("Highlight Subtitle") + "|" +
                    data.get("Brightcove Video Id") + "|" +
                    data.get("VideoImage");

            sitecore

                    // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                    .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(PortraitHighlightIntraCopyPath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                    .navigateToWhichTauckNode(PortraitHighlightIntraCopyPath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"))
                    .fill_Component_Content_With_Data(TourHighlightVideo);
        }

        sitecore.logOut();
    }



    @Test(dependsOnMethods = {"Create_Ship_Inside_Home_Folder"}, dataProvider = "readTestData")
    // @Test(dataProvider = "readTestData")
    public void input_Hero_Settings_Section_Fields_For_Hero_Module(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        invokeBrowser();

        HomePage homePage = new HomePage(driver, test.get());
        PageFactory.initElements(driver, homePage);

        homePage
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode(homeShipNodePath);

        homePage
                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandSections("Section_Hero_Settings")
                .input_Sections_Fields_Save_And_Logout(bind_Content_Fields_In_One_String("HeroSettings", testSheetName), counter);


    }

    // this test is dependents on "Create_Deck_Plans_Deck_Folder" is because if you dont have deck created there is no way you can select here, Again instead of keeping depends on method
    // "Create_Deck_Plans_Decks_Inside_Decks_Folder" i kept "Create_Deck_Plans_Deck_Folder" because "Create_Deck_Plans_Decks_Inside_Decks_Folder" is depends on many other test so for this test
    // will take more time to complete. One thing to keep in mind for this test to build from jenkins is before building this job you have to build "Create_Deck_Plans_Deck_Folder" and
    // "Create_Deck_Plans_Decks_Inside_Decks_Folder" these2 first.

    @Test(dependsOnMethods = {"Create_Deck_Plans_Deck_Folder"})
    public void input_Entity_References_Section_Fields_To_Display_Decks() throws Exception {

       invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

         sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode_ForSectionInput(homeShipNodePath)

                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandSections("Section_Entity_References")
                .Select_Option_From_DropDown(1,ShipName)

       // De select already selected tree list values if any before re selecting or else error will thrown
                .Deselect_TreeList_Selected_Options_To_Reselect()

                .Select_List_Option_From_Multi_Tree_List(ShipName)
                .logOut();

    }


    @Test(dependsOnMethods = {"Create_Ship_Inside_Home_Folder"}, dataProvider = "readTestData")
    public void input_Onboard_Experience_Section_Fields(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode_ForSectionInput(homeShipNodePath)

                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandSections("Section_Onboard_Experience")
                .input_Sections_Fields_Save_And_Logout(bind_Content_Fields_In_One_String("Onboard_Experience", testSheetName),7);

    }
*/

    @Test(dataProvider = "readTestData")
    public void Create_Ship_Inside_Data_Folder(Hashtable<String, String> data) throws InterruptedException, IOException {

        // GDriveSpreedSheetUtil.getTestDataFromExcel("Ship Partners", testSheetName);


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        dataShipNodePath = "/sitecore/content/Tauck/data/ships" + "/" + data.get("ShipName").replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if (sitecore.checkWhetherParentNodeIsPresentOrNot(dataShipNodePath) != true) {
            System.out.println("Parent Node already present please go ahead...");
        } else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/data/ships")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ShipName"));

        }
        sitecore.logOut();


    }


    @Test(dependsOnMethods = {"Create_Ship_Inside_Data_Folder"}, dataProvider = "readTestData")
    public void Create_Ship_Partners_Folders(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        if (data.get("RightClickInsert").equalsIgnoreCase("Ship Partners Folder")) {

            shipPartnersNodePath = dataShipNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();

            // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
            if (sitecore.checkWhetherParentNodeIsPresentOrNot(shipPartnersNodePath) != true) {
                System.out.println("Parent Node already present please go ahead...");
            } else {

                sitecore
                        .navigateToWhichTauckNode(dataShipNodePath)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                        .createTemplateOrTemplateComponent(data.get("ComponentName"));
            }

        } else {

            shipPartnersWithPartnersInsideNodePath = shipPartnersNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase();

            // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
            if (sitecore.checkWhetherParentNodeIsPresentOrNot(shipPartnersWithPartnersInsideNodePath) != true) {
                System.out.println("Parent Node already present please go ahead...");
            } else {

                sitecore
                        .navigateToWhichTauckNode(shipPartnersNodePath)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                        .createTemplateOrTemplateComponent(data.get("ComponentName"))
                        .fill_Component_Content_With_Data(data.get("Content"));
            }

        }

        sitecore.logOut();


    }


    @Test(dependsOnMethods = {"Create_Ship_Partners_Folders"}, dataProvider = "readTestData")
    public void Create_Ship_Partners_Half_Width_Media_Segment(Hashtable<String, String> data) throws Exception {


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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(shipPartnersWithPartnersInsideNodePath + "/" + data.get("ComponentName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(shipPartnersWithPartnersInsideNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("ComponentName"))
                .fill_Component_Content_With_Data(data.get("Content"))
                .logOut();


    }






 /*  @Test(dependsOnMethods = {"Create_Ship_Inside_Data_Folder"}, dataProvider = "readTestData")
    public void input_Ship_Specification_Sections_Fields(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode_ForSectionInput(dataShipNodePath)

                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandShipSpecificationSection()
                .feedContent_Fields_With_Data(bind_Content_Fields_In_One_String("Ship_Specification", testSheetName),1)
                .logOut();

    }




   @Test(dependsOnMethods = {"Create_Ship_Inside_Data_Folder"}, dataProvider = "readTestData")
    public void Create_Deck_Plans_Deck_Folder(Hashtable<String, String> data) throws InterruptedException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        deckPlansDeckNodePath = dataShipNodePath + "/" + data.get("DeckFolderName").replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if(sitecore.checkWhetherParentNodeIsPresentOrNot(deckPlansDeckNodePath)!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode(dataShipNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("DeckFolderName"));

        }
        sitecore.logOut();

    }

     @Test(dependsOnMethods = {"Create_Ship_Inside_Data_Folder"}, dataProvider = "readTestData")
    public void Create_Deck_Plans_CabinCategories_Folder(Hashtable<String, String> data) throws InterruptedException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        deckPlansCabinCatNodePath = dataShipNodePath + "/" + data.get("CabinCatFolderName").replaceAll(" ", "-").toLowerCase();

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();

        // Checking if parent node is present no need to create again, just move forward, if not it will create. This is required when there dependent method that is dependent on this test method.
        if(sitecore.checkWhetherParentNodeIsPresentOrNot(deckPlansCabinCatNodePath)!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode(dataShipNodePath)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("CabinCatFolderName"));

        }
        sitecore.logOut();

    }


// Here reason for adding alwaysRun = true what happen is if any dependent method fails or skip this method automatically fails and/or skips so to always run this method
// after dependent method ran.

   @Test(dependsOnMethods = {"Create_Deck_Plans_Deck_Folder", "Create_Deck_Plans_CabinCategories_Folder"}, dataProvider = "readTestData", alwaysRun = true)
    public void Create_Deck_Plans_Decks_Inside_Decks_Folder(Hashtable<String, String> data) throws InterruptedException, IOException {

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        String ContentString = data.get("Deck Name") + "|" +
                data.get("Deck Image") + "|" +
                data.get("Deck Number");


        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

                // This is required in case if user wants to update the data, in that case it will first delete the component and re add with new data.
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(deckPlansDeckNodePath+"/"+data.get("DecksName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(deckPlansDeckNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("DecksName"))
                .fill_Component_Content_With_Data(ContentString)

   // Reason for keeping this method dependent on "Create_Deck_Plans_CabinCategories_Inside_CabinCategory_Folder" is because Deck Tree list selection need
    //   cabin categories first to be created, Since this method is dependent on many other methods, once you have all cabin categories created and on next run
    //   after any changes needed for Decks keep cabin categories runmode to N so the execution can be faster or else it will re-create cabin categories again
    //   and then it will start this method.


                .select_From_Content_TreeList(0,Arrays.asList(data.get("Deck Cabin Category").split(",")))
                .logOut();


    }

     @Test(dependsOnMethods = {"Create_Deck_Plans_CabinCategories_Folder"}, dataProvider = "readTestData")
    public void Create_Deck_Plans_CabinCategories_Inside_CabinCategory_Folder(Hashtable<String, String> data) throws InterruptedException, IOException {

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
                .checkIsComponentOrSubComponentExistInsideTemplateIfSoDeleteIt(deckPlansCabinCatNodePath+"/"+data.get("CabinCatName").replaceAll(" ", "-").toLowerCase())

                .navigateToWhichTauckNode(deckPlansCabinCatNodePath)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("CabinCatName"))
                .logOut();

    }
*/


    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) throws IOException {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equalsIgnoreCase("Create_Ship_Inside_Home_Folder")) {
            // return DataUtil.getData(xls, "HomeTemplateName", testSheetName);
            return GDriveSpreedSheetUtil.getData("Home_Ships_Name", testSheetName);

        } else if (method.getName().equals("Create_Ship_Inside_Data_Folder")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("Data_Ships_Name", testSheetName);

        } else if (method.getName().equals("input_Ship_Specification_Sections_Fields")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("Ship_Specification", testSheetName);

        } else if (method.getName().equals("Create_Deck_Plans_Deck_Folder")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("DeckPlans_Decks", testSheetName);

        } else if (method.getName().equals("Create_Deck_Plans_Decks_Inside_Decks_Folder")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("Create_DeckPlans_Decks_Inside_Decks_folder", testSheetName);

        } else if (method.getName().equals("Create_Deck_Plans_CabinCategories_Folder")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("DeckPlans_CabinCategories", testSheetName);

        } else if (method.getName().equals("Create_Deck_Plans_CabinCategories_Inside_CabinCategory_Folder")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("Create_DeckPlans_CabinCategories_Inside_CabinCategories_folder", testSheetName);

        } else if (method.getName().equals("input_Onboard_Experience_Section_Fields")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("Onboard_Experience", testSheetName);

        } else if (method.getName().equals("input_Hero_Settings_Section_Fields_For_Hero_Module")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("HeroSettings", testSheetName);

        } else if (method.getName().equals("Create_And_Input_Highlight_Intro_Copy")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("Portrait Highlights and Introduction Copy", testSheetName);

        } else if (method.getName().equals("Create_Portrait_Highlights_Cards")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getData("Portrait Highlights Cards", testSheetName);

        } else if (method.getName().equals("Create_Ship_Partners_Half_Width_Media_Segment")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Ship Partners List Of Partners", testSheetName);

        } else if (method.getName().equals("Create_Ship_Partners_Folders")) {
            // return DataUtil.getData(xls, "HeroSettings", testSheetName);
            return GDriveSpreedSheetUtil.getTestDataFromExcel("Ship_Partners", testSheetName);
        }


        return null;
    }


} // Main class is closing
