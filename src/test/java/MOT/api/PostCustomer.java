package MOT.api;

import listeners.ApiPoJoFile.NormalCustomer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostCustomer {

    public static Response createAStudent(NormalCustomer normalCustomer) {
        return given()
               .contentType(ContentType.JSON)
               .header("X-Rootvia-Client-Id", "sys_admin")
               .when()
               .body(normalCustomer)
               .post("http://10.20.22.52:8080/api/1/auth/token/issue");
    }
}
