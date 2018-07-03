package Locators;

public class HomePageLocators {

    public static final String CONTENT_HERO_SETTINGS = "//div[starts-with(@id,'Section_Hero_Settings')]";

    public static final String CONTENT_TRAVELLING_WITH_TAUCK = "//div[starts-with(@id,'Section_Traveling_With_Tauck')]";

    public static final String CONTENT_TAUCK_EXPERIENCE_COMPONENT = "//div[starts-with(@id,'Section_Tauck_Experience_Component')]";

    public static final String CONTENT_FEATURED_CONTENT = "//div[starts-with(@id,'Section_Featured_Content')]";

    public static final String TOTAL_CONTENT_SECTIONS_PANEL = "//div[@class='scEditorSections']/div";

    public static final String CONTENT_SECTIONS_PANEL_TABLES = "//div[@class='scEditorSections']/table/tbody/tr/td";

    public static final String CONTENT_SECTIONS_PANEL_TABLES_ALTERNATIVE = "//table[contains(concat(' ', @class, ' '), ' scEditorSectionPanel ') and not(contains(concat(' ', @ style, ' '), 'display:none'))]/tbody/tr/td/table";


    public static final String ALREADY_EXPANDED_CONTENT_SECTIONS_PANEL = "//div[@class='scEditorSectionCaptionExpanded']";


    public static final String CONTENT_SHIP_SPECIFICATION = "//div[starts-with(@id,'Section_Specifications')]";

}
