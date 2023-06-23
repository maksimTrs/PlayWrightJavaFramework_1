package pages;

import com.microsoft.playwright.Page;

import static services.PropertyReader.getTestDataFromBundle;


public class HomePage {

    //public static final String HOMEPAGE_URL = "https://www.bestbuy.com/";
    public static final String HOMEPAGE_URL = getTestDataFromBundle("BASE_URL");
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
