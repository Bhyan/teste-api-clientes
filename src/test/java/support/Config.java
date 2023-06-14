package support;

import api.ClientApi;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class Config {

    private final ClientApi clientApi;

    public Config() {
        clientApi = new ClientApi();
    }

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.baseURI = "http://localhost:8080";

        RestAssured.requestSpecification = new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                build();
    }

    @BeforeEach
    public void deleteAllClients() {
        clientApi.deleteAllClients();
    }
}
