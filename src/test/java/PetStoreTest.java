import base.BaseTest;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PetStoreTest extends BaseTest {

    @Test
    public void verifySubMenuFish() {
        //TC-01 “Menu Fish → Submenus “Saltwater”, “Freshwater”
////
////        Go to https://petstore.octoperf.com/
////        Enter the Store
////        Verify that the left side menu “Fish” has the submenus “Saltwater”, “Freshwater”
//            Locator MenuFishImg = page.locator("css=#SidebarContent a[href*='FISH']>img");
//            Locator subMenu = page.locator("#SidebarContent a[href*='FISH']+br");

        getPage().navigate("https://petstore.octoperf.com/");
        Locator enterStoreLink = getPage().locator("css=p a[href*='actions']");
        enterStoreLink.click();

        Locator SidebarContent = getPage().locator("css=#SidebarContent");
//            Locator SideBarArray = page.locator("css=#SidebarContent a img");
        String[] subMenuText = SidebarContent.innerText().trim().split("\n");
        System.out.println(subMenuText[0]);

        assertThat(SidebarContent).isVisible();
        assertThat(SidebarContent).containsText("Saltwater, Freshwater");

//            assertThat(SideBarArray.nth(0)).hasAttribute("src", "../images/fish_icon.gif");

    }

    @Test
    public void signInTest() {
//    TC-02 “Sign-out link with the text “Sign Out”
//
//            Go to https://petstore.octoperf.com/
//            Enter the Store
//            Click the “Sign In” link on the top menu
//            Enter username “Tester1”
//            Enter Password “vvv”
//            Verify that after successful login, the top menu has the sign-out link with the text “Sign Out”

            getPage().navigate("https://petstore.octoperf.com/");
            Locator enterStoreLink = getPage().locator("css=p a[href*='actions']");
            enterStoreLink.click();
            Locator signInLink = getPage().locator("#MenuContent a[href*='signonForm']");
            signInLink.click();
            Locator inputUserName = getPage().locator("input[name='username']");
            inputUserName.fill("Tester1");
            Locator inputPassword = getPage().locator("input[name='password']");
            inputPassword.fill("vvv");
            Locator signOutLink = getPage().locator("#MenuContent a[href*='signoff']");
            Locator loginBtn = getPage().locator("input[value='Login']");
            loginBtn.click();

            assertThat(signOutLink).hasText("Sign Out");
    }
}
