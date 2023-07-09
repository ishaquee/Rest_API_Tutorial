package getting_started;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;

import static  io.restassured.RestAssured.*;

public class Getting_Started {


    @BeforeClass
    public void setup(){
        baseURI = "https://restcountries.com/v3.1/";
        System.out.println("Base uri setup is done");
    }

    @Test
    public void simple_get_request(){
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("all");
        Assert.assertEquals(response.statusCode(),200);
        System.out.println("what we got is -> " + response.body());
    }

    @Test
    public void validate_json_response(){
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("name/usa");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.statusCode(),200);
        String value = jsonPathEvaluator.get("name.nativeName.eng.official").toString();
        //System.out.println("City received from Response " + response.body().prettyPrint());
        Assert.assertEquals(value, "[United States of America]", "Correct city name received in the Response");
    }

    @Test
    public void validate_xml_response(){
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.baseUri("http://restapi.adequateshop.com/api/").get("Traveler/11133");
        Assert.assertEquals(response.statusCode(),200);
        System.out.println(response.asString());
        XmlPath xmlPath = new XmlPath(response.asString());
        String id = xmlPath.getString("Travelerinformation.id");
        String name = xmlPath.getString("Travelerinformation.name");
        String email = xmlPath.getString("Travelerinformation.email");
        Assert.assertEquals(id, "11133", "InCorrect id received in the Response");
        Assert.assertEquals(name, "Developer", "InCorrect name received in the Response");
        Assert.assertEquals(email, "Developer12@gmail.com", "InCorrect email received in the Response");
    }


    @Test
    public void extarct_xml_response_data(){
        Response res = given().baseUri("http://restapi.adequateshop.com/api/").when().get("Traveler/11134").then().extract().response();
        System.out.println(" XML Response FOR Traveller  "+res.asString());
    }

    @Test
    public void extract_json_repsonse_data(){
        Response res = given().baseUri("https://restcountries.com/v3.1/").when().get("name/india").then().extract().response();
        //System.out.println(" JSON Response FOR INDIA --- " + res.asPrettyString());
    }

    @Test
    public void extract_single_value_from_response(){
        RequestSpecification httpRequest = given();
        Response response = httpRequest.baseUri("https://run.mocky.io/v3/").get("55889581-da52-4383-840e-bdf6dde19252");
        Assert.assertEquals(response.statusCode(),200);
        //System.out.println(response.asString());
        XmlPath xmlPath = new XmlPath(response.asString());
        System.out.println(xmlPath.getString("courses.subject[0]"));
    }

    @Test
    public void verifying_status_line(){
        RequestSpecification httpRequest = given();
        Response response = httpRequest.baseUri("https://api.printful.com/").get("variant/1");
        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.statusLine(),"HTTP/1.1 404 Not Found");
    }
}
