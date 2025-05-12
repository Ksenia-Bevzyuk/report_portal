package io.reportportal.demo.client.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static io.reportportal.demo.client.pom.AddDashboardsPage.ADD_NEW_WIDGET;

public class DashboardsPage {
    private String xpathDash = ".//div[@data-id = '%s']/div/a";

    private WebDriver driver;

    public DashboardsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик для перехода в существующий Dashboard")
    public void clickToNewDashboard(int idDash) {
        By dash = By.xpath(String.format(xpathDash, idDash));
        WebElement addDash = driver.findElement(dash);
        addDash.isEnabled();
        addDash.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(ADD_NEW_WIDGET));
    }
}
