////TC01 “Main page title is "Сurrent weather and forecast - OpenWeatherMap"
////        Go to "https://openweathermap.org/"
////        Verify that the main page title is "Сurrent weather and forecast - OpenWeatherMap"
////
////
////        TC02 “Current temperature is shown in Celsium by default”
////        Go to "https://openweathermap.org/"
////        Verify that the current temp is shown on the main page
////        Verify that the current temp’s unit is a Celcium by  default
//
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.By;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
//
//import java.util.List;
//
//import static java.lang.Thread.sleep;
//
//public class SeleniumTest {
//
//    @Test
//    public void verifyTitle() throws InterruptedException {
//        WebDriver driver = new ChromeDriver();
//
//        driver.get("https://openweathermap.org/");
//        sleep(10000);
//        String title = driver.getTitle();
//        System.out.println(title);
//
//        Assertions.assertEquals("Сurrent weather and forecast - OpenWeatherMap", title);
//
//        driver.close();
//    }
//
//    @Test
//    public void verifyTempUnit() throws InterruptedException {
//        WebDriver driver = new ChromeDriver();
//
//        driver.get("https://openweathermap.org/");
//        sleep(10000);
//        WebElement currTemp = driver.findElement(By.cssSelector(".current-temp .heading"));
//        System.out.println(currTemp.getText());
//
//        Assertions.assertEquals("8°C", currTemp.getText());
//        driver.close();
//    }
//
//    //    TC-03
////    Go to https://naveenautomationlabs.com/opencart/
////    In the Search field input “phone”
////    Click Search button
////    Verify that at least one product is shown on the screen as a Search result
//    @Test
//    public void verifySearchField() {
//        WebDriver driver = new ChromeDriver();
//
//        driver.get("https://naveenautomationlabs.com/opencart/");
//
//        WebElement searchField = driver.findElement(By.cssSelector("#search input[name='search']"));
//        searchField.sendKeys("phone");
//        WebElement searchBtn = driver.findElement(By.cssSelector("#search button[type='button']"));
//        searchBtn.click();
//        List<WebElement> productsRaw = driver.findElements(By.cssSelector(".row .product-layout"));
//        int lengthProductsRaw = productsRaw.size();
//
//        Assertions.assertFalse(productsRaw.isEmpty());
//        Assertions.assertEquals(lengthProductsRaw, 1);
//        driver.close();
//    }
//
//    //    TC-04
////    Go to https://naveenautomationlabs.com/opencart/
////    Click on Components -> Monitors menu
////    Get the amount of Monitors products available in the store
////    Hover over the Components menu
////    Verify that the drop-down menu for the section Monitors shows the same amount  of Monitors products available in the stor
//    @Test
//    public void componentsTest() throws InterruptedException {
//        WebDriver driver = new ChromeDriver();
//
//        driver.get("https://naveenautomationlabs.com/opencart/");
//
//        WebElement menuComponentsLink = driver.findElement(By.xpath("//li[@class='dropdown']/a[text()= 'Components']"));
//        menuComponentsLink.click();
//        WebElement subMenuMonitorsLink = driver.findElement(By.cssSelector("div.dropdown-inner> ul a[href*='category&path=25_28']"));
//        String submenuMonitorsText = subMenuMonitorsLink.getText();
//        subMenuMonitorsLink.click();
//        System.out.println(submenuMonitorsText);
//        List<WebElement> productsRaw = driver.findElements(By.cssSelector(".row .product-layout"));
//        int lengthProductsRaw = productsRaw.size();
//        Actions actions = new Actions(driver);
//        actions.moveToElement(menuComponentsLink);
//
//        Assertions.assertTrue(submenuMonitorsText.contains(String.valueOf(lengthProductsRaw)));
//        driver.close();
//    }
//}
