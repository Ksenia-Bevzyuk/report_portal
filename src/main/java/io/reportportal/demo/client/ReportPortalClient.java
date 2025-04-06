package io.reportportal.demo.client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.reportportal.demo.model.Dashboard;
import io.reportportal.demo.model.ListOfUserApiKey;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class ReportPortalClient {
    private RequestSpecification requestSpec;

    public ReportPortalClient(String apiKey) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri("https://demo.reportportal.io")
                .setContentType("application/json")
                .addHeader("Authorization", apiKey);

        requestSpec = builder.build();
    }

    @Step("Получение информации о конкретном пользователе")
    public ValidatableResponse getUserInfo() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .get(EndPoints.GET_USER_INFO)
                .then()
                .log()
                .all();
    }

    @Step ("Получение userId")
    public int getUserId(ValidatableResponse response) {
        return response.extract().jsonPath().getInt("id");
    }

    @Step("Получение api key конкретного пользователя")
    public ValidatableResponse getUserApiKey(int userId) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .get(String.format(EndPoints.GET_USER_API_KEY, userId))
                .then()
                .log()
                .all();
    }

    @Step ("Получение API Id")
    public int getApiId(ValidatableResponse response, String nameApiKey) {
        ListOfUserApiKey list = response.extract().as(ListOfUserApiKey.class);
        for(int i = 0; i < list.getItems().size(); i++) {
            if(list.getItems().get(i).getName().equals(nameApiKey)) {
                return list.getItems().get(i).getId();
            }else{
                i++;
            }
        }
        return 0;
    }

    @Step("Удаление apiKey")
    public ValidatableResponse deleteApiKey(int userId, int keyId) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .delete(String.format(EndPoints.DEL_API_KEY, userId) + keyId)
                .then()
                .log()
                .all();
    }

    @Step("Создание dashboard")
    public ValidatableResponse createDashboard(Dashboard dash, String project) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .body(dash)
                .post(String.format(EndPoints.CREATE_DASHBOARD, project.toLowerCase()))
                .then()
                .log()
                .all();
    }

    @Step ("Получение dashId")
    public int getDashId(ValidatableResponse response) {
        return response.extract().jsonPath().getInt("id");
    }

    @Step("Подтверждение существования созданного dashboard")
    public ValidatableResponse checkDashboard(int dashId, String project) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .get(String.format(EndPoints.CREATE_DASHBOARD, project.toLowerCase()) + dashId)
                .then()
                .log()
                .all();
    }

    @Step("Удаление dashboard")
    public ValidatableResponse daleteDashboard(int dashId, String project) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .delete(String.format(EndPoints.CREATE_DASHBOARD, project.toLowerCase()) + dashId)
                .then()
                .log()
                .all();
    }

    @Step("Получить список всех dashboards")
    public ValidatableResponse checkAllDashboards(String project) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .get(String.format(EndPoints.CREATE_DASHBOARD, project.toLowerCase()))
                .then()
                .log()
                .all();
    }
}
