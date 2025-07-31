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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Регистрация")
public class RegisterTests extends TestBase {

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешная регистрация")
    void successfulRegisterTest() {
        LoginBodyModel authData = new LoginBodyModel("eve.holt@reqres.in", "cityslicka");

        LoginResponseModel responce = step("Регистрация нового пользователя", ()->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responceSpec(200))
                        .extract().as(LoginResponseModel.class));

        step("Check token", ()->
                assertEquals("QpwL5tke4Pnpja7X4", responce.getToken()));

        step("Check ID", () ->
                assertThat(responce.getId(),not(emptyOrNullString())));
    }
}
