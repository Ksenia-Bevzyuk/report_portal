import io.qameta.allure.Description;
import io.reportportal.demo.client.ReportPortalClient;
import io.reportportal.demo.client.pom.*;
import io.reportportal.demo.model.Dashboard;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import webDriver.WebDriverFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class AddWidgetTest {
    private WebDriver driver;
    private AddWidgetPage objAddWidgetPage;
    private ReportPortalClient clientAuth;
    private String genName;
    private String genDesc;
    private ValidatableResponse response;
    private int dashId;
    private String apiKey;
    private String project;
    private String nameApiKey;

    @BeforeEach
    @DisplayName("Перед каждым тестом")
    @Description("Инициализация драйвера, неявное ожидание, войти в профиль, создать Dashboard")
    public void before() {
        driver = WebDriverFactory.createWebDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.open();
        objLoginPage.setLoginFields("default", "1q2w3e");
        assumeTrue(objLoginPage.isSuccessLogin());

        LaunchPage objLaunchPage = new LaunchPage(driver);
        objLaunchPage.successGoToProfile();
        assumeTrue(objLaunchPage.isSuccessGoToProfile());

        ProfilePage objProfilePage = new ProfilePage(driver);
        project = objProfilePage.getProjectName();
        objProfilePage.genApiKey();
        nameApiKey = objProfilePage.genNameApiKey();
        apiKey = objProfilePage.genAndGetApiKey(nameApiKey);
        clientAuth = new ReportPortalClient(apiKey);
        objProfilePage.closeWindow();

        genName = Dashboard.genName();
        genDesc = Dashboard.genDescription();

        response = clientAuth.createDashboard(new Dashboard(genDesc, genName), project);
        response.assertThat().statusCode(SC_CREATED).body("id", notNullValue());
        dashId = clientAuth.getDashId(response);
        clientAuth.checkDashboard(dashId, project).statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка добавления виджета на существующий Dashboard")
    @Description("Вход в систему, переход на существующий Dashboard, добавление виджета")
    public void createNewWidgetOnDashboardTest() {
        LaunchPage objLaunchPage = new LaunchPage(driver);
        objLaunchPage.clickToDashboards();

        DashboardsPage objDashboardsPage = new DashboardsPage(driver);
        objDashboardsPage.clickToNewDashboard(dashId);

        AddDashboardsPage objDemoDashPage = new AddDashboardsPage(driver);
        objDemoDashPage.clickToAddNewWidget();

        objAddWidgetPage = new AddWidgetPage(driver);
        String nameWidget = objAddWidgetPage.genNameWidget();
        objAddWidgetPage.addWidget(nameWidget);

        assertTrue(objAddWidgetPage.isSuccessCreateWidget(nameWidget));
    }

    @AfterEach
    @DisplayName("Удаление созданного dashboard, apiKey и закрытие браузера")
    @Description("Удаление созданного dashboard, apiKey после каждого теста, закрытие браузера")
    public void deleteDash() {
        driver.quit();

        if (response.extract().statusCode() == SC_CREATED) {
            ValidatableResponse responseDelDash = clientAuth.daleteDashboard(dashId, project);
            responseDelDash
                    .assertThat()
                    .statusCode(SC_OK)
                    .body("message", notNullValue());
        }
        ValidatableResponse responseGetUser = clientAuth.getUserInfo();
        int userId = clientAuth.getUserId(responseGetUser);
        ValidatableResponse responseGetKey = clientAuth.getUserApiKey(userId);
        int keyId = clientAuth.getApiId(responseGetKey, nameApiKey);

        clientAuth.deleteApiKey(userId, keyId).statusCode(SC_OK);
    }
}
