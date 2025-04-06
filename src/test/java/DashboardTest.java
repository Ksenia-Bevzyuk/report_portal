import io.qameta.allure.Description;
import io.reportportal.demo.client.ReportPortalClient;
import io.reportportal.demo.client.pom.LaunchPage;
import io.reportportal.demo.client.pom.LoginPage;
import io.reportportal.demo.client.pom.ProfilePage;
import io.reportportal.demo.model.Dashboard;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import webDriver.WebDriverFactory;
import java.util.concurrent.TimeUnit;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class DashboardTest {
    private ReportPortalClient clientAuth;
    private String genName;
    private String genDesc;
    private ValidatableResponse response;
    private int dashId;
    private String apiKey;
    private String project;
    private String nameApiKey;
    private WebDriver driver;

    @BeforeEach
    @DisplayName("Перед каждым тестом")
    @Description("Получение userId из информации о пользователе, " +
            "получение api key, авторизация пользователя")
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

        genName = Dashboard.genName();
        genDesc = Dashboard.genDescription();
    }

    @Test
    @DisplayName("POST /api/v1/default_personal/dashboard корректно заполнены все поля")
    @Description("Успешное создание dashboard, заполнены все поля")
    public void createDashAllFieldCorrectSuccessTest() {
        response = clientAuth.createDashboard(new Dashboard(genDesc, genName), project);

        response.assertThat().statusCode(SC_CREATED).body("id", notNullValue());

        dashId = clientAuth.getDashId(response);
        clientAuth.checkDashboard(dashId, project).statusCode(SC_OK);
    }

    @Test
    @DisplayName("POST /api/v1/default_personal/dashboard не заполнено обязательное поле name")
    @Description("400 при попытке создания dashboard, не заполнено обязательное поле name")
    public void createDashWithoutNameBadRequestTest() {
        response = clientAuth.createDashboard(new Dashboard(genDesc, null), project);
        response.assertThat().statusCode(SC_BAD_REQUEST);

        String desc = clientAuth
                .checkAllDashboards(project)
                .extract()
                .body()
                .as(Dashboard.class)
                .getDescription();
        assertNotEquals(desc, genDesc);
    }

    @Test
    @DisplayName("POST /api/v1/dashboard обязательный параметр в URL null")
    @Description("403 при попытке создания dashboard, " +
            "если не передать обязательный параметр имя проекта в URL")
    public void createDashWithoutProjectNameInUrl403Test() {
        response = clientAuth.createDashboard(new Dashboard(genDesc, genName), "null");
        response.assertThat().statusCode(SC_FORBIDDEN);

        String desc = clientAuth
                .checkAllDashboards(project)
                .extract()
                .body()
                .as(Dashboard.class)
                .getDescription();
        assertNotEquals(desc, genDesc);
    }

    @AfterEach
    @DisplayName("DELETE /api/v1/default_personal/dashboard, apiKey и закрытие браузера")
    @Description("Удаление dashboard, apiKey после каждого теста и закрытие браузера")
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
