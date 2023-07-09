package request_parameters;

import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class Request_Parameters {

    @Test
    public void handling_query_params(){
        given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .queryParam("postId","1")
                .when()
                .get("comments")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void handling_multiple_query_params()
    {
        HashMap<String,Object> params= new HashMap<String,Object>();
        params.put("page","1");
        params.put("id","229836");

        given()
                .baseUri("http://restapi.adequateshop.com/")
                .queryParams(params)
                .when()
                .get("api/Tourist")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void multiple_value_query_params()
    {
        HashMap<String,Object> params= new HashMap<String,Object>();
        params.put("page","1");
        params.put("codes","170");

        given()
                .baseUri("https://restcountries.com/")
                .queryParams(params)
                .when()
                .get("v3.1/alpha")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void path_query_paramters()
    {
        given()
                .baseUri("https://restcountries.com/v3.1")
                .pathParam("currency","USD")
                //.pathParam("name","India")
                .when()
                .get("/currency/{currency}")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void handling_form_data()
    {
        given()
                .baseUri("https://postman-echo.com/")
                .contentType("application/x-www-form-urlencoded;charset=utf-8")
                .formParam("First Name","Mohamed")
                .formParam("Last Name","Ishaque")
                .when()
                .post("post")
                .then()
                .log().body()
                .statusCode(200);

    }
}
