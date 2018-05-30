package Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class DataUtil {


    public static void main(String arg[]) throws IOException {

        //System.out.println(filename);
        Xls_Reader xls = null;
        xls = new Xls_Reader("/Users/vsandesara/Desktop/TauckSitecore/src/Data/TestData.xlsx");

        //System.out.println("Yes or Nt :-  " + isTestExecutable(xls,"LoginTest"));

        grabControlListForMapping(xls, "MediaCarousel", "Editorial_WhyWeTravel");


    }




    public static List<String> grabControlListForMapping(Xls_Reader xls, String testCaseName, String sheetName) {

        int testStartRowNum = 1;
        int dataStartRowNum;
        int colStartRowNum;
        System.out.println("Value is - " + xls.getCellData(sheetName, 0, testStartRowNum));
        if (testCaseName != null) {
            while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
                testStartRowNum++;
            }
        }
        System.out.println("Test starts from row - " + testStartRowNum);

        if (testCaseName != null) {
            colStartRowNum = testStartRowNum + 1;
        } else {
            colStartRowNum = 1;
        }

        if (testCaseName != null) {
            dataStartRowNum = testStartRowNum + 2;
        } else {
            dataStartRowNum = testStartRowNum + 1;
        }

        // calculate rows of data
        int rows = 0;
        while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
            rows++;
        }
        System.out.println("Total rows are  - " + rows);

        //calculate total cols
        int cols = 0;
        while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
            cols++;
        }
        System.out.println("Total cols are  - " + cols);

        Hashtable<String, String> data = new Hashtable<String, String>();
        List<String> listOfComponentToMapwith = new ArrayList<String>();

        for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {

            String key = xls.getCellData(sheetName, 0, rNum);
            String value = xls.getCellData(sheetName, 1, rNum);

            Boolean isITblank = value.isEmpty();

            if (key.equalsIgnoreCase("Y")) {
                //data.put(key, value);
                listOfComponentToMapwith.add(value);
            }

        }

        return listOfComponentToMapwith;
    }


    public static Hashtable<String, String> readSpecificTestDataFromExcel(Xls_Reader xls, String testCaseName, String sheetName, String colName) {

        int testStartRowNum = 1;
        int dataStartRowNum;
        int colStartRowNum;
        System.out.println("Value is - " + xls.getCellData(sheetName, 0, testStartRowNum));
        if (testCaseName != null) {
            while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
                testStartRowNum++;
            }
        }
        System.out.println("Test starts from row - " + testStartRowNum);

        if (testCaseName != null) {
            colStartRowNum = testStartRowNum + 1;
        } else {
            colStartRowNum = 1;
        }

        if (testCaseName != null) {
            dataStartRowNum = testStartRowNum + 2;
        } else {
            dataStartRowNum = testStartRowNum + 1;
        }

        // calculate rows of data
        int rows = 0;
        while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
            rows++;
        }
        System.out.println("Total rows are  - " + rows);

        //calculate total cols
        int cols = 0;
        while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
            cols++;
        }
        System.out.println("Total cols are  - " + cols);

        Hashtable<String, String> data = new Hashtable<String, String>();

        for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {

            for (int cNum = 0; cNum < cols; cNum++) {
                String key = xls.getCellData(sheetName, cNum, colStartRowNum);
                String value = xls.getCellData(sheetName, cNum, rNum);

                Boolean isITblank = value.isEmpty();

                if (value.isEmpty() != true) {
                    if (key.equalsIgnoreCase(colName)) {
                        data.put(key, value);
                    }
                }

            }

        }

        return data;
    }


    public static Hashtable<String, String> getControlDatasourcePlaceholderValueFromXls(Xls_Reader xls, String testCaseName, String sheetName) {

        int testStartRowNum = 1;
        int dataStartRowNum;
        int colStartRowNum;
        System.out.println("Value is - " + xls.getCellData(sheetName, 0, testStartRowNum));
        if (testCaseName != null) {
            while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
                testStartRowNum++;
            }
        }
        System.out.println("Test starts from row - " + testStartRowNum);

        if (testCaseName != null) {
            colStartRowNum = testStartRowNum + 1;
        } else {
            colStartRowNum = 1;
        }

        if (testCaseName != null) {
            dataStartRowNum = testStartRowNum + 2;
        } else {
            dataStartRowNum = testStartRowNum + 1;
        }

        // calculate rows of data
        int rows = 0;
        while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
            rows++;
        }
        System.out.println("Total rows are  - " + rows);

        //calculate total cols
        int cols = 0;
        while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
            cols++;
        }
        System.out.println("Total cols are  - " + cols);

        Hashtable<String, String> data = new Hashtable<String, String>();

        for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {

            for (int cNum = 0; cNum < cols; cNum++) {
                String key = xls.getCellData(sheetName, cNum, colStartRowNum);
                String value = xls.getCellData(sheetName, cNum, rNum);

                Boolean isITblank = value.isEmpty();

                if (value.isEmpty() != true) {
                    if (key.equalsIgnoreCase("Control") || key.equalsIgnoreCase("PlaceHolder") || key.equalsIgnoreCase("DataSource")) {
                        data.put(key, value);
                    }
                }

            }

        }

        return data;
    }


    public static Object[][] getData(Xls_Reader xls, String testCaseName, String sheetName) {
        //String sheetName=FBConstants.TESTDATA_SHEET;
        // reads data for only testCaseName

        int testStartRowNum = 1;
        int dataStartRowNum;
        int colStartRowNum;
        System.out.println("Value is - " + xls.getCellData(sheetName, 0, testStartRowNum));
        if (testCaseName != null) {
            while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
                testStartRowNum++;
            }
        }
        System.out.println("Test starts from row - " + testStartRowNum);

        if (testCaseName != null) {
            colStartRowNum = testStartRowNum + 1;
        } else {
            colStartRowNum = 1;
        }

        if (testCaseName != null) {
            dataStartRowNum = testStartRowNum + 2;
        } else {
            dataStartRowNum = testStartRowNum + 1;
        }

        // calculate rows of data
        int rows = 0;
        while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
            rows++;
        }
        System.out.println("Total rows are  - " + rows);

        //calculate total cols
        int cols = 0;
        while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
            cols++;
        }
        System.out.println("Total cols are  - " + cols);


        //===========================

        Hashtable<String, String> Temptable = new Hashtable<String, String>();

        for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {

            //Temptable = new Hashtable<String,String>();

            for (int cNum = 0; cNum < cols; cNum++) {
                String key = xls.getCellData(sheetName, cNum, colStartRowNum);
                String value = xls.getCellData(sheetName, cNum, rNum);

                Boolean isITblank = value.isEmpty();

                if (value.isEmpty() != true) {
                    if (key.equalsIgnoreCase("Control") || key.equalsIgnoreCase("PlaceHolder") || key.equalsIgnoreCase("DataSource")) {
                        Temptable.put(key, value);
                    }
                }

            }

        }


        //===========================

        Object[][] data = new Object[rows][1];
        //read the data
        int dataRow = 0;
        Hashtable<String, String> table = null;
        for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
            table = new Hashtable<String, String>();
            for (int cNum = 0; cNum < cols; cNum++) {
                String key = xls.getCellData(sheetName, cNum, colStartRowNum);
                String value = xls.getCellData(sheetName, cNum, rNum);
                table.put(key, value);
                // 0,0 0,1 0,2
                //1,0 1,1
            }
            data[dataRow][0] = table;
            dataRow++;
        }
        return data;
    }

    public static boolean isTestExecutable(Xls_Reader xls, String testCaseName) {
        int rows = xls.getRowCount(excelConfig.TESTSUITE_SHEET);
        for (int rNum = 2; rNum <= rows; rNum++) {
            String tcid = xls.getCellData(excelConfig.TESTSUITE_SHEET, "TCID", rNum);
            if (tcid.equals(testCaseName)) {
                String runmode = xls.getCellData(excelConfig.TESTSUITE_SHEET, "Runmode", rNum);
                if (runmode.equals("Y"))
                    return true;
                else
                    return false;

            }
        }
        return false;
    }


    public static List<String> splitStringBasedOnComma(String string) {

        List<String> listOfString = Arrays.asList(string.split(","));
        return listOfString;
    }

    public static List<String> splitStringBasedOnUnderscore(String string) {

        List<String> splitDoubleORstring = Arrays.asList(string.split("\\_"));

        return splitDoubleORstring;
    }


}
