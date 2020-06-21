import Files.Payload;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class StaticJson {

    @Test
    public void staticJson(){
        RestAssured.baseURI="http://216.10.245.166";
        String responseString=given().log().all().header("Content-Type","application/json").body(new File(System.getProperty("user.dir") + "/src/main/java/Files/Addbook.json"))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println(System.getProperty("user.dir"));

    }
}
