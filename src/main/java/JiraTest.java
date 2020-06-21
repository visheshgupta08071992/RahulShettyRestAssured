import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {

    @Test
    public void testJiraApis(){

        RestAssured.baseURI="http://localhost:8080";
        SessionFilter session=new SessionFilter();

        //Login senario.......Generate Cookie Session

 /*       String sessionId=given().log().all().header("Content-Type","application/json").body("{ \"username\": \"vishesh.gupta21\", \"password\": \"May@12345\" }")
                .when().post("/rest/auth/1/session")
                .then().log().all().assertThat().statusCode(200).extract().response().jsonPath().get("session.value");*/

        given().log().all().header("Content-Type","application/json").body("{ \"username\": \"vishesh.gupta21\", \"password\": \"May@12345\" }").filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().assertThat().statusCode(200).extract().response();


/*        given().log().all().header("Content-Type","application/json").header("cookie",sessionId).pathParam("key","10003").body("{\n" +
                "    \"body\": \"This is my comment through Automation\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").when()
                .post("/rest/api/2/issue/{key}/comment").then().log().all();*/


//Add comment on created Bug
     String commentId=   given().log().all().header("Content-Type","application/json").pathParam("key","10003").body("{\n" +
                "    \"body\": \"This is my comment through Automation\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(session).when()
                .post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201)
        .extract().response().jsonPath().getString("id");

        //Add Attachment to created Bug
   /*     given().log().all().header("X-Atlassian-Token","no-check").pathParam("key","10003")
                .header("Content-Type","multipart/formdata")
                .filter(session)
                .multiPart("file",new File( System.getProperty("user.dir")+ "/src/main/java/Files/FiletobeUploaded"))
                .when().post("/rest/api/2/issue/{key}/attachments")
        .then().log().all().assertThat().statusCode(200);*/

        //Get Issue
        String resopnse=given().log().all().pathParam("key","10003").queryParam("fields","comment").filter(session).when().get("/rest/api/2/issue/{key}").then()
                .extract().response().body().asString();

        System.out.println(resopnse);

        JsonPath js=new JsonPath(resopnse);
        int commentSize=js.getInt("fields.comment.comments.size()");
        for(int i=0;i<commentSize;i++){
String commentIdActual=js.getString("fields.comment.comments["+i+"]");
if(commentIdActual.equals(commentId)){
    System.out.println("\n \n Messsage is" +js.getString("fields.comment.comments["+i+"].body"));
    Assert.assertEquals(js.getString("fields.comment.comments["+i+"].body"),"This is my comment through Automation");
break;
}
        }
    }
}
