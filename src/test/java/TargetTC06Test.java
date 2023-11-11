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
        for (int i = 0; i < 3; i++) {
            getPage().mouse().wheel(0, 1200);
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    public void isAllPricesCorrect() {
        Locator productPrices = getPage().locator("css=span[data-test='current-price'] span:first-child");
        List<String> pricesList = productPrices.allInnerTexts();

        for(int i = 0; i < pricesList.size(); i++){
            double price = Double.parseDouble(pricesList.get(i).replace("$", "").split("-")[0]);
                Assert.assertTrue((price > 0 && price < 5));
        }
    }

    @Test
    public void verifyAllPricesCorrectTest() throws InterruptedException {
        getPage().navigate("https://www.target.com/");

        getPage().getByLabel("Deals").click();
        getPage().getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHasText("Clearance")).click();
        getPage().getByLabel("Filters").click();
        getPage().getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("Price")).click();
        getPage().locator("[name='minPriceValue']").fill("0");
        getPage().locator("[data-test='priceFacetMax']").fill("5");
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply")).click();
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("See results")).click();
        Locator pagination = getPage().locator("css=#select-custom-button-id span:first-child");
        Locator nextPageBtn = getPage().locator("css=div[data-test='pagination'] button[aria-label='next page']");
        Locator dropdownArrow = getPage().locator("css=#select-custom-button-id span[class*='btn-arrow'] svg");

        scrollToBottom();

        assertThat(pagination).containsText("page 1 of");
        isAllPricesCorrect();

        dropdownArrow.click();
        Locator pageTwoMenuLink = getPage().locator("css=div[role='tooltip'] ul li a[aria-label='page 2']");
        pageTwoMenuLink.click();
        scrollToBottom();

        assertThat(pagination).containsText("page 2 of");
        isAllPricesCorrect();
        assertThat(nextPageBtn).not().isDisabled();

        dropdownArrow.click();
        Locator tooltipAllPagesList = getPage().locator("css=div[role='tooltip'] ul li a div.nds_sc__opt-lbl");
        List<String> allPagesText = tooltipAllPagesList.allInnerTexts();
        String pageLastText = allPagesText.get(allPagesText.size() - 1);

        getPage().getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHasText(pageLastText)).click();
        scrollToBottom();

        assertThat(nextPageBtn).isDisabled();
        isAllPricesCorrect();
    }
}
