package auth;

import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.*;

public class Auth {

    @Test
    public void basic_digest_auth() {
        given().baseUri("https://postman-echo.com").auth().basic("postman", "password").when().get("/digest-auth").then().log().all().statusCode(200);

//        if you want extract the cookie from the response headers use the following code
//        Map<String,String> response_cookies =  given()
//                .baseUri("https://postman-echo.com")
//                .auth().basic("postman","password")
//                .when()
//                .get("/digest-auth")
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract().cookies();
//        System.out.println(response_cookies);
    }


    @Test
    public void tweetFromAPI() {
        String tweet = "This is a tweet from the server and will be sent to the server in the future";
        given().baseUri("https://api.twitter.com/2/").auth().oauth("API_KEY", "API Secret", "Access Token", "Access Token Secret").param("status", tweet).when().post("statuses/update.json").then().log().all().statusCode(200);
    }

}
