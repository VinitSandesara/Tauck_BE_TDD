package GoogleDriveConfigration;

import Util.Xls_Reader;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GDriveSpreedSheetUtil {


    public static void main(String arg[]) throws IOException {


        getFEControlDatasourceAndPlaceholderValueFromSpecificSheetToMap("EditorialTitle","Editorial_PDF11");


    }



    static String spreadsheetId = "1oqyfIYiCz1L12AtjpyXlfopaxxrKY2a6pOjoLZhlufA";


    public static ValueRange returnResponse(String range) throws IOException {

        Sheets service = GoogleSheetApi.getSheetsService();

        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response;
    }


    public static Hashtable<String, String> getFEControlDatasourceAndPlaceholderValueFromSpecificSheetToMap(String testCaseName, String sheetName) throws IOException {


        List<List<Object>> response = returnResponse(sheetName + "!A1:R").getValues();


        int testStartRowNum = 0;
        int dataStartRowNum;
        int colStartRowNum;


        for (int i = 0; i < response.size(); i++) {
            // String value = values.get(i).get(0).toString();
            if (response.get(i).size() != 0) {

                if (response.get(i).get(0).equals(testCaseName)) {
                    testStartRowNum = i;
                    System.out.println("Test starts from row - " + testStartRowNum);


                    colStartRowNum = testStartRowNum + 1;
                    System.out.println("Col starts from row - " + colStartRowNum);

                    dataStartRowNum = testStartRowNum + 2;

                    // calculate rows of data
                    int rows = 0;
                    int d = 0;
                    while (response.get(dataStartRowNum + rows).size() != 0) {
                        rows++;
                        d = dataStartRowNum + rows;
                        if (dataStartRowNum + rows == response.size()) {
                            break;
                        }
                        // System.out.println("Your value is :- " +  values.get(dataStartRowNum+rows).get(0).toString());
                    }

                    System.out.println("Total rows are  - " + rows);

                    //calculate total cols
                    String temp = response.get(colStartRowNum + 1).get(0).toString();
                    int cols = response.get(colStartRowNum + 1).size();
                    System.out.println("Total cols are  - " + cols);

                    Hashtable<String, String> data = new Hashtable<String, String>();

                    for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {

                        for (int cNum = 0; cNum < cols; cNum++) {
                            String key = response.get(colStartRowNum).get(cNum).toString();
                            String value = response.get(rNum).get(cNum).toString();

                            Boolean isITblank = value.isEmpty();

                            if (value.isEmpty() != true) {
                                if (key.equalsIgnoreCase("Control") || key.equalsIgnoreCase("PlaceHolder") || key.equalsIgnoreCase("DataSource") || key.equalsIgnoreCase("ControlFolder")) {
                                    data.put(key, value);
                                }
                            }

                        }
                        return data;
                    }

                }
            }
        }
        return null;

    }

    public static List<String> getListOfControlsForMapping(String testCaseName, String sheetName) throws IOException {

        List<List<Object>> response = returnResponse(sheetName + "!A1:R").getValues();


        int testStartRowNum = 0;
        int dataStartRowNum;
        int colStartRowNum;
        Object[][] data = null;

        for (int i = 0; i < response.size(); i++) {
            // String value = values.get(i).get(0).toString();
            if (response.get(i).size() != 0) {

                if (response.get(i).get(0).equals(testCaseName)) {
                    testStartRowNum = i;
                    System.out.println("Test starts from row - " + testStartRowNum);


                    colStartRowNum = testStartRowNum + 1;
                    System.out.println("Col starts from row - " + colStartRowNum);

                    dataStartRowNum = testStartRowNum + 2;

                    // calculate rows of data
                    int rows = 0;
                    int d = 0;
                    while (response.get(dataStartRowNum + rows).size() != 0) {
                        rows++;
                        d = dataStartRowNum + rows;
                        if (dataStartRowNum + rows == response.size()) {
                            break;
                        }
                        // System.out.println("Your value is :- " +  values.get(dataStartRowNum+rows).get(0).toString());
                    }

                    System.out.println("Total rows are  - " + rows);

                    //calculate total cols
                    String temp = response.get(colStartRowNum + 1).get(0).toString();
                    int cols = response.get(colStartRowNum + 1).size();
                    System.out.println("Total cols are  - " + cols);

                    List<String> listOfComponentToMapwith = new ArrayList<String>();

                    for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {

                        String key = response.get(rNum).get(0).toString();
                        String value = response.get(rNum).get(1).toString();

                        Boolean isITblank = value.isEmpty();

                        if (key.equalsIgnoreCase("Y")) {
                            //data.put(key, value);
                            listOfComponentToMapwith.add(value);
                        }

                    }

                    return listOfComponentToMapwith;
                }
            }
        }
        return null;

    }



    public static Object[][] getData(String testCaseName, String sheetName) throws IOException {

        List<List<Object>> response = returnResponse(sheetName + "!A1:Z").getValues();


        int testStartRowNum = 0;
        int dataStartRowNum;
        int colStartRowNum;
        Object[][] data = null;

        for (int i = 0; i < response.size(); i++) {
            // String value = values.get(i).get(0).toString();
            if (response.get(i).size() != 0) {

                if (response.get(i).get(0).equals(testCaseName)) {
                    testStartRowNum = i;
                    System.out.println("Test starts from row - " + testStartRowNum);


                    colStartRowNum = testStartRowNum + 1;
                    System.out.println("Col starts from row - " + colStartRowNum);

                    dataStartRowNum = testStartRowNum + 2;

                    // calculate rows of data
                    int rows = 0;
                    int d = 0;
                    while (response.get(dataStartRowNum + rows).size() != 0) {
                        rows++;
                        d = dataStartRowNum + rows;
                        if (dataStartRowNum + rows == response.size()) {
                            break;
                        }
                        // System.out.println("Your value is :- " +  values.get(dataStartRowNum+rows).get(0).toString());
                    }

                    System.out.println("Total rows are  - " + rows);

                    //calculate total cols
                    String temp = response.get(colStartRowNum + 1).get(0).toString();
                    int cols = response.get(colStartRowNum + 1).size();
                    System.out.println("Total cols are  - " + cols);

                    data = new Object[rows][1];
                    //read the data
                    int dataRow = 0;
                    Hashtable<String, String> table = null;

                    for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
                        table = new Hashtable<String, String>();
                        for (int cNum = 0; cNum < response.get(rNum).size(); cNum++) {
                            String key = response.get(colStartRowNum).get(cNum).toString();
                            String value = response.get(rNum).get(cNum).toString();
                            table.put(key, value);
                            // 0,0 0,1 0,2
                            //1,0 1,1
                        }
                        data[dataRow][0] = table;
                        dataRow++;
                    }

                    return data;

                } // if (values.get(i).get(0).equals("MegaMenu")) is closing here
            }

        }

        return data;

    }


}
