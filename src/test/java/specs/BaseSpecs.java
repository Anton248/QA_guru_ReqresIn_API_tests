package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.AllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class BaseSpecs {
    public static String BASE_URI = "https://reqres.in";
    public static String BASE_PATH = "/api";

    public static RequestSpecification baseRequestSpec = with()
            .baseUri(BASE_URI)
            .basePath(BASE_PATH)
            .contentType(JSON)  // = .header("content-type", JSON)
            .filter(withCustomTemplates())
            .log().uri()
            .log().body();

    public static ResponseSpecification baseResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
