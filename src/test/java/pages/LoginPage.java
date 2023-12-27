package pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage{

    private String userNameInput = "#j_username";
    private String passwordInput = "input[name='j_password']";
    private String submitBtn = "button[name='Submit']";

    public LoginPage(Page page) {
        super(page);
    }

    public LoginPage fillUserNameInput(String userName) {
        getElementByLocator(userNameInput).fill(userName);
        return this;
    }

    public LoginPage fillPasswordInput(String password) {
        getElementByLocator(passwordInput).fill(password);
        return this;
    }

    public HomePage clickSubmitBtn () {
        getElementByLocator(submitBtn).click();
        return new HomePage(getPage());
    }

    public HomePage loginUser(String userName, String password) {
        fillUserNameInput(userName);
        fillPasswordInput(password);
        clickSubmitBtn();

        return new HomePage(getPage());
    }
}
