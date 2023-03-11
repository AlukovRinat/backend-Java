package restAssured4;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lesson4.Response;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restAssured4.class1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class class2 extends class1 {
    @Test
    void getRecipePositiveTest() {
        given().spec(getRequestSpecification())
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .spec(responseSpecification);
    }


    @Test
    void getAccountInfoWithExternalEndpointTest(){
        Response response = given().spec(requestSpecification)
                .when()
                .formParam("title","Burger")
                .post("https://api.spoonacular.com/recipes/cuisine").prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(Response.class);
        assertThat(response.getCuisine(), containsString("American"));
    }

    @Test
    void test(){
        given().spec(requestSpecification)
                .when()
                .formParam("title","Burger")
                .formParam("language", "en")
                .post("https://api.spoonacular.com/recipes/cuisine").prettyPeek()
                .then()
                .statusCode(200);
    }

}
