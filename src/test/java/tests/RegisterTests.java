package tests;

import io.restassured.RestAssured;
import models.RegistrationRequestBody;
import models.RegistrationResponseBody;
import org.junit.jupiter.api.*;

import static helpers.AllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specs.CreateAndUpdateUserSpec.registrationRequestSpec;
import static specs.CreateAndUpdateUserSpec.registrationResponseSpec;

//Testing the registration of new user on https://reqres.in/
public class RegisterTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(withCustomTemplates());
    }

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Registering via email & password")
    void successfulRegistrationTest() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("pistol");

        RegistrationResponseBody responseBody =
        step("Make request and receive response", ()->
           given(registrationRequestSpec)
                //.spec(registrationRequestSpec)
                .body(request)
           .when()
                .post("/register")
           .then()
                .spec(registrationResponseSpec)
                .extract().as(RegistrationResponseBody.class));

        step("Check response", () ->
            assertTrue(responseBody.getToken().length()>8));
    }

    @Test
    @Tag("medium") @Tag("extended") @Tag("negative")
    @DisplayName("Registering with empty password")
    void registrationWithEmptyPassword() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("");

        RegistrationResponseBody responseBody = step("Make request and receive response", ()->
            given().
                 log().uri().
                 log().body().
                 contentType(JSON).
                 body(request).
            when().
                 post("/register").
            then().
                 log().status().
                 log().body().
                 statusCode(400).
                 extract().as(RegistrationResponseBody.class));

        step("Check response", () ->
            assertEquals("Missing password", responseBody.getError()));
    }

    @Test
    @Tag("critical") @Tag("blocker") @Tag("negative")
    @DisplayName("Registering without password")
    void registrationWithoutPassword() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");

        RegistrationResponseBody responseBody = step("Make request and receive response", ()->
            given().
                 log().uri().
                 log().body().
                 contentType(JSON).
                 body(request).
            when().
                 post("/register").
            then().
                 log().status().
                 log().body().
                 statusCode(400).
                 extract().as(RegistrationResponseBody.class));

        step("Check response", () ->
            assertEquals("Missing password", responseBody.getError()));
    }
}


