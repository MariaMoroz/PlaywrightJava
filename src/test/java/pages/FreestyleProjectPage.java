package pages;

import com.microsoft.playwright.Page;

public class FreestyleProjectPage extends BasePage {

    public FreestyleProjectPage(Page page) {
        super(page);
    }

    public ConfigureFreestyleProjectPage clickConfigureSideMenuLink() {
        getLink("Configure").click();

        return new ConfigureFreestyleProjectPage(getPage());
    }
}
