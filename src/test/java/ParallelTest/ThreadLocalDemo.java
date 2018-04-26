package ParallelTest;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class ThreadLocalDemo {
    @Test
    public void testMethod1() {
        invokeBrowser("http://www.ndtv.com");
    }

    @Test
    public void testMethod2() {
        invokeBrowser("http://www.facebook.com");

    }

    private void invokeBrowser(String url) {


        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println("Hashcode of webDriver instance = " + LocalDriverManager.getDriver().hashCode());
        LocalDriverManager.getDriver().get(url);


    }
}