package tests.rest;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class RestCrudTest {

    static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

    @Test
    public void createPostTest() {
        String json = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(BASE_URL)
                .then()
                .extract().response();

        assertEquals(response.statusCode(), 201);
        assertEquals(response.jsonPath().getString("title"), "foo");
    }

    @Test
    public void getPostTest() {
        Response response = get(BASE_URL + "/1");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.jsonPath().getInt("id"), 1);
    }

    @Test
    public void updatePostTest() {
        String updated = "{ \"id\": 1, \"title\": \"updated\", \"body\": \"bar\", \"userId\": 1 }";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(updated)
                .when()
                .put(BASE_URL + "/1")
                .then()
                .extract().response();

        assertEquals(response.statusCode(), 200);
        assertEquals(response.jsonPath().getString("title"), "updated");
    }

    @Test
    public void deletePostTest() {
        Response response = delete(BASE_URL + "/1");
        assertEquals(response.statusCode(), 200);
    }
}
