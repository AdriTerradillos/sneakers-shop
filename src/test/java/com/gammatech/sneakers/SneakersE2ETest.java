package com.gammatech.sneakers;

import com.gammatech.sneakers.controllers.AuthRequest;
import com.gammatech.sneakers.controllers.AuthResponse;
import com.gammatech.sneakers.entity.Sneaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/user_creation.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class SneakersE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void should_create_sneaker_and_return_created_status() {
        // Preparación
        Sneaker sneaker = new Sneaker(null, "Air Max", "Nike", "Red", "42", 120.0);
        String url = "http://localhost:" + port + "/sneakers";

        String jwt = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwt);
        HttpEntity<Sneaker> httpRequest = new HttpEntity<>(sneaker, headers);

        // Acción
        ResponseEntity<Sneaker> response = restTemplate.postForEntity(url, httpRequest, Sneaker.class);

        // Verificación
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Air Max", response.getBody().getName());
    }

    @Test
    public void should_create_sneaker_with_wrong_object_and_return_bad_request_status() {
        // Preparación
        Sneaker sneaker = new Sneaker(null, "Air Max", null, "Red", null, 120.0);
        String url = "http://localhost:" + port + "/sneakers";

        String jwt = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwt);
        HttpEntity<Sneaker> httpRequest = new HttpEntity<>(sneaker, headers);

        // Acción
        ResponseEntity<Sneaker> response = restTemplate.postForEntity(url, httpRequest, Sneaker.class);

        // Verificación
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void should_update_sneaker_and_return_ok_status() {
        // Preparación
        Sneaker sneaker = new Sneaker(null, "Air Max", "Nike", "Red", "42", 120.0);
        String createUrl = "http://localhost:" + port + "/sneakers";

        String jwt = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwt);
        HttpEntity<Sneaker> createRequest = new HttpEntity<>(sneaker, headers);

        // Crear una sneaker para modificar
        ResponseEntity<Sneaker> createResponse = restTemplate.postForEntity(createUrl, createRequest, Sneaker.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        Sneaker createdSneaker = createResponse.getBody();
        assertNotNull(createdSneaker);
        assertNotNull(createdSneaker.getId());

        // Modificar la sneaker
        createdSneaker.setName("Air Max Updated");
        createdSneaker.setColor("Blue");
        String updateUrl = createUrl + "/" + createdSneaker.getId();
        HttpEntity<Sneaker> updateRequest = new HttpEntity<>(createdSneaker, headers);

        // Acción
        ResponseEntity<Sneaker> updateResponse = restTemplate.exchange(updateUrl, HttpMethod.PUT, updateRequest, Sneaker.class);

        // Verificación
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Air Max Updated", updateResponse.getBody().getName());
        assertEquals("Blue", updateResponse.getBody().getColor());
    }

    private String getTokenForAdminUser() {
        // Preparación
        AuthRequest authRequest = new AuthRequest("admin", "gammatech");
        String url = "http://localhost:" + port + "/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> httpRequest = new HttpEntity<>(authRequest, headers);

        // Acción
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(url, httpRequest, AuthResponse.class);


        return Objects.requireNonNull(response.getBody()).getJwt();
    }

}
