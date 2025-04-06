package io.reportportal.demo.client.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AddDashboardsPage {
    protected static final By ADD_NEW_WIDGET =
            By.className("dashboardItemPage__buttons-block--QoL50");
    protected static final By WIDGET_TYPE =
            By.className("widgetTypeItem__widget-type-item-name--WYizn");

    private WebDriver driver;

    public AddDashboardsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Добавление нового виджета")
    public void clickToAddNewWidget() {
        WebElement addNewWidget = driver.findElements(ADD_NEW_WIDGET).get(0);
        addNewWidget.click();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(WIDGET_TYPE));
    }
}
