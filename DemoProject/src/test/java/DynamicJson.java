import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI="http://216.10.245.166";
        String responce = given().log().all().header("Content-Type", "application/json")
        .body(PayLoad.AddBook(isbn, aisle)).when().post("Library/Addbook.php")
        .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsonPath = new JsonPath(responce);
        String id = jsonPath.get("ID");
        System.out.println(id);
        Assert.assertNotNull(id);

        given().log().all().header("Content-Type", "application/json")
                .body(PayLoad.deleteBook(id)).when().post("Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("book is successfully deleted"));
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        return new Object[][] {{"abc","123"},{"abcd","1234"},{"abcde","12345"}};
    }
}
