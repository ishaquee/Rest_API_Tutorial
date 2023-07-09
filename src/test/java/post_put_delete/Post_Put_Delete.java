package post_put_delete;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class Post_Put_Delete {

    File file = new File("src/main/resources/employee_details.json");

    @Test
    public void post_request(){
        Response response =   given().baseUri("https://dummy.restapiexample.com/api/v1/")
                .contentType(ContentType.JSON)
                .body(file)
                .when().post("create").then()
                .statusCode(200).extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.statusCode(),200);
        String name = jsonPathEvaluator.get("data.name").toString();
        String id = jsonPathEvaluator.get("data.id").toString();
        String salary = jsonPathEvaluator.get("data.salary").toString();
        String age = jsonPathEvaluator.get("data.age").toString();
        System.out.println("data.name -" + name);
        System.out.println("data.id -" + id);
        System.out.println("data.salary -" + salary);
        System.out.println("data.age -" + age);
        Assert.assertEquals(name, "ishaque", "Incorrect user name received in the Response");
    }

    @Test
    public void post_request_using_jsonObject(){

        JSONObject body = new JSONObject();
        body.put("name","kumar");
        body.put("age","21");
        body.put("salary","20000");

        Response response =   given().baseUri("https://dummy.restapiexample.com/api/v1/")
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post("create").then()
                .statusCode(200).extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.statusCode(),200);
        String name = jsonPathEvaluator.get("data.name").toString();
        String id = jsonPathEvaluator.get("data.id").toString();
        String salary = jsonPathEvaluator.get("data.salary").toString();
        String age = jsonPathEvaluator.get("data.age").toString();
        System.out.println("data.name -" + name);
        System.out.println("data.id -" + id);
        System.out.println("data.salary -" + salary);
        System.out.println("data.age -" + age);
        Assert.assertEquals(name, "kumar", "Incorrect user name received in the Response");
    }


    @Test
    public void put_request_using_jsonObject()
    {
        JSONObject body = new JSONObject();
        body.put("name","kumar");
        body.put("age","21");
        body.put("salary","20000");

        Response response =   given().baseUri("https://dummy.restapiexample.com/api/v1/")
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().put("update/22").then()
                .statusCode(200).extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.statusCode(),200);
        String name = jsonPathEvaluator.get("data.name").toString();
        String salary = jsonPathEvaluator.get("data.salary").toString();
        String age = jsonPathEvaluator.get("data.age").toString();
        System.out.println("data.name -" + name);
        System.out.println("data.salary -" + salary);
        System.out.println("data.age -" + age);
        Assert.assertEquals(name, "kumar", "Incorrect user name received in the Response");
    }

    @Test
    public void delete_request_after_getting_data_from_get_method() throws InterruptedException {

        Response get_response =   given().baseUri("https://dummy.restapiexample.com/api/v1/")
                .contentType(ContentType.JSON)
                .when().get("employees").then()
                .statusCode(200).extract().response();

        JsonPath jsonPathEvaluator = get_response.jsonPath();
        Assert.assertEquals(get_response.statusCode(),200);
        String id = jsonPathEvaluator.get("data[0].id").toString();
        System.out.println("Done with fetching data from get method");
        // just waiting for creating the response before deleting it & also for wait before hitting the delete method
        Thread.sleep(30000);

        Response delete_response = given().
                baseUri("https://dummy.restapiexample.com/api/v1/")
                .contentType(ContentType.JSON)
                .when()
                .delete("delete/"+id)
                .then()
                .statusCode(200).extract().response();
        System.out.println(delete_response.prettyPrint());
    }
}
