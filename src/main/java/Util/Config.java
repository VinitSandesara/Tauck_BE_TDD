package Util;

import java.util.Hashtable;

public class Config {

    public static final boolean GRID_RUN = true;

    /*  For switching in between frames */

    public static final String PARENT_FRAME = "jqueryModalDialogsFrame";
    public static final String CHILD_FRAME = "scContentIframeId0";
    public static final String CHILD_FRAME_TWO = "scContentIframeId1";
    public static final String CHILD_FRAME_THREE = "scContentIframeId2";

   // public static final String CONTROL_PROPERTIES_FRAMES = "feRTEContainer";



    // URLs-prod

    public static final String DEV_HOMEPAGE_URL = System.getProperty("env");
    public static final String DEV_USERNAME = System.getProperty("username");
    public static final String DEV_PASSWORD = System.getProperty("password");

    /* public static final String DEV_HOMEPAGE_URL = "http://qa2017.tauck.com/sitecore/login";
    public static final String DEV_USERNAME = "v";
    public static final String DEV_PASSWORD = "v";*/


    // URLs-uat
  //  public static final String QA_HOMEPAGE_URL = "http://qa2017.tauck.com/sitecore/login";
    public static final String QA_HOMEPAGE_URL = System.getProperty("env");
    public static final String QA_USERNAME = System.getProperty("username");
    public static final String QA_PASSWORD = System.getProperty("password");


    public static final String ENV = "DEV"; //PROD, UAT,SAT

    public static final String REPORTS_PATH = System.getProperty("user.dir") + "/Reports/extent.html";
    //public static final String REPORTS_PATH = "/Users/vsandesara/Desktop/tauck/Reports/";


    public static Hashtable<String, String> table;

    public static Hashtable<String, String> getEnvDetails() {
        if (table == null) {
            table = new Hashtable<String, String>();
            if (ENV.equals("DEV")) {
                table.put("url", DEV_HOMEPAGE_URL);
                table.put("username", DEV_USERNAME);
                table.put("password", DEV_PASSWORD);
            } else if (ENV.equals("QA")) {
                table.put("url", QA_HOMEPAGE_URL);
                table.put("username", QA_USERNAME);
                table.put("password", QA_PASSWORD);
            }

        }
        return table;


    }
}
