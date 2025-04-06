package io.reportportal.demo.client.pom;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static io.reportportal.demo.client.pom.AddDashboardsPage.WIDGET_TYPE;

public class AddWidgetPage {
    private final By NEXT_BUTTON =
            By.xpath(".//div[@class = 'wizardControlsSection__button--PtN2R']/button");
    private final By WIDGET_FIELDS =
            By.xpath(".//div[@class='modalField__modal-field-content--A4lX0']/div/input");
    private String widgetHeader = ".//div[text() = '%s']";
    private final By ADD_BUTTON =
            By.xpath(".//button[text()='Add']");

    private WebDriver driver;

    public AddWidgetPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Выбор типа виджета")
    public void chooseWidgetType() {
        WebElement widgetType = driver.findElements(WIDGET_TYPE).get(4);
        widgetType.click();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .attributeContains(widgetType, "class", "widgetTypeItem__active--MZXpV"));
    }

    @Step("Клик next на второй шаг")
    public void clickNext() {
        driver.findElement(NEXT_BUTTON).click();
    }

    @Step("Клик next на третий шаг")
    public void clickToNextButton() {
        WebElement nextButtonOnConfigTab = driver.findElements(NEXT_BUTTON).get(1);
        nextButtonOnConfigTab.click();
    }

    @Step("Генерация имени виджета")
    public String genNameWidget() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    @Step("Заполнение имени виджета")
    public void setWidgetNameField(String nameWidget) {
        WebElement nameWidgetField = driver.findElement(WIDGET_FIELDS);
        nameWidgetField.click();
        nameWidgetField.clear();
        nameWidgetField.sendKeys(nameWidget);
    }

    @Step("Клик add для добавления виджета на dashboard")
    public void clickToAddButton() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .elementToBeClickable(ADD_BUTTON));
        driver.findElement(ADD_BUTTON).click();
    }

    @Step("Проверка успешности создания виджета")
    public boolean isSuccessCreateWidget(String nameWidget) {
        By widgetHeaderWithName = By.xpath(String.format(widgetHeader, nameWidget));
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(widgetHeaderWithName));

        return driver.findElement(widgetHeaderWithName).isDisplayed();
    }

    @Step("Шаг: добавление нового виджета")
    public void addWidget(String nameWidget) {
        chooseWidgetType();
        clickNext();
        clickToNextButton();
        setWidgetNameField(nameWidget);
        clickToAddButton();
    }
}
