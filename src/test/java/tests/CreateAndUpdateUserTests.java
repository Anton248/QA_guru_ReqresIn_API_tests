package tests;

import io.restassured.RestAssured;
import models.UserRequestBody;
import models.UserCreateResponseBody;
import models.UserUpdateResponseBody;
import org.junit.jupiter.api.*;

import java.util.Calendar;

import static helpers.AllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

//Testing creation and updating user on https://reqres.in/
public class CreateAndUpdateUserTests {

    @BeforeAll
    static void init(){
        RestAssured.filters(withCustomTemplates());
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

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
        step("Make request and receive response", ()->
             given().

                 body(request).
                 //filter(new AllureRestAssured()).
             when().
                 post("/users").
             then().
                 log().status().
                 log().body().
                 statusCode(201).
                 body(matchesJsonSchemaInClasspath("schemes/create-user-response-scheme.json")).
                 extract().as(UserCreateResponseBody.class));

        step("Check response", () -> {
            assertEquals(name, responseBody.getName());
            assertEquals(job, responseBody.getJob());
            assertTrue(responseBody.getId().matches("[0-9]+"));
            assertTrue(responseBody.getCreatedAt().contains(currentYear));
        });
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
        step("Make request and receive response", ()->
            given().
                 log().uri().
                 log().body().
                 contentType(JSON).    // = header("content-type", JSON).
                 body(request).
            when().
                 put("/users/2").
            then().
                 log().status().
                 log().body().
                 statusCode(200).
                 extract().as(UserUpdateResponseBody.class));

        step("Check response", () -> {
            assertEquals(name, responseBody.getName());
            assertEquals(job, responseBody.getJob());
            assertTrue(responseBody.getUpdatedAt().contains(currentYear));
        });
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
        step("Make request and receive response", ()->
            given().
                 log().uri().
                 log().body().
                 contentType(JSON).    // = header("content-type", JSON).
                 body(request).
            when().
                 patch("/users/2").
            then().
                 log().status().
                 log().body().
                 statusCode(200).
                 extract().as(UserUpdateResponseBody.class));

        step("Check response", () -> {
            assertEquals(name, responseBody.getName());
            assertEquals(job, responseBody.getJob());
            assertTrue(responseBody.getUpdatedAt().contains(currentYear));
        });
    }

    @Test
    @Tag("critical") @Tag("negative")
    @DisplayName("Updating empty user")
    void updateEmptyUserTest() {

        UserRequestBody request = new UserRequestBody();
        String job = "zion resident";
        request.setJob(job);

        UserUpdateResponseBody responseBody =
        step("Make request and receive response", ()->
            given().
                 log().uri().
                 log().body().
                 contentType(JSON).    // = header("content-type", JSON).
                 body(request).
            when().
                 put("/users/2").
            then().
                 log().status().
                 log().body().
                 statusCode(200).
                 extract().as(UserUpdateResponseBody.class));

        step("Check response", () ->
            assertNull(responseBody.getName()));
    }

}
