package in.reqres.tests;

import in.reqres.models.LoginBodyModel;
import in.reqres.models.LoginErrorResponseModel;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.LoginSpec.requestSpec;
import static in.reqres.specs.LoginSpec.responceSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Негативные проверки с пользователем")
public class NegativeTests extends TestBase {

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Пользователь не найден (неверный email)")
    void userNotFoundTest() {
        LoginBodyModel authData = new LoginBodyModel("eve.holt55689@reqres.in", "cityslicka");

        LoginErrorResponseModel responce = step("Пользователь не найден", ()->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responceSpec(400))
                        .extract().as(LoginErrorResponseModel.class));

        step("Проверка сообщения ошибки", () ->
                assertEquals("user not found", responce.getError()));
    }

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Конкретный пользователь не найден")
    void singleUserNotFoundTest() {
        step("Конкретный пользователь не найден", ()->
                given(requestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(responceSpec(404)));

    }
}
