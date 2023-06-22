package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Geolocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.CreateAccountPage;
import pages.HomePage;

import java.awt.*;
import java.util.List;

public class PlaywrightRunner {
    protected Page page;
    protected BrowserContext browserContext;
    protected Browser browser;
    protected static Playwright playwright;
    protected HomePage homePage;
    protected CreateAccountPage createAccountPage;


    @BeforeAll
    public static void init() {
        playwright = Playwright.create();
    }

    @BeforeEach
    public void setUp() {
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(width, height)
                .setGeolocation(new Geolocation(-33.8571197, 151.2138464))
                .setPermissions(List.of("geolocation")));
        page = browserContext.newPage();
        homePage = new HomePage(page);
        createAccountPage = new CreateAccountPage(page);
    }

    @AfterEach
    public void tearDown() {
        browserContext.close();
        browser.close();
        homePage = null;
        createAccountPage = null;
    }
}

