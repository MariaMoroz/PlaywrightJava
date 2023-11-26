import base.BaseTest;

import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AssignmentTest extends BaseTest {

    private static final String CHECKBOX_NAME = ".filter-display-name";
    private static final String FILTERED_PRODUCTS = "div[data-testid='filter-chip']";

    private static final String[] EXPECTED_LIST_CHECKBOXES_DEALS = {"New", "Special offer"};
    private static final String[] EXPECTED_LIST_CHECKBOXES_BRANDS = {"Alcatel", "Apple", "Samsung", "T-Mobile", "TCL"};
    private static final String[] EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM = {"Android", "iPadOS", "Other"};

    public void selectFilter(String groupName, String... args) {
        Locator groupByName = page.getByTestId("desktop-filter-group-name").filter(new Locator.FilterOptions().setHasText(groupName));

        groupByName.click();

        for (int i = 0; i < args.length; i++) {
            if (args[0].equals("all")) {
                List<Locator> list = page.locator(CHECKBOX_NAME).all();
                for (Locator el : list) {
                    el.check();
                }
            } else {
                page.locator(CHECKBOX_NAME).getByText(args[i]).check();
            }
        }
    }

    @Test
    public void testFilterDealsAll() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Deals", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(EXPECTED_LIST_CHECKBOXES_DEALS);
    }

    @Test
    public void testFilterBrandsAll() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Brands", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(EXPECTED_LIST_CHECKBOXES_BRANDS);
    }

    @Test
    public void testFilterOperatingSystemAll() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Operating System", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM);
    }

    @Test
    public void testFilterDealsNew() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Deals", EXPECTED_LIST_CHECKBOXES_DEALS[0]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(EXPECTED_LIST_CHECKBOXES_DEALS[0]);
    }

    @Test
    public void testFilterDealsSpecialOffer() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Deals", EXPECTED_LIST_CHECKBOXES_DEALS[1]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(EXPECTED_LIST_CHECKBOXES_DEALS[1]);
    }

    @Test
    public void testFilterDealsBothPtoducts() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Deals", EXPECTED_LIST_CHECKBOXES_DEALS[0], EXPECTED_LIST_CHECKBOXES_DEALS[1]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(EXPECTED_LIST_CHECKBOXES_DEALS);
    }

    @Test
    public void testFilterBrandsAlcatel() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Brands", EXPECTED_LIST_CHECKBOXES_BRANDS[0]);

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(EXPECTED_LIST_CHECKBOXES_BRANDS[0]);
    }

    @Test
    public void testFilterBrandsThreeProducts() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Brands", EXPECTED_LIST_CHECKBOXES_BRANDS[0], EXPECTED_LIST_CHECKBOXES_BRANDS[2], EXPECTED_LIST_CHECKBOXES_BRANDS[3]);

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[] {EXPECTED_LIST_CHECKBOXES_BRANDS[0],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[2],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[3]});

    }

    @Test
    public void testFilterOperatingSystemAndroid() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Operating System", EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM[0]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM[0]);
    }

    @Test
    public void testFilterCombineDealsBrands() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Deals", "New");
        selectFilter("Brands", "Alcatel", "Samsung", "T-Mobile");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[] {EXPECTED_LIST_CHECKBOXES_DEALS[0],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[0],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[2],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[3]});
    }

    @Test
    public void testFilterCombineAllGroups() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Deals", "Special offer");
        selectFilter("Brands", "Apple", "Samsung", "TCL");
        selectFilter("Operating System", " iPadOS", "Other");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[] {EXPECTED_LIST_CHECKBOXES_DEALS[1],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[1],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[2],
                                                                               EXPECTED_LIST_CHECKBOXES_BRANDS[4],
                                                                               EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM[1],
                                                                               EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM[2]});
    }

    @Test
    public void testFilterAllProductsAllGroups() {
        page.navigate("https://www.t-mobile.com/tablets");
        selectFilter("Deals", "all");
        selectFilter("Brands", "all");
        selectFilter("Operating System", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[]{EXPECTED_LIST_CHECKBOXES_DEALS[0],
                                                                              EXPECTED_LIST_CHECKBOXES_DEALS[1],
                                                                              EXPECTED_LIST_CHECKBOXES_BRANDS[0],
                                                                              EXPECTED_LIST_CHECKBOXES_BRANDS[1],
                                                                              EXPECTED_LIST_CHECKBOXES_BRANDS[2],
                                                                              EXPECTED_LIST_CHECKBOXES_BRANDS[3],
                                                                              EXPECTED_LIST_CHECKBOXES_BRANDS[4],
                                                                              EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM[0],
                                                                              EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM[1],
                                                                              EXPECTED_LIST_CHECKBOXES_OPERATING_SYSTEM[2]});
    }
}
