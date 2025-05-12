package io.reportportal.demo.client.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static io.reportportal.demo.client.pom.ProfilePage.ASSIGN_PROJECTS;

public class LaunchPage {
    protected static final By SUCCESS_LOGIN =
            By.xpath(".//p[text() = 'Signed in successfully']");
    private final By AVATAR =
            By.className("userBlock__avatar--x5As7");
    private final By PROFILE =
            By.xpath(".//a[text() = 'Profile']");
    protected static final By DASHBOARDS =
            By.xpath(".//div[@class='sidebarButton__sidebar-nav-btn--gbV_N']/a");

    private WebDriver driver;

    public LaunchPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по аватару пользователя")
    public void clickToAvatar() {
        driver.findElement(SUCCESS_LOGIN).click();
        WebElement avatar = driver.findElement(AVATAR);
        avatar.isDisplayed();
        avatar.click();
    }

    @Step("Клик по profile, для перехода в профиль пользователя")
    public void clickToProfile() {
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(PROFILE)).click();
    }

    @Step("Ожидание перехода в профиль")
    public boolean isSuccessGoToProfile() {
        return new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .attributeContains(ASSIGN_PROJECTS, "aria-current", "true"));
    }

    @Step("Клик по иконке dashboards для перехода в Dashboards")
    public void clickToDashboards() {
        WebElement dashIcon = driver.findElements(DASHBOARDS).get(0);
        dashIcon.click();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                        .attributeContains(DASHBOARDS, "aria-current", "true"));
    }

    @Step("Шаг: переход в профиль")
    public void successGoToProfile() {
        clickToAvatar();
        clickToProfile();
    }
}
