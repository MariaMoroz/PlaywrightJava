package pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage{

    private String projectDropDownMenu = "#projectstatus button.jenkins-menu-dropdown-chevron";
    private String dropDownMenuDeleteProject = "button.jenkins-dropdown__item[href*='/doDelete']";
    private String projectsTable = "table#projectstatus";
    private String projectNameLink = "td a[href*='job'].jenkins-table__link";

    public HomePage(Page page) {
        super(page);
    }

    public HomePage clickDashboardLink() {
        getLink("Dashboard").click();

        return this;
    }

    public HomePage hoverProjectName(String projectName) {
        getLink(projectName).hover();

        return this;
    }

    public HomePage hoverAnyProjectName() {
        getElementByLocator(projectNameLink).hover();

        return this;
    }

    public HomePage clickProjectDropDownMenu() {
        getElementByLocator(projectDropDownMenu).click();

        return this;
    }

    public HomePage clickDeleteProjectByDropDownMenu() {
        getElementByLocator(dropDownMenuDeleteProject).click();

        return this;
    }

    public HomePage cleanDashboard() {
        getPage().onceDialog(Dialog::accept);

        if(getPage().locator(projectsTable).isVisible()) {
           hoverAnyProjectName();
           clickProjectDropDownMenu();
           clickDeleteProjectByDropDownMenu();
        }

        return this;
    }

    public NewJobPage clickNewItemMenuLink() {
        getLink("New Item").click();

        return new NewJobPage(getPage());
    }

    public FreestyleProjectPage clickOnProjectNameLink(String projectName) {
        getLink(projectName).click();

        return new FreestyleProjectPage(getPage());
    }
}
