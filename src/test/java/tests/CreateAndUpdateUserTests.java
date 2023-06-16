package tests;

import models.UserRequestBody;
import models.UserCreateResponseBody;
import models.UserUpdateResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

//Testing creation and updating user on https://reqres.in/
public class CreateAndUpdateUserTests {

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Creating a user")
    void successfulCreateUserTest() {

        UserRequestBody request = new UserRequestBody();
        String name = "morpheus";
        String job = "leader";
        String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
        request.setName(name);
        request.setJob(job);

        UserCreateResponseBody responseBody =
         given().
                 log().uri().
                 log().body().
                 contentType(JSON).    // = header("content-type", JSON).
                 body(request).
          when().
                 post("https://reqres.in/api/users").
          then().
                 log().status().
                 log().body().
                 statusCode(201).
                 body(matchesJsonSchemaInClasspath("schemes/create-user-response-scheme.json")).
                 extract().as(UserCreateResponseBody.class);

        assertEquals(name, responseBody.getName());
        assertEquals(job, responseBody.getJob());
        assertTrue(responseBody.getId().matches("[0-9]+"));
        assertTrue(responseBody.getCreatedAt().contains(currentYear));
    }

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Updating user via put")
    void successfulUpdateUserTest() {

        UserRequestBody request = new UserRequestBody();
        String name = "morpheus";
        String job = "zion resident";
        String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
        request.setName(name);
        request.setJob(job);

        UserUpdateResponseBody responseBody =
         given().
                 log().uri().
                 log().body().
                 contentType(JSON).    // = header("content-type", JSON).
                 body(request).
         when().
                 put("https://reqres.in/api/users/2").
         then().
                 log().status().
                 log().body().
                 statusCode(200).
                 extract().as(UserUpdateResponseBody.class);

        assertEquals(name, responseBody.getName());
        assertEquals(job, responseBody.getJob());
        assertTrue(responseBody.getUpdatedAt().contains(currentYear));
    }

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Updating user via patch")
    void successfulUpdateUserViaPatchTest() {

        UserRequestBody request = new UserRequestBody();
        String name = "morpheus";
        String job = "zion resident";
        String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
        request.setName(name);
        request.setJob(job);

        UserUpdateResponseBody responseBody =
         given().
                 log().uri().
                 log().body().
                 contentType(JSON).    // = header("content-type", JSON).
                 body(request).
          when().
                 patch("https://reqres.in/api/users/2").
          then().
                 log().status().
                 log().body().
                 statusCode(200).
                 extract().as(UserUpdateResponseBody.class);

        assertEquals(name, responseBody.getName());
        assertEquals(job, responseBody.getJob());
        assertTrue(responseBody.getUpdatedAt().contains(currentYear));
    }

    @Test
    @Tag("critical") @Tag("negative")
    @DisplayName("Updating empty user")
    void updateEmptyUserTest() {

        UserRequestBody request = new UserRequestBody();
        String job = "zion resident";
        request.setJob(job);

        UserUpdateResponseBody responseBody =
         given().
                 log().uri().
                 log().body().
                 contentType(JSON).    // = header("content-type", JSON).
                 body(request).
          when().
                 put("https://reqres.in/api/users/2").
          then().
                 log().status().
                 log().body().
                 statusCode(200).
                 extract().as(UserUpdateResponseBody.class);

        assertNull(responseBody.getName());
    }

}
