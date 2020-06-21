import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "getData")
    public void addBook(String aisle,String isbn){

        RestAssured.baseURI="http://216.10.245.166";
        String responseString=given().log().all().header("Content-Type","application/json").body(Payload.addBook(aisle,isbn))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js=new JsonPath(responseString);
        String id=js.get("ID");

        //Delete the added Book
       /* given().log().all().header("Content-Type","application/json").body("{\n" +
                "\n" +
                "\"ID\" : \""+id+"\"\n" +
                "\n" +
                "}")
                .when().post("Library/DeleteBook.php")
                .then().log().all();*/

    }

    @DataProvider
    public Object[][] getData(){
        //array=Collection of Elements
        //multidimensional array=Collection of Arrays
        return new Object[][]{ {"afghi","9871"},{"qqwr","12467"},{"zxz","97077"} };
    }

}
