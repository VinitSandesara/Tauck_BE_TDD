package Template;

import GoogleDriveConfigration.GDriveSpreedSheetUtil;
import TemplateImplementation.HomePage;
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

public class ShipDetail extends testBase {

    String testSheetName = "ShipDetail";
    String homeShipNodePath;
    String dataShipNodePath;
    String deckPlansCabinCatNodePath;
    String deckPlansDeckNodePath;




    @Test(dataProvider = "readTestData")
    public void Create_Ship_Inside_Home_Folder(Hashtable<String, String> data) throws InterruptedException {


        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

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
    public void input_Onboard_Experience_Section_Fields(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        String ContentString = data.get("Onboard Experience Header") + "|" +
                data.get("Onboard Experience Content");


        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode_ForSectionInput(homeShipNodePath)

                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandSections("Section_Onboard_Experience")
                .feedContent_Fields_With_Data(ContentString,7)
                .logOut();

    }


  /*  @Test(dataProvider = "readTestData")
    public void Create_Ship_Inside_Data_Folder(Hashtable<String, String> data) throws InterruptedException {


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
        if(sitecore.checkWhetherParentNodeIsPresentOrNot(dataShipNodePath)!=true) {
            System.out.println("Parent Node already present please go ahead...");
        }else {

            sitecore
                    .navigateToWhichTauckNode("/sitecore/content/Tauck/data/ships")
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ShipName"));

        }
        sitecore.logOut();


    }


  /*  @Test(dependsOnMethods = {"Create_Ship_Inside_Data_Folder"}, dataProvider = "readTestData")
    public void input_Ship_Specification_Sections_Fields(Hashtable<String, String> data) throws Exception {

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        String ContentString = data.get("Maximum Passengers") + "|" +
                data.get("Guests") + "|" +
                data.get("Year Built") + "|" +
                data.get("Year Renovated") + "|" +
                data.get("Length") + "|" +
                data.get("Crew Members") + "|" +
                data.get("Key Features Abbrev") + "|" +
                data.get("Draft") + "|" +
                data.get("Beam") + "|" +
                data.get("Tonnage") + "|" +
                data.get("Cruising Speed") + "|" +
                data.get("Air Conditioned") + "|" +
                data.get("Elevator") + "|" +
                data.get("Registry") + "|" +
                data.get("Crew Nationality") + "|" +
                data.get("Internet") + "|" +
                data.get("Key Features") + "|" +
                data.get("Amenities");

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNode_ForSectionInput(dataShipNodePath)

                .checkAndCollapsedAlreadyExpandedContentSectionsPanel()
                .expandShipSpecificationSection()
                .feedContent_Fields_With_Data(ContentString,1)
                .logOut();

    }
*/



   /* @Test(dependsOnMethods = {"Create_Ship_Inside_Data_Folder"}, dataProvider = "readTestData")
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
*/

/* Here reason for adding alwaysRun = true what happen is if any dependent method fails or skip this method automatically fails and/or skips so to always run this method
 after dependent method ran.
 */
 /*   @Test(dependsOnMethods = {"Create_Deck_Plans_Deck_Folder", "Create_Deck_Plans_CabinCategories_Inside_CabinCategory_Folder"}, dataProvider = "readTestData", alwaysRun = true)
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
        }





        return null;
    }




} // Main class is closing
