package tests;

import models.*;
import org.junit.jupiter.api.*;

import java.util.Calendar;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.UsersSpec.*;

//Testing API https://reqres.in/
public class UsersTests {

    // Register user

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Register user via email & password")
    void RegisterUserTest() {

        RegisterUserRequestBody request = new RegisterUserRequestBody();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("pistol");

        RegisterUserResponseBody responseBody =
        step("Make request and receive response", ()->
           given(registrationRequestSpec)   //  = .spec(registrationRequestSpec)
                .body(request)
           .when()
                .post(REGISTER_PATH)
           .then()
                .spec(registrationResponseSuccessSpec)
                .extract().as(RegisterUserResponseBody.class));

        step("Check response", () ->
            assertTrue(responseBody.getToken().length()>8));
    }

    @Test
    @Tag("medium") @Tag("extended") @Tag("negative")
    @DisplayName("Register user with empty password")
    void registerUserWithEmptyPassword() {

        RegisterUserRequestBody request = new RegisterUserRequestBody();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("");

        RegisterUserResponseBody responseBody = step("Make request and receive response", ()->
            given(registrationRequestSpec)   //  = .spec(registrationRequestSpec).
                 .body(request)
            .when()
                 .post(REGISTER_PATH)
            .then()
                 .spec(registrationResponseErrorSpec)
                 .extract().as(RegisterUserResponseBody.class));

        step("Check response", () ->
            assertEquals("Missing password", responseBody.getError()));
    }

    @Test
    @Tag("critical") @Tag("blocker") @Tag("negative")
    @DisplayName("Register user without password")
    void registerUserWithoutPassword() {

        RegisterUserRequestBody request = new RegisterUserRequestBody();
        request.setEmail("eve.holt@reqres.in");

        RegisterUserResponseBody responseBody = step("Make request and receive response", ()->
             given(registrationRequestSpec)   //  = .spec(registrationRequestSpec).
                  .body(request)
             .when()
                   .post(REGISTER_PATH)
             .then()
                   .spec(registrationResponseErrorSpec)
                   .extract().as(RegisterUserResponseBody.class));

        step("Check response", () ->
            assertEquals("Missing password", responseBody.getError()));
    }

    // Create user
    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Create user")
    void createUserTest() {

        UserRequestBody request = new UserRequestBody();
        String name = "morpheus";
        String job = "leader";

        request.setName(name);
        request.setJob(job);

        String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";

        CreateUserResponseBody responseBody = step("Make request and receive response", ()->
             given(createUserRequestSpec) //  = .spec(createUserRequestSpec)
                   .body(request)
             .when()
                   .post(CREATE_USER_PATH)
             .then()
                   .spec(createUserResponseSuccessSpec)
                   .extract().as(CreateUserResponseBody.class));

        step("Check response", () -> {
            assertEquals(name, responseBody.getName());
            assertEquals(job, responseBody.getJob());
            assertTrue(responseBody.getId().matches("[0-9]+"));
            assertTrue(responseBody.getCreatedAt().contains(currentYear));
        });
    }

    // Update user

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Update user via put")
    void updateUserTest() {

        UserRequestBody request = new UserRequestBody();
        String name = "morpheus";
        String job = "zion resident";
        String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
        request.setName(name);
        request.setJob(job);

        UpdateUserResponseBody responseBody = step("Make request and receive response", ()->
             given(updateUserRequestSpec) //  = .spec(createUserRequestSpec)
                   .body(request)
             .when()
                   .put(UPDATE_USER_PATH)
             .then()
                   .spec(updateUserResponseSuccessSpec)
                   .extract().as(UpdateUserResponseBody.class));

        step("Check response", () -> {
            assertEquals(name, responseBody.getName());
            assertEquals(job, responseBody.getJob());
            assertTrue(responseBody.getUpdatedAt().contains(currentYear));
        });
    }

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Updating user via patch")
    void updateUserViaPatchTest() {

        UserRequestBody request = new UserRequestBody();
        String name = "morpheus";
        String job = "zion resident";
        String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
        request.setName(name);
        request.setJob(job);

        UpdateUserResponseBody responseBody = step("Make request and receive response", ()->
             given(updateUserRequestSpec) //  = .spec(createUserRequestSpec)
                   .body(request)
             .when()
                   .patch(UPDATE_USER_PATH)
             .then()
                   .spec(updateUserResponseSuccessSpec)
                   .extract().as(UpdateUserResponseBody.class));

        step("Check response", () -> {
            assertEquals(name, responseBody.getName());
            assertEquals(job, responseBody.getJob());
            assertTrue(responseBody.getUpdatedAt().contains(currentYear));
        });
    }

    @Test
    @Tag("smoke") @Tag("blocker") @Tag("positive")
    @DisplayName("Update empty user")
    void updateEmptyUserTest() {

        UserRequestBody request = new UserRequestBody();
        String job = "zion resident";
        request.setJob(job);

        UpdateUserResponseBody responseBody = step("Make request and receive response", ()->
             given(updateUserRequestSpec) //  = .spec(createUserRequestSpec)
                   .body(request)
             .when()
                   .patch(UPDATE_USER_PATH)
             .then()
                   .spec(updateEmptyUserResponseSpec)
                   .extract().as(UpdateUserResponseBody.class));

        step("Check response", () ->
                assertNull(responseBody.getName()));

    }





}

