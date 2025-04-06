package io.reportportal.demo.client.pom;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class ProfilePage {
    protected static final By ASSIGN_PROJECTS =
            By.xpath(".//div[@class='navigationTabs__tabs-wrapper--JYPRb']" +
                    "/a[text()='Project assignment']");
    private final By API_KEYS = 
            By.xpath(".//div[@class='navigationTabs__tabs-wrapper--JYPRb']" +
                    "/a[text()='API keys']");
    private final By PROJECT =
            By.className("assignedProjectsBlock__name-col--VWj2G");
    private final By GEN_API_KEY =
            By.xpath(".//span[text()='Generate API Key']/parent::button");
    private final By NAME_API_KEY_FIELD =
            By.xpath(".//input[@type='text']");
    private final By GENERATE_BUTTON =
            By.xpath(".//button[text()='Generate']");
    private final By COPY_BUTTON =
            By.xpath(".//button[text()='Copy to Clipboard']");
    private final By CLOSE_BUTTON =
            By.xpath(".//button[text()='Close']");
    private final By FIELD_WITH_API_KEY =
            By.xpath(".//input[@type='text']");

    private WebDriver driver;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Выбор вкладки в профиле")
    public void clickToTab(String tab) {
        By tabLocator;
        if(Objects.equals(tab,"projects")){
            tabLocator = ASSIGN_PROJECTS;
        } else if(Objects.equals(tab, "API key")) {
            tabLocator = API_KEYS;
        } else {
            tabLocator= ASSIGN_PROJECTS;
            System.out.println("Выбрана вкладка по умолчанию");
        }
        driver.findElement(tabLocator).click();
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .attributeContains(tabLocator, "aria-current", "true"));
    }

    @Step("Получение названия существующего проекта")
    public String getNameExistProject() {
        List<WebElement> list = driver.findElements(PROJECT);
        return list.get(1).getText();
    }

    @Step("Клик для генерации API key")
    public void genApiKeyClick() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .elementToBeClickable(GEN_API_KEY));
        driver.findElement(GEN_API_KEY).click();;
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(NAME_API_KEY_FIELD));
    }

    @Step("Генерация имени API key")
    public String genNameApiKey() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    @Step("Заполнение имени API key")
    public void setApiKeyName(String nameApiKey) {
        driver.findElement(NAME_API_KEY_FIELD).sendKeys(nameApiKey);
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .elementToBeClickable(GENERATE_BUTTON)).click();
    }

    @Step("Получение API key")
    public String getApiKey() {
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .elementToBeClickable(COPY_BUTTON));
        String apiKey = driver.findElement(FIELD_WITH_API_KEY).getDomProperty("value");
        return String.format("Bearer %s", apiKey);
    }

    @Step("Закрыть всплывающее окно с api key")
    public void closeWindow() {
        driver.findElement(CLOSE_BUTTON).click();
    }

    @Step("Шаг: получение имени существующего проекта")
    public String getProjectName() {
        clickToTab("projects");
        return getNameExistProject();
    }

      @Step("Шаг: генерация API key")
    public void genApiKey() {
        clickToTab("API key");
        genApiKeyClick();
    }

    @Step("Шаг: генерация и получение API key")
    public String genAndGetApiKey(String nameApiKey) {
        setApiKeyName(nameApiKey);
        return getApiKey();
    }
}
