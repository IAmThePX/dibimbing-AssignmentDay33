package tests.auth;

import body.auth.LoginBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileWriter;
import java.io.IOException;

public class LoginTest {

    @Test
    public void loginTest() throws IOException {

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        LoginBody loginBody = new LoginBody();

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(loginBody.loginData().toString())
                .when()
                .post("/login")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());

        // validation status
        Assert.assertEquals(response.getStatusCode(), 200);

        // validation message (pakai titik di akhir)
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "User login successfully.", "Message does not match");

        // validation token
        String token = response.jsonPath().getString("data.token");
        Assert.assertNotNull(token, "Token should not be null");
        System.out.println("Token: " + token);

        // put token into json folder
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token", token);

        try (FileWriter file = new FileWriter("src/main/resources/json/token.json")) {
            file.write(tokenJson.toString(4)); // indentation
            file.flush();
        }

        System.out.println("Token berhasil disimpan di src/main/resources/json/token.json");
    }
}