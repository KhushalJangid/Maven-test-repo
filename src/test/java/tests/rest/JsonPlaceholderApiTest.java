package tests.rest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class JsonPlaceholderApiTest {

    static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void getAllPosts() {
        Response res = get(BASE_URL + "/posts");
        assertEquals(res.statusCode(), 200);
        assertTrue(res.jsonPath().getList("$").size() > 0, "Post list should not be empty");
    }

    @Test
    public void getPostById() {
        Response res = get(BASE_URL + "/posts/1");
        assertEquals(res.statusCode(), 200);
        assertEquals(res.jsonPath().getInt("id"), 1);
    }

    @Test
    public void getPostsByUserId() {
        Response res = given()
                .queryParam("userId", 1)
                .when()
                .get(BASE_URL + "/posts");

        assertEquals(res.statusCode(), 200);
        res.jsonPath().getList("userId").forEach(uid -> assertEquals(uid, 1));
    }

    @Test
    public void validateResponseHeaders() {
        Response res = get(BASE_URL + "/posts/1");
        assertEquals(res.getHeader("Content-Type"), "application/json; charset=utf-8");
    }

    @Test
    public void validateResponseTime() {
        long time = get(BASE_URL + "/posts").time();
        assertTrue(time < 1000, "Response time is too high: " + time + "ms");
    }

    @Test
    public void createPost() {
        String json = "{ \"title\": \"test title\", \"body\": \"test body\", \"userId\": 1 }";

        Response res = given()
                .contentType(ContentType.JSON)
                .body(json)
                .post(BASE_URL + "/posts");

        assertEquals(res.statusCode(), 201);
        assertEquals(res.jsonPath().getString("title"), "test title");
    }

    @Test
    public void updatePost() {
        String updatedJson = "{ \"id\": 1, \"title\": \"updated title\", \"body\": \"updated body\", \"userId\": 1 }";

        Response res = given()
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .put(BASE_URL + "/posts/1");

        assertEquals(res.statusCode(), 200);
        assertEquals(res.jsonPath().getString("title"), "updated title");
    }

    @Test
    public void deletePost() {
        Response res = delete(BASE_URL + "/posts/1");
        assertEquals(res.statusCode(), 200); // still returns 200 in sandbox mode
    }
}
