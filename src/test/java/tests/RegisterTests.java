package tests;

import models.RegistrationRequestBody;
import models.RegistrationResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Testing the registration of new user on https://reqres.in/
public class RegisterTests {

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Registering via email & password")
    void SuccessfulRegistrationTest() {

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
                post("https://reqres.in/api/register").
         then().
                log().status().
                log().body().
                statusCode(200).
                extract().as(RegistrationResponseBody.class);

        assertTrue(responseBody.getToken().length()>10);


    }

    @Test
    @Tag("medium") @Tag("extended") @Tag("negative")
    @DisplayName("Registering with too long password")
    void RegistrationWithWrongPassword() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 2000; i++) {   //creating too long password
            password.append(i);
        }
        request.setPassword(password.toString());

        RegistrationResponseBody responseBody =
         given().
                 log().uri().
                 log().body().
                 contentType(JSON).
                 body(request).
         when().
                 post("https://reqres.in/api/register").
         then().
                 log().status().
                 log().body().
                 statusCode(400).       //'bug' is here
                 extract().as(RegistrationResponseBody.class);

        assertEquals("Too long password", responseBody.getError());
    }

    @Test
    @Tag("critical") @Tag("blocker") @Tag("negative")
    @DisplayName("Registering without password")
    void RegistrationWithoutPassword() {

        RegistrationRequestBody request = new RegistrationRequestBody();
        request.setEmail("eve.holt@reqres.in");

        RegistrationResponseBody responseBody =
         given().
                 log().uri().
                 log().body().
                 contentType(JSON).
                 body(request).
         when().
                 post("https://reqres.in/api/register").
         then().
                 log().status().
                 log().body().
                 statusCode(400).
                 extract().as(RegistrationResponseBody.class);

        assertEquals("Missing password", responseBody.getError());
    }
}


