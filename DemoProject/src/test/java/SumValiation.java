import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValiation {
    @Test
    public void validateSum() {
        JsonPath js=new JsonPath(PayLoad.CoursePriceApiMockResponse());

        //6. Verify if Sum of all Course prices matches with Purchase Amount

        int noOfCourses = js.getInt("courses.size()");
        System.out.println("No of courses: "+noOfCourses);

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount: "+purchaseAmount);

        int sumOfCoursePrices=0;
        for(int i=0; i<noOfCourses; i++){
            sumOfCoursePrices += js.getInt("courses["+i+"].copies") * js.getInt("courses["+i+"].price");
        }
        System.out.println("Sum of all Course prices: "+sumOfCoursePrices+" Purchase Amount: "+purchaseAmount);

        Assert.assertEquals(sumOfCoursePrices, purchaseAmount, "Sum of course prices does not match with purchase amount");
    }
}
