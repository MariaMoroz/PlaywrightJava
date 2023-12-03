package tMobileOptimized;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static tMobileOptimized.Constants.*;

public class TMobileTest extends BaseTest{

    @Test
    public  void verifySortByPriceLowToHigh () {
        navigate(TMOBILE_URL);
        hoverMenu(PHONES_AND_DEVICES);
        clickMenu(TABLETS);
        clickOnElementByLocator(SORT_BY_FEATURED);
        clickOnElementByLocator(PRICE_LOW_TO_HIGH);

        assertThat(page).hasTitle(TITLE_PAGE_COMPARE_PRODUCTS);
        assertThat(getElementByLocator(DROPDOWN_MENU_SORT_BY)).hasText(TEXT_PRICE_LOW_TO_HIGH);
        Assert.assertEquals(getActualNumberOfItems(), getExpectedNumberOfItems());

        List<Double> priceListOfItems = createPriceListOfProducts();
        List<Double> actualPriceListOfProducts = priceListOfItems;
        List<Double> expectedPriceListOfProducts = sortPriceListOfProducts(priceListOfItems);

        Assert.assertEquals(actualPriceListOfProducts, expectedPriceListOfProducts);
    }
}
