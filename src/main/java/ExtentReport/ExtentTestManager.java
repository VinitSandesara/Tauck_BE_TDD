package ExtentReport;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.*;
import com.aventstack.extentreports.Status;

import org.apache.log4j.Logger;




public class ExtentTestManager {  // new
    public static Properties prop = new Properties();

    private static InheritableThreadLocal<ExtentTest> extentTest = new InheritableThreadLocal<ExtentTest>();
    public static ExtentReports extent = ExtentManager.getExtent();

    public synchronized static ExtentTest getTest() {
        return extentTest.get();
    }

    public synchronized static ExtentTest createTest(String name, String description,
                                                     String deviceId) {
        ExtentTest test = extent.createTest(name, description);

        if (deviceId != null && !deviceId.isEmpty())
            //  test.assignCategory(deviceId);
            extentTest.set(test);
        return getTest();
    }

    public synchronized static ExtentTest createTest(String name, String description) {
        return createTest(name, description, String.valueOf(Thread.currentThread().getId()));
    }

    public synchronized static ExtentTest createTest(String name) {
        return createTest(name, "Sample Test");
    }



}
