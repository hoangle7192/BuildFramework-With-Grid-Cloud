package MOT.features;

import listeners.ApiPoJoFile.NormalCustomer;
import MOT.api.PostCustomer;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class POSTNewCustomerTest {

    private NormalCustomer normalCustomer;
    private final PostCustomer postCustomer = new PostCustomer();

    @Test
    public void postNewCustomer() {
        Response res = PostCustomer.createAStudent(normalCustomer);
        res.then().statusCode(201);
    }
}
