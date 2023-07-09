package schema_validation;

import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

public class SchemaValidator {

    @Test
    public void validateJSONSchema() throws Exception {
        File fs = new File("schema_nba.json");

        given().baseUri("https://free-nba.p.rapidapi.com/")
                .header("X-RapidAPI-Key","YOUR_API_KEY")
                .header("X-RapidAPI-Host","free-nba.p.rapidapi.com")
                .when()
                .get("players/11")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/resources/schema_nba.json")));
    }

    @Test
    public void validateXMLSchema() throws Exception {
        File fs = new File("src/main/resources/schema/");

        given().baseUri("https://routing-number-bank-lookup.p.rapidapi.com/api")
                .header("X-RapidAPI-Key","YOUR_API_KEY")
                .header("X-RapidAPI-Host","routing-number-bank-lookup.p.rapidapi.com")
                .queryParam("format","xml")
                .when()
                .get("/v1/111913060")
                .then()
                .body(matchesXsdInClasspath("bank_details.xsd"))
                .statusCode(200);
    }
}
