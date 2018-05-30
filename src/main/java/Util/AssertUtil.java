package Util;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

public class AssertUtil extends WaitUtil {


    public AssertUtil(WebDriver driver, ExtentTest test) {
        super(driver, test);
    }


    public void verifyTrue(boolean condition, int actualSize, int expectedSize, String verificationMsg) {


        try {
            Assert.assertTrue(condition, verifyTrueJavaScriptStringPassMsg(actualSize,expectedSize, verificationMsg));
            test.pass(verifyTrueJavaScriptStringPassMsg(actualSize,expectedSize, verificationMsg));

        } catch (AssertionError e) {

            test.warning(verifyTrueJavaScriptStringFailMsg(actualSize,expectedSize, verificationMsg));
            throw e;


        }
    }


    public String verifyTrueJavaScriptStringPassMsg(int actualSize, int expectedSize, String verificationMsg) {

        /* String FEVerification = Arrays.asList(verificationMsg.split("\\|")).get(0);
        String BEVerification = Arrays.asList(verificationMsg.split("\\|")).get(1);

        return "<b><font size=\"3\" color=\"blue\">==================  "+FEVerification+" ================== </font></b> <br />" +
                "<b><font size=\"3\" color=\"#3F6C34\"> &nbsp "+actualSize+" </font></b> <br /> " +
                "<b><font size=\"3\" color=\"blue\">================== Which is "+BEVerification+" ================== </font></b> <br />  " +
                "<b><font size=\"3\" color=\"#3F6C34\">  &nbsp "+expectedSize+" </font></b> ";*/

        return "<b><font size=\"3\" color=\"blue\"> "+verificationMsg+" Actual Size is :- </font></b> <br />" +
                "<b><font size=\"3\" color=\"#3F6C34\"> &nbsp \" "+actualSize+" \" &nbsp </font></b> <br /> " +
                "<b><font size=\"3\" color=\"blue\"> that is captured from FE which is matching with Expected Size is :- </font></b>  <br />" +
                "<b><font size=\"3\" color=\"#3F6C34\">  &nbsp  \" "+expectedSize+" \" &nbsp </font></b> " ;
    }

    public String verifyTrueJavaScriptStringFailMsg(int actualSize, int expectedSize, String verificationMsg) {

      /*  String FEVerification = Arrays.asList(verificationMsg.split("\\|")).get(0);
        String BEVerification = Arrays.asList(verificationMsg.split("\\|")).get(1);

        return "<b><font size=\"3\" color=\"blue\">==================  "+FEVerification+" ================== </font></b> <br />" +
                "<b><font size=\"3\" color=\"#F71C04\">  &nbsp "+actualSize+" </font></b> <br /> " +
                "<b><font size=\"3\" color=\"blue\">================== Which is <b><font size=\"3\" color=\"red\"> NOT </font></b> "+BEVerification+" ================== </font></b> <br />  " +
                "<b><font size=\"3\" color=\"#F71C04\">  &nbsp "+expectedSize+" </font></b> "; */

        return "<b><font size=\"3\" color=\"blue\"> "+verificationMsg+" Actual Size is :- </font></b>   <br />"  +
                "<b><font size=\"3\" color=\"#F71C04\"> \" "+actualSize+" \" </font></b>   <br />" +
                "<b><font size=\"3\" color=\"blue\"> that is captured from FE which is " +
                "  <b><font size=\"3\" color=\"red\"> NOT </font></b> matching with Expected Size is :- </font></b>   <br />" +
                "<b><font size=\"3\" color=\"#F71C04\"> \" "+expectedSize+" \" </font></b>  <br /> " +
                " <b><font size=\"3\" color=\"blue\"> that is initially been used to feed while creating template in BE/Sitecore, " +
                " the changes might have recently been made by someone in Sitecore if it is valid change please ignore this failure or else take appropriate action. </font></b> ";



    }

    public void verifyAssertEquals(String actualText, String expectedText, String verificationMsg) throws IOException {
        try {

            Assert.assertEquals(actualText, expectedText, verifyAssertEqualsJavaScriptStringPassMsg(actualText,expectedText,verificationMsg));
            test.pass(verifyAssertEqualsJavaScriptStringPassMsg(actualText,expectedText,verificationMsg));
        } catch (AssertionError e) {
            test.warning(verifyAssertEqualsJavaScriptStringFailMsg(actualText,expectedText, verificationMsg));
            throw e;
        }

    }

    public String verifyAssertEqualsJavaScriptStringPassMsg(String actualText, String expectedText, String verificationMsg) {

      /*  String FEVerification = Arrays.asList(verificationMsg.split("\\|")).get(0);
        String BEVerification = Arrays.asList(verificationMsg.split("\\|")).get(1);

        return  "<b><font size=\"3\" color=\"blue\">==================  "+FEVerification+" ================== </font></b> <br />  " +
                "<b><font size=\"3\" color=\"#3F6C34\"> &nbsp "+actualText+" </font></b> <br />  " +
                "<b><font size=\"3\" color=\"blue\">================== Which is "+BEVerification+" ================== </font></b>  <br /> " +
                "<b><font size=\"3\" color=\"#3F6C34\"> &nbsp "+expectedText+" </font></b> ";*/

        return "<b><font size=\"3\" color=\"blue\"> "+verificationMsg+" Actual Value is :- </font></b> <br /> " +
                "<b><font size=\"3\" color=\"#3F6C34\"> \" "+actualText+" \" </font></b> <br /> " +
                "<b><font size=\"3\" color=\"blue\"> that is captured from SiteCore while creating template which is matching with Expected Value is:- </font></b> <br /> " +
                "<b><font size=\"3\" color=\"#3F6C34\"> \" "+expectedText+" \" </font></b> " ;

    }

    public String verifyAssertEqualsJavaScriptStringFailMsg(String actualText, String expectedText, String verificationMsg) {

       /* String FEVerification = Arrays.asList(verificationMsg.split("\\|")).get(0);
        String BEVerification = Arrays.asList(verificationMsg.split("\\|")).get(1);

        return "<b><font size=\"3\" color=\"blue\">==================  "+FEVerification+" ================== </font></b> <br />  " +
                "<b><font size=\"3\" color=\"#F71C04\"> &nbsp "+actualText+" </font></b> <br />  " +
                "<b><font size=\"3\" color=\"blue\">================== Which is <b><font size=\"3\" color=\"red\"> NOT </font></b> "+BEVerification+" ================== </font></b>  <br /> " +
                "<b><font size=\"3\" color=\"#F71C04\"> &nbsp "+expectedText+" </font></b> ";*/

        return "<b><font size=\"3\" color=\"blue\"> "+verificationMsg+" Actual Value is :- </font></b> <br />" +
                "<b><font size=\"3\" color=\"#F71C04\">  \" "+actualText+" \"  </font></b> <br />" +
                "<b><font size=\"3\" color=\"blue\"> that is captured from SiteCore while creating template which is " +
                "  <b><font size=\"4\" color=\"red\"> NOT </font></b> matching with Expected Value is :- </font></b> <br />" +
                "<b><font size=\"3\" color=\"#F71C04\">  \" "+expectedText+" \" </font></b> <br />" +
                " <b><font size=\"3\" color=\"blue\"> that is initially been used to feed while creating template in BE/Sitecore, " +
                " the changes might have recently been made by someone in Sitecore if it is valid change please ignore this failure or else take appropriate action. </font></b> ";



    }




}
