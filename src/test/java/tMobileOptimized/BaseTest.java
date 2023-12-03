package tMobileOptimized;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static tMobileOptimized.Constants.*;

public abstract class BaseTest {
    Playwright playwright = Playwright.create();
    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
    Page page = context.newPage();

    public void navigate(String url) {
        page.navigate(url);
    }

    public Locator getLink(String text) {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }
    public void hoverMenu(String menuName) {
        getLink(menuName).hover();
    }

    public void clickMenu(String menuName) {
        getLink(menuName).click();
    }

    public void clickOnElementByLocator(String locator) {
        page.locator(locator).click();
    }

    public Locator getElementByLocator(String locator) {
        return page.locator(locator);
    }

    public Locator getElementByTestId(String locator) {
        return page.getByTestId(locator);
    }

    public int getExpectedNumberOfItems() {
        String textCountItems = getElementByLocator(EXPECTED_COUNT_ITEMS).innerText().trim();

        return parseInt(textCountItems.replaceAll("[^0-9]", ""));
    }

    public void scrollAllProducts(String locator) {
        getElementByTestId(locator).last().hover();
    }

    public List<String> getAllInnerTextOfProductCards () {
        List<String> allProductNames = getElementByLocator(PRODUCT_NAMES).allInnerTexts();
        allProductNames.remove(allProductNames.size() - 1);

        return allProductNames;
    }

    public int getActualNumberOfItems () {
        scrollAllProducts(PRODUCT_CARDS);

        return getAllInnerTextOfProductCards().size();
    }

    public List<Double> createPriceListOfProducts() {
        List<Double> fullPriceList = new ArrayList<>();
        List<String> list = getAllInnerTextOfProductCards();
        for (String str : list) {
            String newS = str.replace("\n", "");
            String fPrice = newS.substring(newS.lastIndexOf("$") + 1).replaceAll("[^0-9.]+", "");
            fullPriceList.add(parseDouble(fPrice));
        }

        return fullPriceList;
    }

    public List<Double> sortPriceListOfProducts(List<Double> list) {
        Collections.sort(list);

        return list;
    }
}
