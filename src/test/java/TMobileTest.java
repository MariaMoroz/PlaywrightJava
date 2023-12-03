import base.BaseTest;
import com.microsoft.playwright.Locator;

import com.microsoft.playwright.options.AriaRole;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class TMobileTest extends BaseTest {
//1. Зайти на сайт t-mobile.com
//2. В верхнем меню Products & что-то там еще (не помню) выбрать Tablets
//3. На странице Tablets отсортировать цену  Low to High
//4. Verify that the prices of displayed products are arranged in ascending order, from the lowest to the highest. ❗️На самом деле надо проверить цену всех продуктов на странице. Уточняю, тк displayed, оказалось, можно понять по-разному )

    @Test
    public void verifyAllPricesAscendingSorted() {
        page.navigate("https://www.t-mobile.com/");

        page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("Phones & devices")).hover();
        page.getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHasText("Tablets")).click();
        page.locator("#mat-select-value-1").click();
        page.locator("#mat-option-5").click();
        Locator sortBy = page.locator("span mat-select-trigger ");

        assertThat(page).hasTitle("Compare Our Best Tablets for Sale | Apple, Samsung & More");
        assertThat(sortBy).hasText(" Sort by: Price Low to High ");

        Locator countItems = page.locator("[role='status'].item-count");
        String textCountItems = countItems.innerText().trim();
        int items = parseInt(textCountItems.replaceAll("[^0-9]", ""));
        System.out.println(items);

        Locator productCards = page.getByTestId("product-card-link");
        productCards.last().hover();

        List<String> allProductNames = page.locator("[_ngcontent-serverapp-c179] .small-card").allInnerTexts();
        allProductNames.remove(allProductNames.size() - 1);

        Assert.assertEquals(allProductNames.size(), items);

//        List<String> fprice = allProductNames.stream().map(d -> d.replace("\n", "")).collect(Collectors.toList());
//        List<String> newList = fprice.stream().map(el -> el.substring(el.lastIndexOf("$") +1).replaceAll("[^0-9.]+", "")).collect(Collectors.toList());
//        List<Double> fullPriceList = newList.stream().map(s -> parseDouble(s)).collect(Collectors.toList());
//        System.out.println(fullPriceList);
        List<Double> fullPriceList = new ArrayList<>();
        for (String str : allProductNames) {
            String newS = str.replace("\n", "");
            String fPrice = newS.substring(newS.lastIndexOf("$") + 1).replaceAll("[^0-9.]+", "");
            fullPriceList.add(parseDouble(fPrice));
        }

        List<Double> actualPricesList = fullPriceList;
        System.out.println(actualPricesList);
        Collections.sort(fullPriceList);
        System.out.println(fullPriceList);

        Assert.assertEquals(actualPricesList, fullPriceList);

//        [T-Mobile®Rating:4.2out of 5 stars.Total reviews count is:(329)Navigate to reviewsMobile Internet SIM CardFull price$0+ tax,
//        Offers availableFREE Alcatel JOY TAB™ Kids 2AlcatelRating:1.8out of 5 stars.Total reviews count is:(5)Navigate to reviewsJOY TAB™ KIDS 2Starting atMonthly$0 $7.00for 24 monthsToday$0down + taxFull price: $168.00,
//        Offers availableFREE with a new tablet lineTCLRating:4.5out of 5 stars.Total reviews count is:(2)Navigate to reviewsTAB 8 LEStarting atMonthly$0 $7.00for 24 monthsToday$0down + taxFull price: $168.00,
//        Offers availableFREE with a new tablet lineT-Mobile®Rating:4.4out of 5 stars.Total reviews count is:(5)Navigate to reviewsREVVL® TAB 5GStarting atMonthly$0 $8.34for 24 monthsToday$0down + taxFull price: $199.99,
//        Offers availableFREE with a new tablet lineSamsungRating:3.8out of 5 stars.Total reviews count is:(164)Navigate to reviewsGalaxy Tab A7 LiteStarting atMonthly$0 $8.34for 24 monthsToday$0down + taxFull price: $199.99,
//        Offers available50% off when you add a new lineAppleRating:4.4out of 5 stars.Total reviews count is:(299)Navigate to reviewsiPad 9th genStarting atMonthly$9.58 $19.17for 24 monthsToday$0down + taxFull price: $459.99,
//        Offers available50% off with new tablet lineSamsungRating:4.7out of 5 stars.Total reviews count is:(43)Navigate to reviewsGalaxy Tab S9 FE 5GStarting atMonthly$11.46 $22.92for 24 monthsToday$0down + taxFull price: $549.99,
//        Offers available$230 off any iPad with a new lineAppleRating:4.5out of 5 stars.Total reviews count is:(10)Navigate to reviewsiPad 10th genStarting atMonthly$15.41 $25.00for 24 monthsToday$0down + taxFull price: $599.99,
//        Offers available$230 off any iPad with a new lineAppleRating:4.6out of 5 stars.Total reviews count is:(180)Navigate to reviewsiPad mini 6th genStarting atMonthly$15.41 $25.00for 24 monthsToday$49.99down + taxFull price: $649.99,
//        Offers availableSee 2 dealsSamsungRating:4.5out of 5 stars.Total reviews count is:(48)Navigate to reviewsGalaxy Tab S7 FE 5GStarting atMonthly$4.12 $27.92for 24 monthsToday$0down + taxFull price: $669.99,
//        Offers available$230 off any iPad with a new lineAppleRating:4.8out of 5 stars.Total reviews count is:(66)Navigate to reviewsiPad Air 5th genStarting atMonthly$20.41 $30.00for 24 monthsToday$29.99down + taxFull price: $749.99,
//        Offers available$230 off any iPad with a new lineAppleRating:5.0out of 5 stars.Total reviews count is:(1)Navigate to reviewsiPad Pro 11-inch 4th genStarting atMonthly$20.41 $30.00for 24 monthsToday$279.99down + taxFull price: $999.99,
//        Offers available50% off with new tablet lineSamsungRating:4.4out of 5 stars.Total reviews count is:(9)Navigate to reviewsGalaxy Tab S8+ 5GStarting atMonthly$7.08 $30.00for 24 monthsToday$379.99down + taxFull price: $1,099.99,
//        Offers available$230 off any iPad with a new lineAppleRating:4.7out of 5 stars.Total reviews count is:(3)Navigate to reviewsiPad Pro 12.9-inch 6th genStarting atMonthly$20.41 $30.00for 24 monthsToday$579.99down + taxFull price: $1,299.99, ]
    }
}
