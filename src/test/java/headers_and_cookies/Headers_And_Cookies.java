package headers_and_cookies;

import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Headers_And_Cookies {


    @Test
    public void sending_request_headers()
    {
     given()
             .baseUri("http://data.fixer.io/api")
             .queryParam("access_key","YOUR_ACCESS_KEY")
             .queryParam("start_date","2012-05-01")
             .queryParam("end_date","2012-06-01")
             .when()
             .get("/latest")
             .then()
             .log().body()
             .statusCode(200);
    }

    @Test
    public void sending_headers_object()
    {
        HashMap<String,Object> custom_headers = new HashMap<String,Object>();
        custom_headers.put("content-type","application/x-www-form-urlencoded");
        custom_headers.put("X-RapidAPI-Key","YOUR_XRapid-Key");
        custom_headers.put("X-RapidAPI-Host","text-translator2.p.rapidapi.com");
        given()
                .baseUri("https://text-translator2.p.rapidapi.com")
                .param("source_language","en")
                .param("target_language","ar")
                .param("text","Mohamed Ishaque S")
                .headers(custom_headers)
                .when()
                .post("/translate")
                .then()
                .log().body().statusCode(200);
    }

    @Test
    public void sending_request_cookies()
    {
        given()
        .baseUri("http://data.fixer.io/api")
            .queryParam("access_key","YOUR_ACCESS_KEY")
            .queryParam("start_date","2012-05-01")
            .queryParam("end_date","2012-06-01")
                .cookies("user","234")
            .when()
            .get("/latest")
            .then()
            .log().body()
            .statusCode(200);
    }

    @Test
    public void sending_cookies_using_builder()
    {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key","YOUR_ACCESS_KEY")
                .queryParam("start_date","2012-05-01")
                .queryParam("end_date","2012-06-01")
                .cookies("user","234")
                .when()
                .get("/latest")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void validating_response_header()
    {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key","YOUR_ACCESS_KEY")
                .queryParam("Symbols","USD")
                .when()
                .get("/latest")
                .then()
                .log().all()
                .statusCode(200)
                .headers("transfer-encoding","chunked");
    }

    @Test
    public void extract_response_header()
    {
      Headers response_headers =  given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key","YOUR_ACCESS_KEY")
                .queryParam("Symbols","USD")
                .when()
                .get("/latest")
                .then()
                .statusCode(200)
                .extract().headers();

        System.out.println(response_headers.getValue("date"));
        System.out.println(response_headers.getValue("content-type"));
        System.out.println(response_headers.getValue("x-apilayer-transaction-id"));
    }

    @Test
    public void extract_response_cookies()
    {
        Map<String,String> response_cookies =  given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key","YOUR_ACCESS_KEY")
                .queryParam("Symbols","USD")
                .when()
                .get("/latest")
                .then()
                .log().all()
                .statusCode(200)
                .extract().cookies();
        System.out.println(response_cookies);

    }
}
