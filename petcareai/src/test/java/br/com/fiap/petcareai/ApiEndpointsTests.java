package br.com.fiap.petcareai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiEndpointsTests {

    private static final HttpClient HTTP = HttpClient.newHttpClient();

    @LocalServerPort
    private int port;

    @Test
    void deveListarPetsComSucesso() throws Exception {
        HttpResponse<String> response = enviarGet("/api/pets?page=0&size=10&sort=nome,asc");
        assertEquals(200, response.statusCode());
        assertTrue(response.body() != null && response.body().contains("content"));
    }

    @Test
    void deveRetornar400QuandoSortInvalido() throws Exception {
        HttpResponse<String> response = enviarGet("/api/pets?sort=string");
        assertEquals(400, response.statusCode());
        assertTrue(response.body() != null && response.body().contains("status"));
    }

    @Test
    void deveRetornar400QuandoEmailTutorInvalido() throws Exception {
        String payload = """
                {
                  "nome": "Teste",
                  "email": "string",
                  "telefone": "11999998888",
                  "endereco": "Rua A, 10"
                }
                """;

        HttpResponse<String> response = enviarPostJson("/api/tutores", payload);
        assertEquals(400, response.statusCode());
        assertTrue(response.body() != null && response.body().contains("status"));
    }

    private HttpResponse<String> enviarGet(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + path))
                .GET()
                .build();
        return HTTP.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> enviarPostJson(String path, String json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return HTTP.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}




