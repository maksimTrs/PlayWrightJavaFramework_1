package pages;

import com.microsoft.playwright.Page;

public class HomePage {

    private final Page page;

    public static final String HOMEPAGE_URL = "https://www.bestbuy.com/";
    private static String accountBtn = "button[data-lid='hdr_signin']";
    private static String createAccountBtn = "div.header-guest-user a.create-account-btn";

    public HomePage(Page page) {
        this.page = page;
    }

    public HomePage navigate() {
        page.navigate(HOMEPAGE_URL);
        return this;
    }

    public AccountNavigationPage openAccountPage() {
        page.locator(accountBtn).click();
        return new AccountNavigationPage(page);
    }
}
