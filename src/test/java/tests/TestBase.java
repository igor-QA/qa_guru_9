package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;
import static helpers.AttachmentsHelper.attachVideo;

public class TestBase {

    @BeforeAll
    static void setup() {
        String selenoidLogin = System.getProperty("selenoid.username");
        String selenoidPwd = System.getProperty("selenoid.pwd");
        String remoteBrowser = System.getProperty("remote.browser.url");
        String url = "https://"+ selenoidLogin + ":" + selenoidPwd  + "@" + remoteBrowser + ":4444/wd/hub/";

        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.remote = url;
        Configuration.browserCapabilities = capabilities;
        Configuration.startMaximized = true;
        //Configuration.browser = FIREFOX;
    }

    @AfterEach
    @Step("Attachments")

    public void afterEach () {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();

        closeWebDriver();
    }
}