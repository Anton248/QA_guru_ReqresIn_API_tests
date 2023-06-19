package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;
import static specs.BaseSpecs.baseRequestSpec;
import static specs.BaseSpecs.baseResponseSpec;

public class UsersSpec {

    public static final String REGISTER_PATH = "/register";
    public static final String CREATE_USER_PATH = "/users";
    public static final String UPDATE_USER_PATH = "/users/2";
    public static final String GET_USERS_LIST_PATH = "/users?page=2";

    // Registration

    public static RequestSpecification registrationRequestSpec = with()
            .spec(baseRequestSpec);

    public static ResponseSpecification registrationResponseSuccessSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody("token", notNullValue())
            .build();

    public static ResponseSpecification registrationResponseErrorSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(400)
            .build();

    // Create user

    public static RequestSpecification createUserRequestSpec = with()
            .spec(baseRequestSpec);

    public static ResponseSpecification createUserResponseSuccessSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath("schemes/create-user-response-scheme.json"))
            .build();

    // Update user

    public static RequestSpecification updateUserRequestSpec = with()
            .spec(baseRequestSpec);

    public static ResponseSpecification updateUserResponseSuccessSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemes/update-user-response-scheme.json"))
            .build();

    public static ResponseSpecification updateUserOnlyJobResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .build();

    // Get user's list

    public static ResponseSpecification getUsersListResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemes/get-users-list-response-scheme.json"))
            .build();

}
