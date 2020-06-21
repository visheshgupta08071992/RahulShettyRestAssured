import Files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class complexJsonParse {

  /*  1. Print No of courses returned by API

      2.Print Purchase Amount

      3. Print Title of the first course

      4. Print All course titles and their respective Prices

      5. Print no of copies sold by RPA Course

      6. Verify if Sum of all Course prices matches with Purchase Amount*/

    @Test
    public void parseJson(){
        JsonPath js=new JsonPath(Payload.coursePrice());
        //Print No of courses returned by API
        System.out.println(js.get("courses.size()"));
        //Print Purchase Amount
        System.out.println(js.get("dashboard.purchaseAmount"));
        //Print Title of the first course
        System.out.println(js.get("courses[0].title"));
        //Print All course titles and their respective Prices
        int courseSize=js.get("courses.size()");
        for (int i=0;i<courseSize;i++){
            System.out.println("Course Title :" +js.get("courses["+i+"].title") + "\n Course Price" + js.get("courses["+i+"].price"));
        }
        //Print no of copies sold by RPA Course
        for (int i=0;i<courseSize;i++){
            String courseTitle=js.get("courses["+i+"].title");
            if(courseTitle.equals("RPA")){
                System.out.println("Copies of Course RPA is : " +js.get("courses["+i+"].copies"));
                break;
            }
        }
        //Verify if Sum of all Course prices matches with Purchase Amount
        int purchaseAmount=js.getInt("dashboard.purchaseAmount");
        int totalPrice=0;
        for (int i=0;i<courseSize;i++){
            totalPrice=totalPrice + (js.getInt("courses["+i+"].price") * js.getInt("courses["+i+"].copies"));
        }
        Assert.assertEquals(totalPrice,purchaseAmount,"Purchase price does not match the Total Price");




    }
}
