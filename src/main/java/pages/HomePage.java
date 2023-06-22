package pages;

import com.microsoft.playwright.Page;

public class HomePage {

    public static final String HOMEPAGE_URL = "https://www.bestbuy.com/";
    private static String accountBtn = "button[data-lid='hdr_signin']";

    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public HomePage navigate() {
        page.navigate(HOMEPAGE_URL);
        return this;
    }

    public void openAccountPage() {
        page.locator(accountBtn).click();
    }
}
