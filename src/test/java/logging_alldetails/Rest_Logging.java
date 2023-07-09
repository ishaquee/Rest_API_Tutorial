package logging_alldetails;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Rest_Logging {

    @Test
    public void logging_All_Details(){
        given().baseUri("https://restcountries.com/v3.1/")
                .when()
                .get("capital/tallinn")
                .then()
                .log().all();
    }

    @Test
    public void logging_only_body()
    {
        given().baseUri("https://restcountries.com/v3.1/")
                .when()
                .get("region/europe")
                .then()
                .log().headers();
    }

    @Test
    public void logging_cookies()
    {
        given().baseUri("https://restcountries.com/v3.1/")
                .when()
                .get("translation/alemania")
                .then()
                .log().cookies();
    }

    @Test
    public void log_if_error()
    {
        given().baseUri("https://restcountries.com/v3.1/")
                .when()
                .get("translation/alemanvcxia")
                .then()
                //.log().ifError();
                .log().ifStatusCodeIsEqualTo(404);
    }

    @Test
    public void log_if_validation_fails()
    {
        given().baseUri("https://restcountries.com/v3.1/")
                .when()
                .get("translation/alemanvcxia")
                .then()
                .log().ifValidationFails()
                .statusCode(404);
    }

}
