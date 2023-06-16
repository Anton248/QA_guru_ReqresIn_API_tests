package tests;

import io.restassured.RestAssured;
import models.RegistrationRequestBody;
import models.RegistrationResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Testing the registration of new user on https://reqres.in/
public class RegisterTests {

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Registering via email & password")
    void successfulRegistrationTest() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("pistol");

        RegistrationResponseBody responseBody =
         given().
                log().uri().
                log().body().
                contentType(JSON).    // = header("content-type", JSON).
                body(request).
         when().
                post("/register").
         then().
                log().status().
                log().body().
                statusCode(200).
                extract().as(RegistrationResponseBody.class);

        assertTrue(responseBody.getToken().length()>10);


    }

    @Test
    @Tag("medium") @Tag("extended") @Tag("negative")
    @DisplayName("Registering with empty password")
    void registrationWithEmptyPassword() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("");

        RegistrationResponseBody responseBody =
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
                 extract().as(RegistrationResponseBody.class);

        assertEquals("Missing password", responseBody.getError());
    }

    @Test
    @Tag("critical") @Tag("blocker") @Tag("negative")
    @DisplayName("Registering without password")
    void registrationWithoutPassword() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");

        RegistrationResponseBody responseBody =
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
                 extract().as(RegistrationResponseBody.class);

        assertEquals("Missing password", responseBody.getError());
    }
}


