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

    public void isLoadCorrectNumberOfProducts () throws InterruptedException {
        Locator productPrices = page.locator("css=span[data-test='current-price'] span:first-child");
        Locator nextPageBtn = page.locator("css=div[data-test='pagination'] button[aria-label='next page']");
        Locator pagination = page.locator("css=#select-custom-button-id span:first-child");

        int count = 24;
        if (nextPageBtn.isDisabled()) {
            count = countProductsOnLastPage();
        }
        System.out.println(pagination.innerText());
        System.out.println(productPrices.count());

        assertThat(productPrices).hasCount(count);

    }

    public void scrollToBottom() throws InterruptedException {
        Locator products = page.locator("css=div.styles__StyledCol-sc-fw90uk-0 div[data-test='@web/site-top-of-funnel/ProductCardWrapper']");

        int countProducts = products.count();

        for(int i = 0; i < countProducts; i+=8) {
            page.mouse().wheel(0, 1000);
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    public int countProductsOnLastPage () throws InterruptedException {
        Locator searchResult = page.locator("css=div[data-test='resultsHeading'] h2 span");
        int result = Integer.parseInt(searchResult.innerText().split(" ")[0]);
        int countPages = findLastPage();

        return result - (countPages - 1)* 24;
    }

    public void isAllPricesCorrect() {
        Locator productPrices = page.locator("css=span[data-test='current-price'] span:first-child");
        List<String> pricesList = productPrices.allInnerTexts();

        for (String s : pricesList) {
            double price = Double.parseDouble(s.replace("$", "").split("-")[0]);
            Assert.assertTrue((price > 0 && price <= 5));
        }
    }

    public void selectLastPage() {
        Locator dropdownArrow = page.locator("css=#select-custom-button-id span[class*='btn-arrow'] svg");
        Locator menuPageNumsList = page.locator("css=div[role='tooltip'] ul li a div.nds_sc__opt-lbl");

        dropdownArrow.click();
        List<String> allPagesText = menuPageNumsList.allInnerTexts();
        String pageLastText = allPagesText.get(allPagesText.size() - 1);
        page.getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHasText(pageLastText)).click();
    }

    public void clickNextPageBtn() {
        Locator nextPageBtn = page.locator("css=div[data-test='pagination'] button[aria-label='next page']");
        nextPageBtn.click();
    }

    public int findLastPage() throws InterruptedException {
        scrollToBottom();
        Locator dropdownArrow = page.locator("css=#select-custom-button-id span[class*='btn-arrow'] svg");
        dropdownArrow.click();
        Locator menuPageNumsList = page.locator("css=div[role='tooltip'] ul li a div.nds_sc__opt-lbl");
        return menuPageNumsList.count();
    }

    @Test
    public void verifyAllPricesCorrectTest() throws InterruptedException {
        Locator pagination = page.locator("css=#select-custom-button-id span:first-child");

        page.navigate("https://www.target.com/");

        page.getByLabel("Deals").click();
        page.getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHasText("Clearance")).click();
        page.getByLabel("Filters").click();
        page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("Price")).click();
        page.locator("[name='minPriceValue']").fill("0");
        page.locator("[data-test='priceFacetMax']").fill("5");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("See results")).click();

        int lastPage = findLastPage();
        List<String> numberPageData = List.of("1", "2", String.valueOf(lastPage));

        for (String page: numberPageData) {
            scrollToBottom();
            isLoadCorrectNumberOfProducts();
            assertThat(pagination).containsText(String.format("page %s of", page));
            isAllPricesCorrect();

            if (page.equals("2")) {
                selectLastPage();
            } else if(!page.equals(String.valueOf(lastPage))) {
                clickNextPageBtn();
            }
        }
    }
}
