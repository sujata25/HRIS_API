package com.qait.automation.getpageobjects;

import com.qait.automation.getpageobjects.Action;
import com.qait.automation.getpageobjects.Verification;
import org.testng.Reporter;


public class LogMessages {

    public static void main(String[] args) {
        LogMessages log = new LogMessages();
        log.sendActionMessage(Action.CHECK_CHECKBOX, "locator", "rep1", "rep2");
        log.sendVerificationPassedMessage(Verification.EXACT_TEXT, "test",
                "locator", "rep1", "rep2");

    }

    private static void logMessage(String msgType, String message) {
        Reporter.log("[" + msgType.toUpperCase() + "]: " + message, true);
    }

    public void sendActionMessage(Action action) {
        String message = "";
        switch (action) {
            case RELOAD_PAGE:
                message = "Page Reloaded";
                break;
            case SWITCH_DEFAULT:
                message = "Switched to Default Content/Default Window";
                break;
            case SWITCH_WINDOW:
                message = "Switched to secondary window";
                break;
            default:
                message = "Incorrect action specified";
        }
    }

    public void sendActionMessage(Action action, String... locator) {
        String message = "";
        String locatorDesc = getLocatorDescription(locator);
        switch(action) {
            case CLICK:
                message = "Clicked Element with " + locatorDesc;
                break;
            case SELECT_RADIO:
                message = "Selected Radio Button with " + locatorDesc;
                break;
            case CHECK_CHECKBOX:
                message = "Checked Checkbox with " + locatorDesc;
                break;
            case UNCHECK_CHECKBOX:
                message = "Unchecked Checkbox with " + locatorDesc;
                break;
            case JAVASCRIPT_CLICK:
                message = "Clicked Element with " + locatorDesc + " using Javascript";
                break;
            case SWITCH_FRAME:
                message = "Switched to Frame with " + locatorDesc;
                break;
            default:
                message = "Incorrect Action specified";
        }
        logMessage("Action", message);
    }

    public void sendActionMessage(String data, Action action, String... locator) {
        String message = "";
        String locatorDesc = getLocatorDescription(locator);
        switch(action) {
            case FILL_INPUT:
                message = "Filled Element having " + locatorDesc + " with Text '" + data + "'";
                break;
            case SELECT_DROPDOWN_OPTION:
                message = "Selected Option '" + data + "' from Dropdown having " + locatorDesc;
                break;
            default:
                message = "Incorrect Action specified";
        }
        logMessage("Action", message);
    }

    public void sendVerificationPassedMessage(Verification verification,
                                                      String expectedData, String... locator) {
        String message = "";
        String locatorDesc = getLocatorDescription(locator);
        switch(verification) {
            case DISPLAYED:
                message = "Element with " + locatorDesc + " is Displayed";
                break;
            case EXACT_TEXT:
                message = "Text of Element with " + locatorDesc + " is '" + expectedData + "'";
                break;

            case PARTIAL_TEXT:
                message = "Element with " + locatorDesc + " contains Text '" + expectedData + "'";
                break;
            case NOT_DISPLAYED:
                message = "Element with " + locatorDesc + " is not Displayed";
                break;
            default:
                message = "Incorrect Verification specified";
        }
        logMessage("Verified", message);
    }

    public String getVerificationFailedMessage(Verification verification, String expectedData,
                                                      String... locator) {
        String message = "";
        String locatorDesc = getLocatorDescription(locator);
        if(verification.equals(Verification.DISPLAYED)) {
            message = "Element with " + locatorDesc + " is not Displayed";
        }else if(verification.equals(Verification.EXACT_TEXT)) {
            message = "Text of Element with " + locatorDesc + " is not '" + expectedData + "'";
        }else if(verification.equals(Verification.PARTIAL_TEXT)) {
            message = "Element with " + locatorDesc + " does not contain Text '" + expectedData + "'";
        }else if(verification.equals(Verification.NOT_DISPLAYED)) {
            message = "Element with " + locatorDesc + " is Displayed";
        }
        return message;
    }

    private static String getLocatorDescription(String[] locator) {
        String locatorDesc = "Locator '" + locator[0] + "'";
        if(locator.length == 2) locatorDesc += " and replacement '" + locator[1] + "'";
        if(locator.length == 3) locatorDesc += ", replacement1 '" +
                locator[1] + "' and replacement2 '" + locator[2] + "'";
        return locatorDesc;
    }


}
