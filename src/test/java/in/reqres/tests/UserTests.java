package in.reqres.tests;

import in.reqres.models.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static in.reqres.specs.LoginSpec.requestSpec;
import static in.reqres.specs.LoginSpec.responceSpec;

@Feature("Действия с пользователем, создание/изменение/удаление")
public class UserTests extends TestBase {

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешное создание нового пользователя")
    void createUserTest() {
        UserBodyModel userData = new UserBodyModel("morpheus", "leader");

        UserResponceModel responce = step("Создание пользователя", ()->
        given(requestSpec)
                .body(userData)
                .when()
                .post("/users")
                .then()
                .spec(responceSpec(201))
                .extract().as(UserResponceModel.class));

        step("Проверка name", ()->
                assertEquals("morpheus", responce.getName()));

        step("Проверка job", ()->
                assertEquals("leader", responce.getJob()));

        step("Проверка ID", () ->
                assertThat(responce.getId(),not(emptyOrNullString())));

        step("Проверка createdAt", () ->
                assertThat(responce.getCreatedAt(),not(emptyOrNullString())));
    }


    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешное получение данных конкретного пользователя")
    void singleUserTest() {
        SingleUserResponseModel responce = step("Получение данных конкретного пользователя", ()->
        given(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responceSpec(200))
                .extract().as(SingleUserResponseModel.class));

        step("Проверка ID", ()->
        assertEquals(2, responce.getData().getId()));

        step("Проверка name", ()->
        assertEquals("Janet", responce.getData().getFirstName()));

    }

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешное удаление пользователя")
    void deleteUserTest() {
        step("Удаление пользователя", ()->
        given(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(responceSpec(204)));
    }

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Полное обновление пользователя (PUT)")
    void updateInfoPutTest() {
        UserBodyModel userPutData = new UserBodyModel("morpheus", "zion resident");

        UserResponceModel responce = step("Обновление информации пользователя, PUT", ()->
        given(requestSpec)
                .body(userPutData)
                .when()
                .put("/users/2")
                .then()
                .spec(responceSpec(200))
                .extract().as(UserResponceModel.class));

        step("Проверка name", ()->
                assertEquals("morpheus", responce.getName()));

        step("Проверка job", ()->
                assertEquals("zion resident", responce.getJob()));

        step("Проверка updatedAt", () ->
                assertThat(responce.getUpdatedAt(),not(emptyOrNullString())));
    }

    @Test
    @Owner("anakotko")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Частичное обновление пользователя (PATCH)")
    void updateInfoPatchTest() {
        UserBodyModel userPatchData = new UserBodyModel("morpheus", "zion resident");

        UserResponceModel responce = step("Обновление информации пользователя, Patch", ()->
        given(requestSpec)
                .body(userPatchData)
                .when()
                .patch("/users/2")
                .then()
                .spec(responceSpec(200))
                .extract().as(UserResponceModel.class));

        step("Проверка name", ()->
                assertEquals("morpheus", responce.getName()));

        step("Проверка job", ()->
                assertEquals("zion resident", responce.getJob()));

        step("Проверка updatedAt", () ->
                assertThat(responce.getUpdatedAt(),not(emptyOrNullString())));
    }
}
