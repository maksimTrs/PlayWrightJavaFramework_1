package pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class SignInPage {

    private static String emailAddressField = "//input[@type='email']";
    private static String passwordField = "//input[@type='password']";
    private static String signInBtn = "//button[@data-track='Sign In']";

    private static String signInVerifyWindow = "//form/../p[1]";

    private static String googleFrameLocator = "//iframe[contains(@title, 'Sign in with Google Dialog')]";
    private static String googleFrameLocatorCloseBtn = "//div[@id='close']";
    private final Page page;

    public SignInPage(Page page) {
        this.page = page;
    }

    public void doSignIn(String email, String pass) {
        FrameLocator middleFrame = page.frameLocator(googleFrameLocator);
        middleFrame.locator(googleFrameLocatorCloseBtn).click();
        page.locator(emailAddressField).fill(email);
        page.locator(passwordField).fill(pass);
        page.locator(signInBtn).dblclick();
        // page.evaluate("document.evaluate(\"" + signInBtn + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");

    }

    public void validateAccountSignInVerifyWindow(String accountName) {
        PlaywrightAssertions.assertThat(page.locator(signInVerifyWindow))
                .containsText(String.format("We've sent a code to %s", accountName), new LocatorAssertions.ContainsTextOptions().setTimeout(7000));
    }
}
