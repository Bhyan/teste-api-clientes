package api;

import domain.Client;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ClientApi {
    final static String CLIENT_URI = "/cliente";
    final static String CLIENT_BY_ID = "/cliente/{id}";
    final static String DROP_ALL_CLIENTS = "/cliente/apagaTodos";

    public Response getAllClient() {
        return given()
        .when()
                .get();
    }

    public Response createResponseClient(Client client) {
        return given()
                .body(client)
        .when()
                .post(CLIENT_URI);
    }
    public void createClient(Client client) {
        given()
                .body(client)
        .when()
                .post(CLIENT_URI)
        .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    public Response updateResponseClient(Client client) {
        return given()
                .body(client)
        .when()
                .put(CLIENT_URI);
    }

    public Response deleteClient(Client client) {
        return given()
                .pathParam("id", client.getId())
        .when()
                .delete(CLIENT_BY_ID);
    }

    public Response getClientById(Client client) {
        return given()
                .pathParam("id", client.getId())
        .when()
                .get(CLIENT_BY_ID);
    }

    public void deleteAllClients() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(DROP_ALL_CLIENTS)
                .then()
                .statusCode(200);
    }
}
