package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ConfigureFreestyleProjectPage extends BasePage {

    private String discardOldBuildsHelpBtn = "a[tooltip='Help for feature: Discard old builds']";
    private String discardOldBuildsTooltipText = ".help-area>.help div";

    public ConfigureFreestyleProjectPage(Page page) {
        super(page);
    }

    public FreestyleProjectPage clickOnSaveBtn() {
        getElementByRoleButton("Save").click();

        return new FreestyleProjectPage(getPage());
    }

    public ConfigureFreestyleProjectPage clickOnDiscardOldBuildsHelpBtn() {
        getElementByLocator(discardOldBuildsHelpBtn).click();

        return this;
    }

    public Locator getDiscardOldBuildsTooltipText() {

        return getElementByLocator(discardOldBuildsTooltipText);
    }
}
