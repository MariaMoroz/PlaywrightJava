package pages;

import com.microsoft.playwright.Page;

public class NewJobPage extends BasePage{

    public NewJobPage(Page page) {
        super(page);

    }

    public NewJobPage fillItemNameInput(String itemName) {
        getElementByLabel("Enter an item name").fill(itemName);

        return this;
    }

    public NewJobPage clickOnFreestyleType() {
        getElementByRoleRadio("Freestyle project").click();

        return this;
    }

    public ConfigureFreestyleProjectPage clickOkBtn() {
        getElementByRoleButton("OK").click();

        return new ConfigureFreestyleProjectPage(getPage());
    }
}
