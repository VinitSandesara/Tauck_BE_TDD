package rough;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class kickoffuser {

    public static void main(String arg[]) throws IOException, InterruptedException {

      String value =

              "\"Our Mission|Our Mission subtitle|WHO WE ARE|We are a leader in the creation of enriching travel experiences that enhance people’s lives by broadening their knowledge and fulfilling their dreams. We are a family company built on a timeless philosophy of integrity, innovation and respect. By exceeding customer expectations, we continually build a loyal and dedicated following._Tauck Address|Tauck Address subtitle|CORPORATE INFORMATION|\n" +
                      "Wilton Woods\n" +
                      "10 Westport Road\n" +
                      "Wilton, CT\n" +
                      "06897\n" +
                      "\n" +
                      "Main Office:\n" +
                      "203-899-6500\n" +
                      "\n" +
                      "Reservations:\n" +
                      "1-800-788-7885_Tom Armstrong|Tom Armstrong subtitle|PUBLIC RELATIONS|Please contact Tauck's Corporate Communications Manager Tom Armstrong at tarmstrong@tauck.com if you need further information or would like to schedule an interview.\"";



        List<String> splitDoubleORstring = Arrays.asList(value.split("\\_"));


    }



}
