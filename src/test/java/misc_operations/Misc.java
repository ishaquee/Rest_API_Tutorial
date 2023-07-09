package misc_operations;

import io.restassured.RestAssured;
import io.restassured.config.XmlConfig;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;

public class Misc {

    @BeforeClass
    public void before_setup()
    {
        baseURI = "http://localhost/";
        port = 8080;
    }

    @Test
    public void hitGetRequestUsingWireMock(){
        when()
                .get("name/deutschland")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void assertTimeTakenForResponse(){
        when()
                .get("name/deutschland")
                .then()
                .time(Matchers.lessThan(2000L), TimeUnit.MILLISECONDS)
                .statusCode(200);
    }

    @Test
    public void xml_Namespace_Validation()
    {
        XmlConfig xmlConfig = XmlConfig.xmlConfig().declareNamespace("reservation","http://www.pcibooking.net/reservation");

        given()
                .config(RestAssured.config().xmlConfig(xmlConfig))
                .when()
                .get("card_details")
                .then()
                .body("BankCardDetails.BankCard.Type",Matchers.equalTo("Vaisa"))
                .body("BankCardDetails.BankCard.Number",Matchers.equalTo("4580458045804580"))
                .statusCode(200);
    }

    @Test
    public void validate_response_using_response_path()
    {
        Response response = given()
                .baseUri("https://official-joke-api.appspot.com/")
                .when()
                .get("random_joke")
                .then()
                .extract().response();

        String type = response.path("type");
        System.out.println(type);
        Assert.assertNotNull(type);

    }

    @Test
    public void validate_response_using_response_path1()
    {
        Response response = given()
                .baseUri("https://api.zippopotam.us/")
                .when()
                .get("us/33162")
                .then()
                .extract().response();

        String Country = response.path("country");
        System.out.println(Country);
        Assert.assertEquals("United States", Country);
    }

    // using lamba expression for validation
    @Test
    public void response_Aware_Matcher_Example(){
        when()
                .get("/get_article")
                .then()
                .log().all()
                .body("articleUrl", response -> Matchers.equalTo(response.path("href").toString() + response.path("articleId").toString()));
    }

    @Test
    public void response_Aware_Matcher()
    {
        given()
                .baseUri("https://api.zippopotam.us/")
                .when()
                .get("us/33162")
                .then()
                .body("country",response -> Matchers.equalTo("United States"));
    }

}
