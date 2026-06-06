import io.restassured.path.json.JsonPath;

import java.util.List;
import java.util.Map;

public class ComplexJsonParse {
    public static void main(String[] args){
        JsonPath js=new JsonPath(PayLoad.CoursePriceApiMockResponse());

        //1. Print No of courses returned by API
        int noOfCourses = js.getInt("courses.size()");
        System.out.println("No of courses: "+noOfCourses);

        //2.Print Purchase Amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount: "+purchaseAmount);

        //3.Print Title of the first course
        String firstCourseTitle = js.getString("courses[0].title"); //or js.get("courses[0].title"); .get by default returns string
        System.out.println("First Course Title: "+firstCourseTitle);

        //4.Print All course titles and their respective prices
        for(int i=0; i<noOfCourses; i++){
            System.out.println("Course Title: "+js.getString("courses["+i+"].title")+" , Price: "+js.getInt("courses["+i+"].price"));
        }

        //5. Print no of copies sold by RPA Course
        for(int i=0; i<noOfCourses; i++){
            if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")){
                System.out.println("no of copies sold by RPA Course : "+js.getInt("courses["+i+"].copies"));
                break;
            }
        }


    }
}
