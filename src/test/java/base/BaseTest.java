package base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


import java.nio.file.Paths;
public abstract class BaseTest {
    private Playwright playwright;
    private Browser browser;

    private BrowserContext context;
    private Page page;

    protected Page getPage() {
        return page;
    }

    @BeforeClass
    protected void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    protected void createContextAndPage() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
               .setScreenshots(true)
               .setSnapshots(true)
               .setSources(true));
        page = context.newPage();
    }

    @AfterMethod
    void closeContext() {
        context.tracing()
               .stop(new Tracing.StopOptions()
               .setPath(Paths.get("trace.zip")));
        context.close();
    }

    @AfterClass
    protected void closeBrowser() {
        playwright.close();
    }
}
