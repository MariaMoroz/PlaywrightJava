import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

//TC-06
//Go to https://www.target.com/
//Click on the Deals menu
//Click on the Clearance submenu
//Click on the Filter icon
//Click on the Price filter
//Set the filter range as “0 to 5”
//Click Apply
//Click See Results
//Verify that all shown products on page #1 have prices, which are  less or equal to $5
//Click on the Page # 2
//Verify that all shown products on page #2 have prices, which are  less or equal to $5
//Click on the last page
//Verify that all shown products on the last page have prices, which are  less or equal to $5

public class TargetTC06Test extends BaseTest {

    public void scrollToBottom() throws InterruptedException {
        Locator products = page.locator("css=div.styles__StyledCol-sc-fw90uk-0 div[data-test='@web/site-top-of-funnel/ProductCardWrapper']");

        int countProducts = products.count();

        for(int i = 0; i < countProducts; i+=8) {
            page.mouse().wheel(0, 1000);
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    public void isAllPricesCorrect() {
        Locator productPrices = page.locator("css=span[data-test='current-price'] span:first-child");
        List<String> pricesList = productPrices.allInnerTexts();

        for (String s : pricesList) {
            double price = Double.parseDouble(s.replace("$", "").split("-")[0]);
            Assert.assertTrue((price > 0 && price <= 5));
        }
    }

    public void selectPageByNumber(int num) {
        Locator dropdownArrow = page.locator("css=#select-custom-button-id span[class*='btn-arrow'] svg");

        dropdownArrow.click();
        page.getByLabel(String.format("page %d", num), new Page.GetByLabelOptions().setExact(true)).click();
    }

    public void verifyOnlyOnePage() throws InterruptedException {
        Locator products = page.locator("css=div.styles__StyledCol-sc-fw90uk-0 div[data-test='@web/site-top-of-funnel/ProductCardWrapper']");
        int amountOfProducts = products.count();

        scrollToBottom();
        Assert.assertTrue(amountOfProducts <= 24);
        isAllPricesCorrect();
    }

    public void verifyFullPage() throws InterruptedException {
        Locator products = page.locator("css=div.styles__StyledCol-sc-fw90uk-0 div[data-test='@web/site-top-of-funnel/ProductCardWrapper']");

        scrollToBottom();
        assertThat(products).hasCount(24);
        isAllPricesCorrect();
    }

    public void verifyLastPage() throws InterruptedException {
        scrollToBottom();
        isAllPricesCorrect();
    }

    public int countAllPages () {
        Locator pagination = page.locator("css=#select-custom-button-id span:first-child");
        String[] paginationList = pagination.innerText().split(" ");
        int amountOfPages = Integer.parseInt(paginationList[paginationList.length - 1]);

        return amountOfPages;
    }

    @Test
    public void verifyAllPricesCorrectTest() throws InterruptedException {
        page.navigate("https://www.target.com/");

        page.getByLabel("Deals").click();
        page.getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHasText("Clearance")).click();
        page.getByLabel("Filters").click();
        page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("Price")).click();
        page.locator("[name='minPriceValue']").fill("0");
        page.locator("[data-test='priceFacetMax']").fill("5");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("See results")).click();

        Locator products = page.locator("css=div.styles__StyledCol-sc-fw90uk-0 div[data-test='@web/site-top-of-funnel/ProductCardWrapper']");
        products.last().hover();

        Locator pagination = page.locator("css=#select-custom-button-id span:first-child");
        pagination.hover();

        if (!pagination.isVisible()) {
            verifyOnlyOnePage();
        } else if (pagination.isVisible()) {
            int lastPageNumber = countAllPages();
                if (lastPageNumber == 2) {
                    verifyFullPage();
                    selectPageByNumber(2);
                    verifyLastPage();
                } else {
                    verifyFullPage();
                    selectPageByNumber(2);
                    verifyFullPage();
                    selectPageByNumber(lastPageNumber);
                    verifyLastPage();
                }
        }
    }
}
