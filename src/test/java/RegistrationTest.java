import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class RegistrationTest {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        String user = System.getProperty("user");
        String password = System.getProperty("password");
        String remoteBrowser = System.getProperty("selenoid.autotests.cloud/wd/hub");

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC",true);
        capabilities.setCapability("enableVideo",true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "https://" + user + ":" + password + "@" + remoteBrowser;
    }

    @AfterEach
    void addAttachments () {
        Attach.screenshotAs("Screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @Test
    void succesFillTest() {
        step("Открываем форму", () -> {
        open("/automation-practice-form");
        });

        step("Проверяем хэдер", () -> {
        $(".main-header").shouldHave(text("Practice Form"));
        });

        step("Вводим имя", () -> {
        $("#firstName").setValue("Sergei");
        });

        step("Вводим фамилию", () -> {
        $("#lastName").setValue("Kashtuev");
        });

        step("Вводим почту", () -> {
        $("#userEmail").setValue("kashtuev@gmail.com");
        });

        step("Выбираем пол", () -> {
        $("#genterWrapper").find(byText("Male")).click();
        });

        step("Вводим номер телефона", () -> {
        $("#userNumber").setValue("9515705298");
        });

        step("Выбираем дату рождения", () -> {
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("January");
        $(".react-datepicker__year-select").selectOption("1990");
        $$(".react-datepicker__day").find(text("29")).click();
        });

        step("Выбираем предмет", () -> {
        $("#subjectsInput").setValue("English").pressEnter();
        });

        step("Выбираем увлечения", () -> {
        $(byText("Music")).click();
        });

        step("Загружаем фото", () -> {
//        $("#uploadPicture").uploadFromClasspath("mexico.jpg");
        });

        step("Указываем страну", () -> {
        $("#currentAddress").setValue("Russia");
        });

        step("Выбираем штат", () -> {
        $("#state").click();
        $(byText("NCR")).click();
        });

        step("Выбираем город", () -> {
        $("#city").click();
        $(byText("Delhi")).click();
        });

        step("Потверждаем ввод данных", () -> {
        $("#state").click();
        $("#submit").click();
        });
    }
}