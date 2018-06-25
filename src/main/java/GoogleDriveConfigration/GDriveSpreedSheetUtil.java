package GoogleDriveConfigration;

import Util.Xls_Reader;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class GDriveSpreedSheetUtil {


    public static void main(String arg[]) throws IOException {


        getData("WhyTauck","MegaMenu");


    }



    static String spreadsheetId = "1oqyfIYiCz1L12AtjpyXlfopaxxrKY2a6pOjoLZhlufA";


    public static ValueRange returnResponse(String range) throws IOException {

        Sheets service = GoogleSheetApi.getSheetsService();

        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response;
    }


    public static Object[][] getData(String testCaseName, String sheetName) throws IOException {

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
