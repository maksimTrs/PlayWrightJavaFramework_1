package pages;

import com.microsoft.playwright.Page;

public class AccountNavigationPage {

    private final Page page;

    private static String accountBtn = "button[data-lid='hdr_signin']";
    private static String accountWindowMenu = "//div[@class='account-menu']//a[text()='%s']";

    public AccountNavigationPage(Page page) {
        this.page = page;
    }

    public void accountNavigateTo(AccountPartitions accountPartition) {
        page.locator(String.format(accountWindowMenu, accountPartition.getDisplayName())).click();
    }

}
