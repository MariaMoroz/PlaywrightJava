//TC01 “Main page title is "Сurrent weather and forecast - OpenWeatherMap"
//        Go to "https://openweathermap.org/"
//        Verify that the main page title is "Сurrent weather and forecast - OpenWeatherMap"
//
//
//        TC02 “Current temperature is shown in Celsium by default”
//        Go to "https://openweathermap.org/"
//        Verify that the current temp is shown on the main page
//        Verify that the current temp’s unit is a Celcium by  default

import base.BaseTest;
import com.microsoft.playwright.*;
import org.testng.annotations.Test;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FirstPlaywrightTest extends BaseTest {

    //                TC01
    @Test
    public void navigatePage() {
        page.navigate("https://openweathermap.org/");

        String title = page.title();
        System.out.println(title);

        assertThat(page).hasTitle("");
    }
    //                TC02
    @Test
    public void verifyTempUnit() {
        page.navigate("https://openweathermap.org/");

        Locator currTemp = page.locator("css=.current-temp .heading");
        String currentTemp = currTemp.innerText();
        System.out.println(currentTemp);

        assertThat(currTemp).isVisible();
        assertThat(currTemp).containsText("C");
    }
}
