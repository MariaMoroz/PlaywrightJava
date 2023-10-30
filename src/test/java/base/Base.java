package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public abstract class Base {

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
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}
