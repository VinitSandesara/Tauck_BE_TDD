package Template.EditorialTemplate;

import NodeAndComponentConfig.navigateToNode;
import NodeAndComponentConfig.rightClickInsert;
import NodeAndComponentConfig.whatIsTheComponentName;
import TemplateImplementation.Editorial;
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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;


public class EditorialTemplate extends testBase {

    String testSheetName = "EditorialTemplate";
    public mapControlWithDataSource mapcontrolWithDataSource;


    @AfterClass
    public void mapDataSourceWithFrontEndControls() throws Exception {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        invokeBrowser();
        //editorialComponentList();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        editorialTemplateControls editorialtemplateControl = sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()
                .navigateToWhichTauckNodeForMappingDataSourceWithFrontEndControl(navigateToNode.EDITORIAL)

                .clickPresentationLink()
                .clickDetailsLink()
                .clickFinalLayoutTabInsideLayoutDetailsDialog()
                .navigateToDeviceEditor()
                .clickControlsInsideDeviceEditorForMappingDataSourceSequentially();

        List<String> listOfComponentToMapWithDataSource =  DataUtil.grabControlListForMapping(xls,testSheetName,"Template_Control");
        for (int outerloop = 0; outerloop < listOfComponentToMapWithDataSource.size(); outerloop++) {


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
    public void createEditorialTemplate() throws Exception {

        invokeBrowser();

        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        sitecore.launchSitecore()
                .login()
                .goToContentEditorIfNotKickOffUser()

                // Creating EditorialTemplate template
                .navigateToWhichTauckNode(navigateToNode.HOME)
                .rightClickInsertTemplateOrComponent(rightClickInsert.EDITORIAL_TEMPLATE)
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


        // Delete pre-added FE Controls before mapping datasource.
        Object mapControlswithDataSource = sitecore.createTemplateOrTemplateComponent(whatIsTheComponentName.EDITORIAL_TEMPLATE_NAME, mapControlWithDataSource.class.getSimpleName());
        if (mapControlswithDataSource instanceof mapControlWithDataSource)
            ((mapControlWithDataSource) mapControlswithDataSource)
                    .clickPresentationLink()
                    .clickDetailsLink()
                    .clickFinalLayoutTabInsideLayoutDetailsDialog()
                    .navigateToDeviceEditor()
                    .clickControlsInsideDeviceEditor()
                    .removePreAddedFEControls();


    }

  /*  @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_AuthorProfiles_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver,test.get());
        PageFactory.initElements(driver, sitecore);

        if (data.get("ComponentName").equalsIgnoreCase("Author Profiles")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Author Profiles component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();

            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore
                        // Creating sub component of author profiles
                        .navigateToWhichTauckNode(navigateToNode.AUTHOR_PROFILES)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                Object editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)
                    ((EditorialTemplate) editorial).authorProfiles_content_AuthorProfile(DataUtil.splitStringBasedOnUnderscore(data.get("Content_AuthorProfile")).get(i));

            }

        }
    }

  /*  @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_FlexCard_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        if (data.get("ComponentName").equalsIgnoreCase("EditorialTemplate Flex Cards")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Author Profiles component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();

            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore
                        // Creating sub component of author profiles
                        .navigateToWhichTauckNode(navigateToNode.FLEX_CARD)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                Object editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)
                    ((EditorialTemplate) editorial).flexCard_Content_CardSettings(DataUtil.splitStringBasedOnUnderscore(data.get("Content_CardSettings")).get(i));

            }

        }
    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_CategoryCardModule_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        if (data.get("ComponentName").equalsIgnoreCase("Category Card Module")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Author Profiles component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();

            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore
                        // Creating sub component of author profiles
                        .navigateToWhichTauckNode(navigateToNode.CATEGORY_CARD)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                Object editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)
                    ((EditorialTemplate) editorial).categoryCards_content_CategoryCardInfo(DataUtil.splitStringBasedOnUnderscore(data.get("Content_CategoryCardInfo")).get(i));

            }

        }
    }



    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
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

                // Text copy folder component
                .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);
        Object editorial = sitecore.createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());

        // Fill the content
        if (editorial instanceof EditorialTemplate)
            ((EditorialTemplate) editorial)
                    .headerMedia_content_HeaderMediaSettings(data.get("Content_HeaderMediaSettings"));


    }

    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
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

                // Text copy folder component
                .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


        Object editorial = sitecore.createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());

        if (editorial instanceof EditorialTemplate)
            ((EditorialTemplate) editorial)
                    .editorialHero_content_HeroSettings(data.get("Content_HeroSettings"));


    }

    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_TextCopyFolder_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        globalTemplateImplementation sitecore;

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        if (data.get("ComponentName").equalsIgnoreCase("Text Copy Folder")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Text copy folder component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();

            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore
                        .navigateToWhichTauckNode(navigateToNode.TEXT_COPY_FOLDER)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);

                Object editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)
                    ((EditorialTemplate) editorial).textCopyFolder_Content_SingleRichText(DataUtil.splitStringBasedOnUnderscore(data.get("Content_SingleRichText")).get(i));

            }


        }

    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
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

        if (data.get("RightClickInsert").equalsIgnoreCase("Featured FeedContent")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Text copy folder component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());
        }

    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_Editorial_Quotes_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);

        if (data.get("ComponentName").equalsIgnoreCase("EditorialTemplate Quotes")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // EditorialTemplate Quotes component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());

        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();


            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore
                        // Creating Sub component of EditorialTemplate Quotess
                        .navigateToWhichTauckNode(navigateToNode.EDITORIAL_QUOTES)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                Object editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)

                    ((EditorialTemplate) editorial).editorialQuotes_content_Quotes(DataUtil.splitStringBasedOnUnderscore(data.get("Content_Quotes")).get(i));


            }
        }


    }

    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_GridMediaFolder_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("ComponentName").equalsIgnoreCase("Grid Media Folder")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Grid Media Folder component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());

        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();


            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore

                        // Creating Sub component of Grid Media Folder
                        .navigateToWhichTauckNode(navigateToNode.GRID_MEDIA_FOLDER)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                Object editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)

                    ((EditorialTemplate) editorial).gridMediaFolder_content_GridMedia(DataUtil.splitStringBasedOnUnderscore(data.get("Content_GridMedia")).get(i));

            }
        }


    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_HalfWidthMedia_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        Object editorial = null;

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("ComponentName").equalsIgnoreCase("Half Width Media Module")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Grid Media Folder component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());

        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();


            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore

                        // Creating Sub component of Grid Media Folder
                        .navigateToWhichTauckNode(navigateToNode.HALF_WIDTH_MEDIA)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                 editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)

                    ((EditorialTemplate) editorial).halfWidthMeida_Content_Segment(DataUtil.splitStringBasedOnUnderscore(data.get("Content_Segment")).get(i));

            }
            sitecore
                    .navigateToWhichTauckNode(navigateToNode.HALF_WIDTH_MEDIA);
            ((EditorialTemplate) editorial).mediaCarouselAndHalfWidthMedia_content_MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")));

        }


    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_FeaturedBrand_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("ComponentName").equalsIgnoreCase("Featured Brand Module")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Grid Media Folder component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());

        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();


            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore

                        // Creating Sub component of Grid Media Folder
                        .navigateToWhichTauckNode(navigateToNode.FEATURED_BRAND)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                Object editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)

                    ((EditorialTemplate) editorial).featuredBrand_Content_FeaturedBrandInfo(DataUtil.splitStringBasedOnUnderscore(data.get("Content_FeaturedBrandInfo")).get(i));

            }
        }


    }


    @Test(dependsOnMethods = {"createEditorialTemplate"}, dataProvider = "readTestData")
    public void add_MediaCarousel_Component_Inside_EditorialTemplate(Hashtable<String, String> data) throws InterruptedException {

        Object editorial = null;

        if (!DataUtil.isTestExecutable(xls, testSheetName)) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }

        if (!data.get(excelConfig.RUNMODE_COL).equals("Y")) {
            throw new SkipException("Skipping the test as Rnumode is N");
        }


        invokeBrowser();
        globalTemplateImplementation sitecore = new globalTemplateImplementation(driver, test.get());
        PageFactory.initElements(driver, sitecore);


        if (data.get("ComponentName").equalsIgnoreCase("Media Carousel Cards")) {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser()

                    // Grid Media Folder component
                    .navigateToWhichTauckNode(navigateToNode.EDITORIAL)
                    .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                    .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME)
                    .createTemplateOrTemplateComponent(data.get("ComponentName"), this.getClass().getSimpleName());

        } else {
            sitecore
                    .login()
                    .goToContentEditorIfNotKickOffUser();


            for (int i = 0; i < DataUtil.splitStringBasedOnComma(data.get("ComponentName")).size(); i++) {

                sitecore

                        // Creating Sub component of Grid Media Folder
                        .navigateToWhichTauckNode(navigateToNode.MEIDA_CAROUSEL)
                        .rightClickInsertTemplateOrComponent(data.get("RightClickInsert"))
                        .switchToContentIframeDialog(Config.PARENT_FRAME, Config.CHILD_FRAME);


                editorial = sitecore.createTemplateOrTemplateComponent(DataUtil.splitStringBasedOnComma(data.get("ComponentName")).get(i), this.getClass().getSimpleName());

                if (editorial instanceof EditorialTemplate)

                  ((EditorialTemplate) editorial).mediaCarousel_content_CardSettings(DataUtil.splitStringBasedOnUnderscore(data.get("Content_CardSettings")).get(i));

            }
            sitecore
                    .navigateToWhichTauckNode(navigateToNode.MEIDA_CAROUSEL);
                    ((EditorialTemplate) editorial).mediaCarouselAndHalfWidthMedia_content_MultiListSelection(DataUtil.splitStringBasedOnComma(data.get("ComponentName")));

        }


    }*/


    @DataProvider(name = "readTestData")
    public Object[][] getData(Method method) {

        Xls_Reader xls = new Xls_Reader(excelConfig.TESTDATA_XLS_PATH);

        if (method.getName().equals("add_AuthorProfiles_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "AuthorProfiles", testSheetName);

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

        }else if (method.getName().equals("add_FeaturedBrand_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "FeaturedBrand", testSheetName);

        }else if (method.getName().equals("add_HalfWidthMedia_Component_Inside_EditorialTemplate")) {
            return DataUtil.getData(xls, "HalfWidthMedia", testSheetName);
        }





        return null;
    }


}
