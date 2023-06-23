package tests;

import annotations.PlayWrightPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.AccountNavigationPage;
import pages.CreateAccountPage;
import pages.HomePage;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PlaywrightRunner {
    protected static Playwright playwright;
    protected Page page;
    protected BrowserContext browserContext;
    protected Browser browser;
    @PlayWrightPage
    protected HomePage homePage;
    @PlayWrightPage
    protected CreateAccountPage createAccountPage;
    @PlayWrightPage
    protected AccountNavigationPage accountNavigationPage;


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
                //  .setGeolocation(new Geolocation(-33.8571197, 151.2138464))
                .setPermissions(List.of("geolocation")));
        page = browserContext.newPage();

        initPages(this, page);

/*      homePage = new HomePage(page);
        createAccountPage = new CreateAccountPage(page);*/
    }

    @AfterEach
    public void tearDown() {
        browserContext.close();
        browser.close();
        homePage = null;
        createAccountPage = null;
    }

    private void initPages(Object obj, Page page) {
        Class<?> superclass = obj.getClass().getSuperclass();
        for (Field field : superclass.getDeclaredFields()) {
            if (field.isAnnotationPresent(PlayWrightPage.class)) {
                Class<?>[] pageClass = {Page.class};
                try {
                    field.set(this, field.getType().getConstructor(pageClass).newInstance(page));
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException("!!! +++ Constructor for PO objects wasn't created for field: " + field.getName() + " +++ !!!\n" + e);
                }
            }
        }
    }
}

