package tests.sportActivity;

import body.sportActivity.CreateSportActivityBody;
import body.sportActivity.UpdateSportActivityBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

public class UpdateSportActivityTest {

    private String token;

    @Test
    public void updateSportActivityTestValid() throws IOException {

        // Set base URI
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file token.json
        FileReader reader = new FileReader("src/main/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();

        // Ambil activity_id dari file activity_id.json
        String activityFile = "src/main/resources/json/activity_id.json";
        String activityContent = new String(readAllBytes(Paths.get(activityFile)));
        JSONObject activityJson = new JSONObject(activityContent);
        int activityId = activityJson.getInt("activity_id");

        // Baca body dari file JSON
        UpdateSportActivityBody bodyHelper = new UpdateSportActivityBody();
        JSONObject requestBody = bodyHelper.getBodyFromFile("src/main/resources/json/update_activity.json", Utils.getDateAfterFourDays());
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody.toString())
                .when()
                .post("/sport-activities/update/" + activityId)
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());

        // validation status code
        Assert.assertEquals(response.getStatusCode(), 200);
        // validation message
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "data saved", "Message does not match");
    }

    @Test
    public void updateSportActivityTestInvalidActivityDate() throws IOException {

        // Set base URI
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file token.json
        FileReader reader = new FileReader("src/main/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();

        // Ambil activity_id dari file activity_id.json
        String activityFile = "src/main/resources/json/activity_id.json";
        String activityContent = new String(readAllBytes(Paths.get(activityFile)));
        JSONObject activityJson = new JSONObject(activityContent);
        int activityId = activityJson.getInt("activity_id");

        // Baca body dari file JSON
        UpdateSportActivityBody bodyHelper = new UpdateSportActivityBody();
        JSONObject requestBody = bodyHelper.getBodyFromFile("src/main/resources/json/update_activity.json", "");
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody.toString())
                .when()
                .post("/sport-activities/update/" + activityId)
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());

        // validation status code
        Assert.assertEquals(response.getStatusCode(), 406);
        // validation message
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "The activity date field is required.", "Message does not match");
    }
}