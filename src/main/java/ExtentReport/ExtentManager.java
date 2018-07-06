package ExtentReport;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ExtentManager {
    public static Properties prop = new Properties();

    public static ExtentReports extent;
    public static String filePath = System.getProperty("user.dir") + "/Reports/extent.html";


    public ExtentManager() throws IOException {
        prop.load(new FileInputStream("config.properties"));
    }

    public synchronized static ExtentReports getExtent() {

        if (extent == null) {
            Date date = new Date();

            SimpleDateFormat ft =
                    new SimpleDateFormat ("E MM.dd.yyyy 'at' hh:mm:ss a");


            extent = new ExtentReports();
            extent.attachReporter(getHtmlReporter());
/*			if (System.getenv("ExtentX").equalsIgnoreCase("true")) {
        extent.attachReporter(getExtentXReporter());
			}*/
            extent.setSystemInfo("Date/Time", ft.format(date));
            extent.setSystemInfo("Application", "Sitecore");
            extent.setSystemInfo("Environment", System.getProperty("env"));
            extent.setSystemInfo("User", System.getProperty("username"));

        }

        return extent;
    }

    private static ExtentHtmlReporter getHtmlReporter() {


        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);

        // make the charts visible on report open
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setJS("$('.brand-logo').text('TAUCK')");

        htmlReporter.config().setDocumentTitle("Tauck Automation Report");
        htmlReporter.config().setReportName("Tauck Automation SiteCore Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);


        // htmlReporter.loadXMLConfig("Reports/ReportsConfig.xml");
        return htmlReporter;
    }

    private static ExtentXReporter getExtentXReporter() {
        String host = prop.getProperty("MONGODB_SERVER");
        Integer port = Integer.parseInt(prop.getProperty("MONGODB_PORT"));
        ExtentXReporter extentx = new ExtentXReporter(host, port);

        // project name
        String projectName = prop.getProperty("projectName", "ExtentReports");
        extentx.config().setProjectName(projectName);

        // report or build name
        String reportName = prop.getProperty("reportName", "ExtentReports");
        extentx.config().setReportName(reportName);

        // server URL
        // ! must provide this to be able to upload snapshots

        return extentx;
    }

}
