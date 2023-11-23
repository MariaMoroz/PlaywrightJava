import base.BaseTest;

import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AssignmentTest extends BaseTest {
    private static final String GROUP_BUTTONS = "#mat-expansion-panel-header-%d legend[data-testid='desktop-filter-group-name']";
    private static final String CHECKBOX_NAME = "//mat-checkbox/label/span/span[@class='filter-display-name'][contains(text(), '%s')]";
    private static final String FILTERED_PRODUCTS = "div[data-testid='filter-chip']";

    private static final String[] LIST_CHECKBOXES_DEALS = {"New", "Special offer"};
    private static final String[] LIST_CHECKBOXES_BRANDS = {"Alcatel", "Apple", "Samsung", "T-Mobile", "TCL"};
    private static final String[] LIST_CHECKBOXES_OPERATING_SYSTEM = {"Android", "iPadOS", "Other"};

    public void clickCheckBoxes(String[] list, String... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[0].equals("all")) {
                for (String itemName : list) {
                    page.locator(String.format(CHECKBOX_NAME, itemName)).click();
                }
            } else {
                page.locator(String.format(CHECKBOX_NAME, args[i])).click();
            }
        }
    }

    public void filterProducts(String groupName, String... args) {

        switch (groupName) {
            case "Deals":
                page.locator(String.format(GROUP_BUTTONS, 2)).click();
                clickCheckBoxes(LIST_CHECKBOXES_DEALS, args);

                break;

            case "Brands":
                page.locator(String.format(GROUP_BUTTONS, 3)).click();
                clickCheckBoxes(LIST_CHECKBOXES_BRANDS, args);

                break;

            case "Operating System":
                page.locator(String.format(GROUP_BUTTONS, 4)).click();
                clickCheckBoxes(LIST_CHECKBOXES_OPERATING_SYSTEM, args);

                break;
        }
    }

    @Test
    public void testFilterDealsAll() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Deals", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(LIST_CHECKBOXES_DEALS);
    }

    @Test
    public void testFilterBrandsAll() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Brands", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(LIST_CHECKBOXES_BRANDS);
    }

    @Test
    public void testFilterOperatingSystemAll() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Operating System", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(LIST_CHECKBOXES_OPERATING_SYSTEM);
    }

    @Test
    public void testFilterDealsNew() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Deals", LIST_CHECKBOXES_DEALS[0]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(LIST_CHECKBOXES_DEALS[0]);
    }

    @Test
    public void testFilterDealsSpecialOffer() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Deals", LIST_CHECKBOXES_DEALS[1]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(LIST_CHECKBOXES_DEALS[1]);
    }

    @Test
    public void testFilterDealsBothPtoducts() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Deals", LIST_CHECKBOXES_DEALS[0], LIST_CHECKBOXES_DEALS[1]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(LIST_CHECKBOXES_DEALS);
    }

    @Test
    public void testFilterBrandsAlcatel() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Brands", LIST_CHECKBOXES_BRANDS[0]);

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(LIST_CHECKBOXES_BRANDS[0]);
    }

    @Test
    public void testFilterBrandsThreeProducts() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Brands", LIST_CHECKBOXES_BRANDS[0], LIST_CHECKBOXES_BRANDS[2], LIST_CHECKBOXES_BRANDS[3]);

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[] {LIST_CHECKBOXES_BRANDS[0],
                                                                               LIST_CHECKBOXES_BRANDS[2],
                                                                               LIST_CHECKBOXES_BRANDS[3]});

    }

    @Test
    public void testFilterOperatingSystemAndroid() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Operating System", LIST_CHECKBOXES_OPERATING_SYSTEM[0]);

        assertThat(page.locator(FILTERED_PRODUCTS)).hasText(LIST_CHECKBOXES_OPERATING_SYSTEM[0]);
    }

    @Test
    public void testFilterCombineDealsBrands() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Deals", "New");
        filterProducts("Brands", "Alcatel", "Samsung", "T-Mobile");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[] {LIST_CHECKBOXES_DEALS[0],
                                                                               LIST_CHECKBOXES_BRANDS[0],
                                                                               LIST_CHECKBOXES_BRANDS[2],
                                                                               LIST_CHECKBOXES_BRANDS[3]});
    }

    @Test
    public void testFilterCombineAllGroups() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Deals", "Special offer");
        filterProducts("Brands", "Apple", "Samsung", "TCL");
        filterProducts("Operating System", " iPadOS", "Other");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[] {LIST_CHECKBOXES_DEALS[1],
                                                                               LIST_CHECKBOXES_BRANDS[1],
                                                                               LIST_CHECKBOXES_BRANDS[2],
                                                                               LIST_CHECKBOXES_BRANDS[4],
                                                                               LIST_CHECKBOXES_OPERATING_SYSTEM[1],
                                                                               LIST_CHECKBOXES_OPERATING_SYSTEM[2]});
    }

    @Test
    public void testFilterAllProductsAllGroups() {
        page.navigate("https://www.t-mobile.com/tablets");
        filterProducts("Deals", "all");
        filterProducts("Brands", "all");
        filterProducts("Operating System", "all");

        assertThat(page.locator(FILTERED_PRODUCTS)).containsText(new String[]{LIST_CHECKBOXES_DEALS[0],
                                                                              LIST_CHECKBOXES_DEALS[1],
                                                                              LIST_CHECKBOXES_BRANDS[0],
                                                                              LIST_CHECKBOXES_BRANDS[1],
                                                                              LIST_CHECKBOXES_BRANDS[2],
                                                                              LIST_CHECKBOXES_BRANDS[3],
                                                                              LIST_CHECKBOXES_BRANDS[4],
                                                                              LIST_CHECKBOXES_OPERATING_SYSTEM[0],
                                                                              LIST_CHECKBOXES_OPERATING_SYSTEM[1],
                                                                              LIST_CHECKBOXES_OPERATING_SYSTEM[2]});

    }
}