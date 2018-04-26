package Util;

import com.aventstack.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WaitUtil extends excelDataUtil {


    /**
     * Waits and Switches to the Frame
     *
     * @param DRIVER
     * @param timeOutInSeconds
     * @param locator
     * @throws InterruptedException
     */
    private static WaitUtil Wait;


    ExtentTest test;
    String maxTimeOut = "25";

    public WaitUtil(WebDriver driver){
        super(driver);
    }



    Logger LOGGER = Logger.getLogger(WaitUtil.class);


    public void waitAndSwitchToFrame(
            int timeOutInSeconds, By locator) throws InterruptedException {
        waitForPageLoad(timoutPageLoad);
        LOGGER.info("Switching to Frame" + locator.toString());
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));

    }


  /*  public static WaitUtil getWait() {
        if (Wait == null) {
            Wait = new WaitUtil();
        }
        return Wait;

    }*/


    /**
     * Waits and Switched to the Frame found by its Id or Name
     *
     * @param DRIVER
     * @param timeOutInSeconds
     * @param sFrameName
     * @throws InterruptedException
     */
    public int timoutPageLoad;

    public void waitAndSwitchToFrame(
            int timeOutInSeconds, String sFrameName)
            throws InterruptedException {
        WebDriverWait wait = null;
        try {
            waitForPageLoad(timoutPageLoad);
            LOGGER.info("Waiting and Switching to Frame by its Name "
                    + sFrameName);
            wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions
                    .frameToBeAvailableAndSwitchToIt(sFrameName));
            LOGGER.info("Switched to Frame : " + sFrameName);
        } catch (TimeoutException e) {
            driver.navigate().refresh();
            waitForPageLoad(timoutPageLoad);
            wait.until(ExpectedConditions
                    .frameToBeAvailableAndSwitchToIt(sFrameName));
        } catch (Exception e) {
            LOGGER.error("Error Occured while switching to frame " + sFrameName
                    + e.getMessage());
            driver.switchTo().frame(sFrameName);
        }

    }

    /**
     * Waits for an Alert or Pop Up
     *
     * @param maxTimeInSeconds
     * @throws InterruptedException
     */
    public void waitForAlert(int maxTimeInSeconds)
            throws InterruptedException {
        while ((maxTimeInSeconds) != 0) {

            driver.switchTo().alert().accept();
            LOGGER.info("Alert Accepted");
            break;
        }
    }

    public void waitForFrame(int maxTimeInSeconds, String id)
            throws InterruptedException {
        while ((maxTimeInSeconds) != 0) {
            driver.switchTo().frame(id);
            LOGGER.info("Alert Accepted");
            break;
        }
    }

    /**
     * Uses ExpectedCondition to wait for Element to Appear Using wait
     *
     * @param element
     * @param timeOutInSeconds
     * @param pollingInMilliSeconds
     * @return
     * @throws Exception
     */
    public boolean waitForElementToAppear(
            final WebElement element, int timeOutInSeconds,
            int pollingInMilliSeconds) throws Exception {


        try {
            LOGGER.info("Wait for Element to Appear using Wait until element is Displayed");
            return (new WebDriverWait(driver, timeOutInSeconds,
                    pollingInMilliSeconds))
                    .until(new ExpectedCondition<Boolean>() {

                        public Boolean apply(WebDriver driver) {
                            LOGGER.info("Waiting for element to be displayed ");
                            return element.isDisplayed();
                        }
                    });

        } catch (NoSuchElementException e) {
            LOGGER.error("The element to be serach is not present in the page->waitForElementToBeHidden->"
                    + e.getMessage());
            throw new Exception(
                    "The element to be serach is not present in the page->waitForElementToBeHidden->"
                            + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Some exception in waitForElementToBeHidden"
                    + e.getMessage());
            throw new Exception("Some exception in waitForElementToBeHidden"
                    + e.getMessage());
        }

    }

    public boolean waitForElementToBeDisplay(WebElement element,
                                             int maxSecondTimeout, boolean... isFailOnExcaption)
            throws Exception {
        try {
            LOGGER.info("INTO METHOD waitForElementToBeDisplay");
            maxSecondTimeout = maxSecondTimeout * 20;
            while ((!element.isDisplayed() && (maxSecondTimeout > 0))) {
                // LOGGER.info("Loading...CountDown="+maxSecondTimeout);
                Thread.sleep(50l);
                maxSecondTimeout--;
            }
            if ((maxSecondTimeout == 0) && (isFailOnExcaption.length != 0)) {
                if (isFailOnExcaption[0] == true) {
                    LOGGER.error("Element is not display within "
                            + (maxSecondTimeout / 20) + "Sec.");
                    throw new Exception("Element is not display within "
                            + (maxSecondTimeout / 20) + "Sec.");
                }
            }
            LOGGER.info("OUT OF METHOD waitForElementToBeDisplay");
            return true;
        } catch (UnhandledAlertException e) {
            LOGGER.error("Unexpected alert is coming->waitForElementToBeDisplay->"
                    + e.getMessage());
            throw new UnhandledAlertException(
                    "Unexpected alert is coming->waitForElementToBeDisplay->"
                            + e.getMessage());
        } catch (NoSuchElementException e) {
            LOGGER.error("The element to be search is not present in the page->waitForElementToBeDisplay->"
                    + e.getMessage());
            throw new NoSuchElementException(
                    "The element to be search is not present in the page->waitForElementToBeDisplay->"
                            + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Some exception in waitForElementToBeDisplay"
                    + e.getMessage());
            throw new Exception("Some exception in waitForElementToBeDisplay"
                    + e.getMessage());
        }
    }

    /**
     * Wait for html element to be hidden
     *
     * @param element
     * @param maxSecondTimeout
     * @param isFailOnExcaption ( optional parameter true if fail on exception)
     * @throws Exception
     * @throws IOException
     */
    public boolean waitForElementToBeHidden(WebElement element,
                                            int maxSecondTimeout, boolean... isFailOnExcaption)
            throws Exception {
        try {
            LOGGER.info("INTO waitForElementToBeHidden METHOD");
            maxSecondTimeout = maxSecondTimeout * 20;
            while (element.isDisplayed() && (maxSecondTimeout > 0)) {
                // LOGGER.info("Loading...CountDown="+maxSecondTimeout);
                Thread.sleep(50l);
                maxSecondTimeout--;
            }
            if ((maxSecondTimeout == 0) && (isFailOnExcaption.length != 0)) {
                if (isFailOnExcaption[0] == true) {
                    LOGGER.error("Element is not hidden within "
                            + (maxSecondTimeout / 20) + "Sec.");
                    throw new Exception("Element is not hidden within "
                            + (maxSecondTimeout / 20) + "Sec.");
                }
            }
            LOGGER.info("OUT OF METHOD waitForElementToBeHidden");
            return true;
        } catch (NoSuchElementException e) {
            throw new Exception(
                    "The element to be serach is not present in the page->waitForElementToBeHidden->"
                            + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Some exception in waitForElementToBeHidden"
                    + e.getMessage());
        }
    }

    public void waitForElementToBeVisible(By locator) {
        try {
            LOGGER.info("Waiting for an element to be visible using By locator "
                    + locator);
            WebDriverWait wait = new WebDriverWait(driver,

                    Integer.parseInt(maxTimeOut));      //Need to Give Global Timeout Value

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            LOGGER.info("Exception while waiting for visibility"
                    + e.getMessage());
        }
    }

    /**
     * waits for specified duration and checks that an element is present on
     * DOM. Visibility means that the element is not only displayed but also has
     * a height and width that is greater than 0.
     */
    public void waitForElementToBeVisible(
            WebElement element) {
        LOGGER.info("Waiting for an element to be visible " + element);
        WebDriverWait wait = new WebDriverWait(driver,

                Integer.parseInt(maxTimeOut)); //Need to Give Global Timeout Value

        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait For Element to Disable
     *
     * @param element
     * @param maxSecondTimeout
     * @return
     * @throws Exception
     */
    public boolean waitForElementToDisable(WebElement element,
                                           int maxSecondTimeout) throws Exception {
        try {
            LOGGER.info("INTO waitForElementToDisable METHOD");
            maxSecondTimeout = maxSecondTimeout * 20;
            while (element.isEnabled() && (maxSecondTimeout > 0)) {
                // LOGGER.info("Loading...CountDown="+maxSecondTimeout);
                Thread.sleep(50l);
                maxSecondTimeout--;
            }
            if ((maxSecondTimeout == 0)) {

                LOGGER.error("Element is not disabled within "
                        + (maxSecondTimeout / 20) + "Sec.");
                throw new Exception("Element is not disabled within "
                        + (maxSecondTimeout / 20) + "Sec.");
            }
            LOGGER.info("OUT OF METHOD waitForElementToDisable");
            return true;
        } catch (NoSuchElementException e) {
            throw new Exception(
                    "The element to be disabled is not present in the page->waitForElementToDisable->"
                            + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Some exception in waitForElementToDisable"
                    + e.getMessage());
        }
    }

    /**
     * Waits for an Element to DisAppear using Wait
     *
     * @param element
     * @param timeOutInSeconds
     * @param pollingInMilliSeconds
     * @return
     * @throws Exception
     */
    public boolean waitForElementToDisAppear(
            final WebElement element, int timeOutInSeconds,
            int pollingInMilliSeconds) throws Exception {
        try {
            LOGGER.info("Waiting for element to disappear using wait untile element is not Displayed");
            return (new WebDriverWait(driver, timeOutInSeconds,
                    pollingInMilliSeconds))
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {

                            LOGGER.info("Waiting dfor element to be disappear ");
                            return !element.isDisplayed();
                        }
                    });

        } catch (NoSuchElementException e) {
            LOGGER.error("The element to be serach is not present in the page->waitForElementToBeHidden->"
                    + e.getMessage());
            throw new Exception(
                    "The element to be serach is not present in the page->waitForElementToBeHidden->"
                            + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Some exception in waitForElementToBeHidden"
                    + e.getMessage());
            throw new Exception("Some exception in waitForElementToBeHidden"
                    + e.getMessage());
        }

    }

    /**
     * Wait For Element to Enable
     *
     * @param element
     * @param maxSecondTimeout
     * @return
     * @throws Exception
     */
    public boolean waitForElementToEnable(WebElement element,
                                          int maxSecondTimeout) throws Exception {
        try {
            LOGGER.info("INTO waitForElementToEnable METHOD");
            maxSecondTimeout = maxSecondTimeout * 20;
            while (!element.isEnabled() && (maxSecondTimeout > 0)) {
                // LOGGER.info("Loading...CountDown="+maxSecondTimeout);
                Thread.sleep(50l);
                maxSecondTimeout--;
            }
            if ((maxSecondTimeout == 0)) {

                LOGGER.error("Element is not enabled within "
                        + (maxSecondTimeout / 20) + "Sec.");
                throw new Exception("Element is not enabled within "
                        + (maxSecondTimeout / 20) + "Sec.");
            }
            LOGGER.info("OUT OF METHOD waitForElementToEnable");
            return true;
        } catch (NoSuchElementException e) {
            throw new Exception(
                    "The element to be enabled is not present in the page->waitForElementToEnable->"
                            + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Some exception in waitForElementToEnable"
                    + e.getMessage());
        }
    }

    /**
     * Waits for an Element to get Stale or deleted from DOM
     *
     * @param iTimeOutInSeconds
     * @param by
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    public boolean waitForElementToStale(
            int iTimeOutInSeconds, By by) throws NumberFormatException,
            IOException {
        boolean isStale = true;
        int iAttempt = 0;
        try {
            iTimeOutInSeconds = iTimeOutInSeconds * 20;
            while (isStale && iTimeOutInSeconds > 0) {
                iAttempt++;
                LOGGER.info("Waiting for Element to Statle Attempt Number :"
                        + iAttempt);
                driver.manage().timeouts()
                        .implicitlyWait(100, TimeUnit.MILLISECONDS);
                LOGGER.info("Element :" + driver.findElement(by).isDisplayed());
                if (driver.findElements(by).size() == 0) {
                    isStale = false;
                    break;
                }
                Thread.sleep(30l);
                iTimeOutInSeconds--;
            }
        } catch (NoSuchElementException e) {
            LOGGER.error("No Element Found.This Means Loader is no more in HTML. Moving out of waitForElementToStale!!!");
            isStale = false;
        } catch (StaleElementReferenceException s) {
            LOGGER.error("Given Element is stale from DOM Moving out of waitForElementToStale!!!");
            isStale = false;
        } catch (Exception e) {
            LOGGER.error("Some Exception ocurred Please check code!!!");
        } finally {
            driver.manage()
                    .timeouts()
                    .implicitlyWait(
                            Integer.parseInt(("browser.implicitwait")),
                            //Need to Give Global implicit Timeout Value
                            TimeUnit.SECONDS);
        }
        return isStale;
    }

    /**
     * Waits for Page Load via Java Script Ready State
     *
     * @param iTimeOut
     * @throws InterruptedException
     */
    public boolean waitForPageLoad(int iTimeOut)
            throws InterruptedException {
        boolean isLoaded = false;

        Thread.sleep(2000);
        try {
            LOGGER.info("Waiting For Page load via JS");
            ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript(
                            "return document.readyState").equals("complete");
                }
            };
            WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
            wait.until(pageLoadCondition);
            isLoaded = true;
        } catch (Exception e) {
            LOGGER.error("Error Occured waiting for Page Load "
                    + driver.getCurrentUrl());
        }
        return isLoaded;
    }

    /**
     * Waits for Page Load via Java Script Ready State
     *
     * @throws InterruptedException
     */
    public boolean waitForPageLoad(WebDriver driver)
            throws InterruptedException {
        boolean isLoaded = false;
        int iTimeOut = Integer.parseInt(maxTimeOut); //Need to Give Global Timeout Value;
        Thread.sleep(2000);
        try {
            LOGGER.info("Waiting For Page load via JS");
            ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript(
                            "return document.readyState").equals("complete");
                }
            };
            WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
            wait.until(pageLoadCondition);
            isLoaded = true;
        } catch (Exception e) {
            LOGGER.error("Error Occured waiting for Page Load "
                    + driver.getCurrentUrl());
        }
        return isLoaded;
    }

    /**
     * An expectation for checking if the given text is present in the specified
     * element
     *
     * @param element
     * @param sText
     */
    public void waitFortextToBePresentInElement(
            final WebElement element, final String sText) {
        WebDriverWait wait = new WebDriverWait(driver,
                Integer.parseInt(maxTimeOut)); //Need to Give Global Timeout Value
        wait.until(ExpectedConditions.textToBePresentInElement(element, sText));
    }

    /**
     * An expectation for checking if the given text is present in the element
     * that matches the given locator.
     *
     * @param locator
     * @param sText
     */
    public void waitFortextToBePresentInElementLocated(
            By locator, final String sText) {
        WebDriverWait wait = new WebDriverWait(driver,
                Integer.parseInt(maxTimeOut)); //Need to Give Global Timeout Value
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator,
                sText));
    }

    /**
     * wait till test present on element
     *
     * @param element
     * @param maxSecondTimeout
     * @param isFailOnExcaption ( optional parameter true if fail on exception)
     * @throws Exception
     */
    public boolean waitForTextToBePresentOnElement(WebElement element,
                                                   int maxSecondTimeout, String matchText,
                                                   boolean... isFailOnExcaption) throws Exception {
        try {
            LOGGER.info("INTO METHOD waitForTextToBePresentOnElement");
            maxSecondTimeout = maxSecondTimeout * 20;
            while ((!element.isDisplayed() && (maxSecondTimeout > 0) && (element
                    .getText().toLowerCase().equalsIgnoreCase(matchText
                            .toLowerCase().trim())))) {
                LOGGER.info("Loading...CountDown=" + maxSecondTimeout);
                Thread.sleep(50l);
                maxSecondTimeout--;
            }
            if ((maxSecondTimeout == 0) && (isFailOnExcaption.length != 0)) {
                if (isFailOnExcaption[0] == true) {
                    LOGGER.error("Element is not display within "
                            + (maxSecondTimeout / 20) + "Sec.");
                    throw new Exception("Element is not display within "
                            + (maxSecondTimeout / 20) + "Sec.");
                }
            }
            LOGGER.info("OUT OF METHOD waitForTextToBePresentOnElement");
            return true;
        } catch (UnhandledAlertException e) {
            throw new UnhandledAlertException(
                    "Unexpected alert is coming->waitForTextToBePresentOnElement->"
                            + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(
                    "The element to be search is not present in the page->waitForTextToBePresentOnElement->"
                            + e.getMessage());
        } catch (Exception e) {
            throw new Exception(
                    "Some exception in waitForTextToBePresentOnElement"
                            + e.getMessage());
        }
    }

    /**
     * Waits Until the Attribute of Element got Changed.
     *
     * @param webElement
     * @param attribute
     * @param value
     * @param maxSecondTimeout
     */
    public void waitTillElementAttributeChange(WebElement webElement,
                                               String attribute, String value, int maxSecondTimeout) {
        // boolean flag=false;
        try {
            LOGGER.info("INTO METHOD waitTillElementAttributeChange");
            maxSecondTimeout = maxSecondTimeout * 20;
            while (webElement.getAttribute(attribute) != null) {
                if ((!webElement.getAttribute(attribute.trim()).toLowerCase()
                        .contains(value.trim().toLowerCase()))
                        && (maxSecondTimeout > 0)) {
                    LOGGER.info("Loading...CountDown=" + maxSecondTimeout);
                    try {
                        Thread.sleep(50l);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    maxSecondTimeout--;

                }

            }
            LOGGER.info("OUT OF METHOD waitTillElementAttributeChange");
        } catch (Exception e) {
            LOGGER.error("SOME ERROR CAME IN METHOD->waitTillElementAttributeChange->"
                    + e.getMessage());

        }
    }


    public WebElement customWaitToStale(int timeOut, By byObj) throws InterruptedException {
        WebElement we = null;
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        int waitCounter = 0;

        while (waitCounter <= timeOut) {
            Thread.sleep(1000);
            try {
                we = driver.findElement(byObj);
                break;
            } catch (NoSuchElementException e) {

            }
            waitCounter = waitCounter + 1;
        }

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        return we;
    }







}
