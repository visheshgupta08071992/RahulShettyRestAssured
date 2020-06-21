import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

//Check whether Add Place API is working through RestAssured
public class Basics {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        Response response= given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payload.addPlace()).when().post("maps/api/place/add/json")
        .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server",equalTo("Apache/2.4.18 (Ubuntu)"))
        .extract().response();

        String place_id=response.jsonPath().get("place_id");
        System.out.println(place_id);

    //Add Place->Update Place with new address ->Get Place to validate if New Address is present in response

        String address="Summer Walk,Africa";

        //Update Place
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body("{\n" +
                "\"place_id\":\""+place_id+"\",\n" +
                "\"address\":\""+address+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}").when().put("maps/api/place/update/json").then().log().all().assertThat()
                .statusCode(200).body("msg",equalTo("Address successfully updated"));

        //Get Place to Validate if New Address is present in Response
        Response response1=given().log().all().queryParam("key","qaclick123").queryParam("place_id",""+ place_id+"")
                .when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response();

        String newAddress=response1.jsonPath().get("address");
        Assert.assertEquals(address,newAddress,"Address has not been Updated");

    }
}
