import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        //validate if Add Place API is working as expected
        //given - all input details
        //when - submit the api -resource , http method
        //then - validate the response

//        RestAssured.baseURI="https://rahulshettyacademy.com";
//        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//                .body(PayLoad.AddPlace()).when().post("maps/api/place/add/json")
//                .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server","Apache/2.4.52 (Ubuntu)");

        //add place-> update place with new address-> Get Place to validate if new address is present in response

        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(PayLoad.AddPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server","Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);
        JsonPath js=new JsonPath(response); //for parsing Json
        String placeId=js.getString("place_id");
        System.out.println(placeId);

        String newAddress="Madhapur, hyd";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(PayLoad.UpdatePlace(placeId,newAddress)).when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));

        String getPlceResponse=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath getPlaceResponseJson=new JsonPath(getPlceResponse);
        String actualAddress=getPlaceResponseJson.getString("address");
        System.out.println(actualAddress);
        //Junit, Testng for assertion and other
        Assert.assertEquals(actualAddress,newAddress);

        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").
                body("{\n" +
                        "    \"place_id\":\""+placeId+"\"\n" +
                        "}\n")
                .when().delete("maps/api/place/delete/json")
                .then().log().all().assertThat().statusCode(200);

    }
}
