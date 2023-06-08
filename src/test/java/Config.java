import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class Config {

    @BeforeAll
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.baseURI = "http://localhost:8080";
    }
}
