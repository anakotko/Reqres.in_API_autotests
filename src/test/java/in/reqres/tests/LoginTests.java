package in.reqres.tests;

import in.reqres.models.LoginBodyModel;
import in.reqres.models.LoginResponseModel;
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

@Feature("Вход в систему")
public class LoginTests extends TestBase {

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешный вход в систему")
    void successfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel("eve.holt@reqres.in", "cityslicka");

        LoginResponseModel responce = step("Вход в систему", ()->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responceSpec(200))
                        .extract().as(LoginResponseModel.class));

        step("Check token", ()->
                assertEquals("QpwL5tke4Pnpja7X4", responce.getToken()));
    }
}
