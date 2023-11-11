package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public abstract class BaseTest {

    // Shared between all tests in this class.
    private static Playwright playwright;
    private static Browser browser;

    // New instance for each test method.
    private BrowserContext context;
    private Page page;

    protected Playwright getPlaywright() {
        return playwright;
    }

    protected Browser getBrowser() {
        return browser;
    }

    protected BrowserContext getContext() {
        return context;
    }

    protected Page getPage() {
        return page;
    }

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType
                .LaunchOptions()
                .setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    protected void createContextAndPage() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
    }

    @AfterEach
    protected void closeContext() {
        context.tracing()
               .stop(new Tracing.StopOptions()
               .setPath(Paths.get("trace.zip")));
        context.close();
    }
}
