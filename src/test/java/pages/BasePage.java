package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public abstract class BasePage {
    private Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected Page getPage() {
        return page;
    }

    public Locator getLink(String text) {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text).setExact(true));
    }

    public Locator getElementByLocator(String locator) {

        return page.locator(locator);
    }

    public Locator getElementByLabel(String labelName) {

        return page.getByLabel(labelName);
    }

    public Locator getElementByText(String text) {

        return page.getByText(text);
    }

    public Locator getElementByRoleRadio(String name) {

        return page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName(name));
    }

    public Locator getElementByRoleButton(String buttonName) {

        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(buttonName));
    }
}
