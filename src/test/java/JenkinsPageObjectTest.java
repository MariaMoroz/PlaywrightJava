import base.BaseTest;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static constants.JenkinsConstants.*;


public class JenkinsPageObjectTest extends BaseTest {

    @Test
    public void verifyHelpTooltipText() {
        page.navigate("http://localhost:8080/");

        new LoginPage(page)
                .loginUser(loginUserName, loginPassword);

        new HomePage(page)
                .cleanDashboard()
                .clickNewItemMenuLink()
                .fillItemNameInput(projectName)
                .clickOnFreestyleType()
                .clickOkBtn();

        Locator tooltipHelp = new HomePage(page)
                .clickDashboardLink()
                .clickOnProjectNameLink(projectName)
                .clickConfigureSideMenuLink()
                .clickOnDiscardOldBuildsHelpBtn()
                .getDiscardOldBuildsTooltipText();

        assertThat(tooltipHelp).hasText(expectedTooltipHelpText);
    }
}
