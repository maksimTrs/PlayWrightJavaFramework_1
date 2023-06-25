package tests;

import annotations.PlayWrightPage;
import com.microsoft.playwright.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.AccountNavigationPage;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.SignInPage;
import services.TestsWatcher;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@ExtendWith(TestsWatcher.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaywrightRunner {
    protected Playwright playwright;
    protected Page page;
    protected BrowserContext browserContext;
    protected Browser browser;
    @PlayWrightPage
    protected HomePage homePage;
    @PlayWrightPage
    protected CreateAccountPage createAccountPage;
    @PlayWrightPage
    protected AccountNavigationPage accountNavigationPage;
    @PlayWrightPage
    protected SignInPage signInPage;


    @BeforeAll
    public static void init() {
        //playwright = Playwright.create();
        try {
            FileUtils.deleteDirectory(new File("videos"));
            FileUtils.deleteDirectory(new File("traces"));
            System.out.println("Folders [videos, traces] were deleted successfully.");
        } catch (IOException e) {
            System.err.println("Failed to delete folder: " + e.getMessage());
        }

        Path path = FileSystems.getDefault().getPath("//target/allure-results");
        try {
            Files.deleteIfExists(path);
        } catch (IOException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        }
    }

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        // browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(width, height)
                //  .setGeolocation(new Geolocation(-33.8571197, 151.2138464))
                .setPermissions(List.of("geolocation"))
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720));

        browserContext.setDefaultTimeout(40000);

        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));

        page = browserContext.newPage();
        //  page.setDefaultTimeout(40000);

        initPages(this, page);

/*      homePage = new HomePage(page);
        createAccountPage = new CreateAccountPage(page);*/
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("traces/" + testInfo.getDisplayName().replace("()", "") + ".zip")));

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

