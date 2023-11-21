import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LambdaTest extends BaseTest {

    @Test
    public void playgroundTest () {
        page.navigate("https://ecommerce-playground.lambdatest.io/");
//        1.Verify the user is navigated to the eCommerce website.
//        2.Verify page title is Your Store.
        assertThat(page).hasURL("https://ecommerce-playground.lambdatest.io/");
        assertThat(page).hasTitle("Your Store");

//        Click on Shop by Category.
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Shop by Category")).click();
        Locator menuCategories = page.locator("#widget-navbar-217841 ul.navbar-nav");
//        1.Verify Shop by Category menu is visible.
//        2.Verify Top categories is shown to the user.
        assertThat(menuCategories).isVisible();
        assertThat(page.getByText("Top categories ")).isVisible();

//        Click on the Components category.
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Components")).click();
        Locator productsList = page.locator("h4.title a.text-ellipsis-2");
        Locator productHTC = productsList.filter(new Locator.FilterOptions().setHasText("HTC Touch HD"));
//        1.Verify the user is redirected to the Components page
//        2.Verify the page title is ‘Components’
//        3.Verify ‘HTC Touch HD’ is shown in the list.
        assertThat(page).hasURL("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25");
        assertThat(page).hasTitle("Components");
        assertThat(productHTC).isVisible();

//        Click on the first product, ‘HTC Touch HD’
        productHTC.click();
        Locator headerProductDetailsPage = page.locator(".content-title h1");
        Locator addToCartBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ADD To CART"));
//        1.Verify the user is navigated to the ‘HTC Touch HD’ product details page.
//        2.Verify page title is ‘HTC Touch HD’ Verify Availability is shown as ‘In Stock’
//        3.Verify the ‘ADD To CART’ button is enabled.
        assertThat(page).hasTitle("HTC Touch HD");
        assertThat(headerProductDetailsPage).hasText("HTC Touch HD");
        assertThat(page.getByText("In Stock")).isVisible();
        assertThat(addToCartBtn).isEnabled();

//        Click on the ‘ADD To CART’ button.
        addToCartBtn.click();
        Locator successMessage = page.locator("div.toast-body div p");
//        1.Verify product is successfully added to the cart.
        assertThat(successMessage).isVisible();
        assertThat(successMessage).hasText("Success: You have added HTC Touch HD to your shopping cart!");

        //Click on the ‘Cart’ icon.
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CLOSE")).click();
        Locator cartIcon = page.locator("div#entry_217825>a[href='#cart-total-drawer']");
        cartIcon.click();
        Locator cartPageHeader = page.locator("#cart-total-drawer h5");
        Locator checkoutBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout"));
        Locator sidePanelCart = page.locator("#cart-total-drawer div.entry-component");
//        1.Verify the Cart side panel is open
//        2.Verify the Checkout button is shown to the user
        assertThat(sidePanelCart).isVisible();
        assertThat(cartPageHeader).hasText("Cart");
        assertThat(checkoutBtn).isVisible();
        assertThat(checkoutBtn).isEnabled();

//        Click on the ‘Checkout’ button.
        checkoutBtn.click();
        Locator productDetails = page.locator("#checkout-cart table td:nth-child(2)");
        Locator accountInfo = page.locator("input[name='account']~label");
        String[] accountInfoList = {"Login", "Register Account", "Guest Checkout"};
//        1.Verify the user is redirected to the Checkout page.
//        2.Verify product details are shown in the right side pane.
//        Since you didn’t log into the application on the right-hand side, you will also see the Registration/Login/Guest flow.
        assertThat(page).hasURL("https://ecommerce-playground.lambdatest.io/index.php?route=checkout/checkout");
        assertThat(page).hasTitle("Checkout");
        assertThat(productDetails).isVisible();
        assertThat(accountInfo).hasText(accountInfoList);
        Object accountInfoText = accountInfo.evaluateAll(
                "list => list.map(element => element.textContent)");
        System.out.println(accountInfoText);
    }
}
