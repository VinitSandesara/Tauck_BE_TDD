package Template;

import NodeAndComponentConfig.navigateToNode;
import NodeAndComponentConfig.rightClickInsert;
import NodeAndComponentConfig.whatIsTheComponentName;
import TemplateImplementation.globalTemplateImplementation;
import Util.Config;
import Util.DataUtil;
import Util.Xls_Reader;
import Util.excelConfig;
import base.testBase;
import mapDataSourceWithFE.mapControlWithDataSource;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class MegaMenu extends testBase {

    String testSheetName = "MegaMenu";
    public mapControlWithDataSource mapcontrolWithDataSource;

    @Test
    public void dummyOne() {

        invokeBrowser();
    }


    @Test(dataProvider = "readTestData")
    public void create_MegaMenu_And_SubMenus(Hashtable<String, String> data) throws InterruptedException {

        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser();


        for(int i=0;i<Arrays.asList(data.get("RightClickInsert").split("\\|")).size();i++) {

            sitecore
                    .navigateToWhichTauckNode(Arrays.asList(data.get("NavigateToNodePath").split("\\|")).get(i))
                    .rightClickInsertTemplateOrComponent(Arrays.asList(data.get("RightClickInsert").split("\\|")).get(i))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .insertFromTemplateWhenComponentIsNotPresentOnRightClickInsert(Arrays.asList(data.get("ComponentToBeInsert").split("\\|")).get(i),Arrays.asList(data.get("SubMenus").split("\\|")).get(i) );


        }

    }



   //  @Test(dependsOnMethods = {"create_MegaMenu_And_SubMenus"}, dataProvider = "readTestData")
    @Test(dataProvider = "readTestData")
    public void add_Destinations_SubMenus_And_SubMenus_SubItems(Hashtable<String, String> data) throws InterruptedException {


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

          // Create Menu - sub Menus

                .navigateToWhichTauckNode(data.get("NavigateToNodePath"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("SubMenus"), this.getClass().getSimpleName());

         // Create Sub Menus - Sub Items
        for(int i=0;i<Arrays.asList(data.get("SubItemsComponentName").split("\\|")).size();i++) {

            sitecore
                .navigateToWhichTauckNode(data.get("SubItemsNavigateToNodePath"))
                .rightClickInsertTemplateOrComponent(Arrays.asList(data.get("SubItemsRightClickInsert").split("\\|")).get(i))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(Arrays.asList(data.get("SubItemsComponentName").split("\\|")).get(i), this.getClass().getSimpleName());

        }

    }

 // @Test(dependsOnMethods = {"create_MegaMenu_And_SubMenus"}, dataProvider = "readTestData")
    @Test(dataProvider = "readTestData")
    public void add_ToursAndCruises_SubMenus_And_SubMenus_SubItems(Hashtable<String, String> data) throws InterruptedException {


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

                // Create Menu - sub Menus

                .navigateToWhichTauckNode(data.get("NavigateToNodePath"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("SubMenus"), this.getClass().getSimpleName());

        // Create Sub Menus - Sub Items
        for(int i=0;i<Arrays.asList(data.get("SubItemsComponentName").split("\\|")).size();i++) {

            sitecore
                    .navigateToWhichTauckNode(data.get("SubItemsNavigateToNodePath"))
                    .rightClickInsertTemplateOrComponent(Arrays.asList(data.get("SubItemsRightClickInsert").split("\\|")).get(i))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(Arrays.asList(data.get("SubItemsComponentName").split("\\|")).get(i), this.getClass().getSimpleName());

        }

    }



  //  @Test(dependsOnMethods = {"create_MegaMenu_And_SubMenus"}, dataProvider = "readTestData")
    @Test(dataProvider = "readTestData")
    public void add_WhyTauck_SubMenus(Hashtable<String, String> data) throws InterruptedException {


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore
                .login()
                .goToContentEditorIfNotKickOffUser()

                // Create Menu - sub Menus

                .navigateToWhichTauckNode(data.get("NavigateToNodePath"))
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                .createTemplateOrTemplateComponent(data.get("SubMenus"), this.getClass().getSimpleName());


    }










    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("create_MegaMenu_And_SubMenus")) {
            return DataUtil.getData(xls, "Menus", testSheetName);

        }else if (method.getName().equals("add_Destinations_SubMenus_And_SubMenus_SubItems")) {
            return DataUtil.getData(xls, "Destinations", testSheetName);

        }else if (method.getName().equals("add_ToursAndCruises_SubMenus_And_SubMenus_SubItems")) {
            return DataUtil.getData(xls, "Tours and Cruises", testSheetName);

        }else if (method.getName().equals("add_WhyTauck_SubMenus")) {
            return DataUtil.getData(xls, "WhyTauck", testSheetName);
        }

            return null;
    }


}
