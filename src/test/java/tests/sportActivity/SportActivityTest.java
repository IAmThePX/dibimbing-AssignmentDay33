package tests.sportActivity;

import body.sportActivity.CreateSportActivityBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SportActivityTest {

    private String token;

    @BeforeClass
    public void setup() throws Exception {
        // Set base URI
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file token.json
        FileReader reader = new FileReader("src/main/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();
    }

    @Test
    public void sportActivityTestValid() throws IOException {

        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        CreateSportActivityBody createSportActivityBody = new CreateSportActivityBody();

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(createSportActivityBody.getBody("76", "17").toString())
                .when()
                .post("/sport-activities/create")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());

        // validation status code
        Assert.assertEquals(response.getStatusCode(), 200);
        // validation message
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "data saved", "Message does not match");

        // Save activity id
        String activity_id = response.jsonPath().getString("result.id");
        System.out.println("Activity ID: " + activity_id);

        JSONObject tokenJson = new JSONObject();
        tokenJson.put("activity_id", activity_id);

        try (FileWriter file = new FileWriter("src/main/resources/json/activity_id.json")) {
            file.write(tokenJson.toString(4)); // 4 = indentation
            file.flush();
        }
    }
    @Test
    public void sportActivityTestInvalidCategoryId() throws IOException {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        CreateSportActivityBody createSportActivityBody = new CreateSportActivityBody();

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(createSportActivityBody.getBody("", "17").toString())
                .when()
                .post("/sport-activities/create")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());

        // validation status code
        Assert.assertEquals(response.getStatusCode(), 406);
        // validation message
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "The sport category id field is required.", "Message does not match");
    }

    @Test
    public void sportActivityTestInvalidPrice() throws IOException {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        CreateSportActivityBody createSportActivityBody = new CreateSportActivityBody();

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(createSportActivityBody.getBody("78", "").toString())
                .when()
                .post("/sport-activities/create")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());

        // validation status code
        Assert.assertEquals(response.getStatusCode(), 406);
        // validation message
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "The price field is required.", "Message does not match");
    }
}