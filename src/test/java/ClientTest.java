import api.ClientApi;
import domain.Client;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.Config;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

public class ClientTest extends Config {
    private final ClientApi clientApi;

    public ClientTest() {
        clientApi = new ClientApi();
    }
    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar clientes, então a lista deve esta vazia")
    public void getAllClientsAndIsEmpty() {
        Response expectedEmpty = clientApi.getAllClient();

        expectedEmpty.then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(new IsEqual<>(new HashMap().toString()));
    }

    @Test
    @DisplayName("Quando cadastrar um cliente, então ele deve estar disponível no resultado")
    public void createClient() {

        Client client = Client.builder().build();

        Response expectedResponse = clientApi.createResponseClient(client);

        expectedResponse.then().statusCode(HttpStatus.SC_CREATED)
                .body("1005.nome", equalTo(client.getNome()))
                .body("1005.idade", equalTo(client.getIdade()))
                .body("1005.risco", equalTo(client.getRisco()));
    }

    @Test
    @DisplayName("Quando editar um cliente já cadastrado, então ele deve estar disponível com os novos dados")
    public void updateClient() {
        Client createClient = Client.builder().build();

        clientApi.createClient(createClient);

        Client updateClient = Client.builder().nome("Mickey Mouse").idade(30).risco(2).build();

        Response expectedResponse = clientApi.updateResponseClient(updateClient);

        expectedResponse
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

        clientApi.createClient(createClient);

        Response expectedResponse = clientApi.deleteClient(createClient);

        expectedResponse
        .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(not(contains("Minnie Mouse")));
    }

    @Test
    @DisplayName("Quando pesquiso um cliente pelo ID, então o sistema deve retorna-lo")
    public void getAClient() {
        Client createClient = Client.builder().build();

        clientApi.createClient(createClient);

        Response expectedResponse = clientApi.getClientById(createClient);

        expectedResponse.then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(createClient.getId()))
                .body("nome", equalTo(createClient.getNome()))
                .body("idade", equalTo(createClient.getIdade()))
                .body("risco", equalTo(createClient.getRisco()));
    }
}
