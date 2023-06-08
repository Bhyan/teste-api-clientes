import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ClientTest {

    final static String CLIENT_URI = "/cliente";
    final static String GET_CLIENT_BY_ID = "/cliente/{id}";
    final static String DROP_ALL_CLIENTS = "/cliente/apagaTodos";

    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar clientes, então a lista deve esta vazia")
    public void getAllClientsAndIsEmpty() {

        given()
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(new IsEqual<>(new HashMap().toString()));
    }

    @Test
    @DisplayName("Quando cadastrar um cliente, então ele deve estar disponível no resultado")
    public void createClient() {

        Client createClient = Client.builder().build();

        given()
                .contentType(ContentType.JSON)
                .body(createClient)
        .when()
                .post(CLIENT_URI)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("1005.nome", equalTo(createClient.getNome()))
                .body("1005.idade", equalTo(createClient.getIdade()))
                .body("1005.risco", equalTo(createClient.getRisco()));
    }

    @Test
    @DisplayName("Quando editar um cliente já cadastrado, então ele deve estar disponível com os novos dados")
    public void updateClient() {
        Client createClient = Client.builder().build();

        given()
                .contentType(ContentType.JSON)
                .body(createClient)
        .when()
                .post(CLIENT_URI)
        .then()
                .statusCode(HttpStatus.SC_CREATED);

        Client updateClient = Client.builder().nome("Mickey Mouse").idade(30).risco(2).build();

        given()
                .contentType(ContentType.JSON)
                .body(updateClient)
        .when()
                .put(CLIENT_URI)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("1005.nome", equalTo(updateClient.getNome()))
                .body("1005.idade", equalTo(updateClient.getIdade()))
                .body("1005.risco", equalTo(updateClient.getRisco()));
    }

    @Test
    @DisplayName("Quando deletar um cliente, então ele não deve esta disponível")
    public void deleteClient() {
        Client createClient = Client.builder().build();

        given()
                .contentType(ContentType.JSON)
                .body(createClient)
        .when()
                .post(CLIENT_URI)
        .then()
                .statusCode(HttpStatus.SC_CREATED);

        given()
                .pathParam("id", createClient.getId())
                .contentType(ContentType.JSON)
        .when()
                .delete(GET_CLIENT_BY_ID)
        .then()
                .statusCode(200)
                .assertThat().body(not(contains("Minnie Mouse")));
    }

    @Test
    @DisplayName("Quando pesquiso um cliente pelo ID, então o sistema deve retorna-lo")
    public void getAClient() {
        Client createClient = Client.builder().build();

        given()
                .contentType(ContentType.JSON)
                .body(createClient)
        .when()
                .post(CLIENT_URI)
        .then()
                .statusCode(HttpStatus.SC_CREATED);

        given()
                .pathParam("id", createClient.getId())
                .contentType(ContentType.JSON)
        .when()
                .get(GET_CLIENT_BY_ID)
        .then()
                .statusCode(200)
                .body("id", equalTo(createClient.getId()))
                .body("nome", equalTo(createClient.getNome()))
                .body("idade", equalTo(createClient.getIdade()))
                .body("risco", equalTo(createClient.getRisco()));
    }

    @BeforeEach
    public void deleteAllClients() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(DROP_ALL_CLIENTS)
        .then()
                .statusCode(200);
    }
}
