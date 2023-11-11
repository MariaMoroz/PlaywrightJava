import base.BaseTest;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Thread.sleep;

public class NaveenAutomationLabsTest extends BaseTest {

//    TC-03
//    Go to https://naveenautomationlabs.com/opencart/
//    In the Search field input “phone”
//    Click Search button
//    Verify that at least one product is shown on the screen as a Search result

    @Test
    public void verifySearchField() throws InterruptedException {
        getPage().navigate("https://naveenautomationlabs.com/opencart/");

        Locator inputSearchField = getPage().locator("css=#search input[name='search']");
        inputSearchField.fill("phone");
        Locator searchBtn = getPage().locator("css=#search button[type='button']");
        searchBtn.click();
        Locator productsRaw = getPage().locator("css=.row .product-layout");
        assertThat(productsRaw).not().isEmpty();
//        assertThat(productsRaw).not().hasCount(0);

        //        getPage().getByPlaceholder("Search").click();
//        Locator productsRawBtns = getPage().getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("Add to Cart"));

//        sleep(4000);
//        assertThat(productsRaw).isVisible();
//        assertThat(productsRaw).containsText("phone");
//        assertThat(productsRaw).not().isEmpty();
//        assertThat(productsRaw).hasCount(1);
//        assertThat(productsRawBtns).hasCount(1);
    }

    //    TC-04
//    Go to https://naveenautomationlabs.com/opencart/
//    Click on Components -> Monitors menu
//    Get the amount of Monitors products available in the store
//    Hover over the Components menu
//    Verify that the drop-down menu for the section Monitors shows the same amount  of Monitors products available in the store
    @Test
    public void componentsTest() {
        getPage().navigate("https://naveenautomationlabs.com/opencart/");

        Locator menuComponentsLink = getPage().locator("xpath=//li[@class='dropdown']/a[text()= 'Components']");
        menuComponentsLink.click();
        Locator subMenuMonitorsLink = getPage().locator("css=div.dropdown-inner> ul a[href*='category&path=25_28']");
        subMenuMonitorsLink.click();

        Locator productsRaw = getPage().locator("css=.row .product-layout");
//        Locator productsRaw = getPage().getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("Add to Cart"));
        int productsCount = productsRaw.count();
        menuComponentsLink.hover();

        assertThat(subMenuMonitorsLink).containsText(String.valueOf("(" + productsCount + ")"));


        //        Locator rows = getPage().getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("Components"));
//        Object texts = rows.evaluateAll(
//                "list => list.map(element => element.textContent)");
//        System.out.println(texts);
    }
}
