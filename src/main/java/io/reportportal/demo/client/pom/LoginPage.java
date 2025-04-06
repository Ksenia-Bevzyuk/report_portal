package io.reportportal.demo.client.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static io.reportportal.demo.client.pom.LaunchPage.SUCCESS_LOGIN;

public class LoginPage {
    private final String REPORT_PORTAL_URL = "https://demo.reportportal.io";
    private final By LOGIN_FIELD = By.name("login");
    private final By PASS_FIELD = By.name("password");
    private final By LOGIN_BUTTON = By.xpath(".//button[text() = 'Login']");

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу")
    public void open() {
        driver.get(REPORT_PORTAL_URL);
        new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(LOGIN_FIELD));
    }

    @Step("Заполнить поле login в форме входа")
    public void setLoginField(String login) {
        WebElement loginField = driver.findElement(LOGIN_FIELD);
        loginField.clear();
        loginField.sendKeys(login);
    }

    @Step("Заполнить поле password в форме входа")
    public void setPassField(String password) {
        WebElement passField = driver.findElement(PASS_FIELD);
        passField.clear();
        passField.sendKeys(password);
    }

    @Step("Клик по кнопке login для входа в аккаунт")
    public void clickToLogin() {
        WebElement loginButton = driver.findElement(LOGIN_BUTTON);
        loginButton.isEnabled();
        loginButton.click();
    }

    @Step("Проверка успешности входа в аккаунт")
    public boolean isSuccessLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(SUCCESS_LOGIN));
        return driver.findElement(SUCCESS_LOGIN).isDisplayed();
    }

    @Step("Шаг: заполнение полей и клик login для входа в аккаунт")
    public void setLoginFields(String login, String password) {
        setLoginField(login);
        setPassField(password);
        clickToLogin();
    }
}
