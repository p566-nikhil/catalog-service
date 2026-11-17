
package com.polarbookshop.catalogservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.polarbookshop.catalogservice.domain.Book;
import dasniko.testcontainers.keycloak.KeycloakContainer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
@Testcontainers
class CatalogServiceApplicationTests {

    @Container
    static KeycloakContainer keycloak =
        new KeycloakContainer("quay.io/keycloak/keycloak:26.0")
            .withRealmImportFile("test-realm-config.json");

    @DynamicPropertySource
    static void keycloakProperties(DynamicPropertyRegistry registry) {
        registry.add(
            "spring.security.oauth2.resourceserver.jwt.issuer-uri",
            () -> keycloak.getAuthServerUrl().replaceAll("/+$", "")
                + "/realms/PolarBookshop"
        );
    }

    private static String isabelleToken;
    private static String bjornToken;

    @BeforeAll
    static void generateAccessTokens() {
        var webClient = WebClient.builder()
            .baseUrl(
                keycloak.getAuthServerUrl().replaceAll("/+$", "")
                + "/realms/PolarBookshop/protocol/openid-connect/token"
            )
            .build();

        isabelleToken = tokenFor("isabelle", webClient);
        bjornToken = tokenFor("bjorn", webClient);
    }

    private static String tokenFor(
            String username, WebClient webClient) {

        var response = webClient.post()
            .body(BodyInserters
                .fromFormData("grant_type", "password")
                .with("client_id", "polar-test")
                .with("username", username)
                .with("password", "password"))
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();

        return response.get("access_token").asText();
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookCreated() {
        var expected = Book.of(
            "123456789X", "Title", "Author", null, 9.90
        );

        webTestClient
            .post().uri("/books")
            .headers(h -> h.setBearerAuth(isabelleToken))
            .bodyValue(expected)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Book.class)
            .value(actual -> {
                assertThat(actual).isNotNull();
                assertThat(actual.isbn()).isEqualTo(expected.isbn());
                assertThat(actual.publisher()).isNull();
            });
    }

    @Test
    void workflowTest() {
        var book = Book.of(
            "9781234567897", "Title", "Author", null, 9.90
        );

        webTestClient
            .post().uri("/books")
            .headers(h -> h.setBearerAuth(isabelleToken))
            .bodyValue(book)
            .exchange()
            .expectStatus().isCreated();

        webTestClient
            .get().uri("/books/{isbn}", book.isbn())
            .exchange()
            .expectStatus().isOk()
            .expectBody(Book.class)
            .value(actual -> {
                assertThat(actual).isNotNull();
                assertThat(actual.isbn()).isEqualTo(book.isbn());
                assertThat(actual.title()).isEqualTo(book.title());
                assertThat(actual.author()).isEqualTo(book.author());
                assertThat(actual.publisher()).isNull();
                assertThat(actual.price()).isEqualTo(book.price());
            });

        webTestClient
            .put().uri("/books/{isbn}", book.isbn())
            .headers(h -> h.setBearerAuth(isabelleToken))
            .bodyValue(Book.of(
                book.isbn(), "New Title", "New Author", null, 19.90
            ))
            .exchange()
            .expectStatus().isOk()
            .expectBody(Book.class)
            .value(actual -> {
                assertThat(actual).isNotNull();
                assertThat(actual.isbn()).isEqualTo(book.isbn());
                assertThat(actual.title()).isEqualTo("New Title");
                assertThat(actual.author()).isEqualTo("New Author");
                assertThat(actual.publisher()).isNull();
                assertThat(actual.price()).isEqualTo(19.90);
            });

        webTestClient
            .delete().uri("/books/{isbn}", book.isbn())
            .headers(h -> h.setBearerAuth(isabelleToken))
            .exchange()
            .expectStatus().isNoContent();

        webTestClient
            .get().uri("/books/{isbn}", book.isbn())
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void whenPostAsEmployeeThen201() {
        var book = Book.of(
            "0306406152",
            "Security Book",
            "Test Author",
            "Polarsophia",
            19.90
        );

        webTestClient
            .post().uri("/books")
            .headers(h -> h.setBearerAuth(isabelleToken))
            .bodyValue(book)
            .exchange()
            .expectStatus().isCreated();
    }

    @Test
    void whenPostAsCustomerThen403() {
        var book = Book.of(
            "0131103628",
            "Customer Book",
            "Test Author",
            "Polarsophia",
            19.90
        );

        webTestClient
            .post().uri("/books")
            .headers(h -> h.setBearerAuth(bjornToken))
            .bodyValue(book)
            .exchange()
            .expectStatus().isForbidden();
    }

    @Test
    void whenPostUnauthenticatedThen401() {
        var book = Book.of(
            "0201633612",
            "Anonymous Book",
            "Test Author",
            "Polarsophia",
            19.90
        );

        webTestClient
            .post().uri("/books")
            .bodyValue(book)
            .exchange()
            .expectStatus().isUnauthorized();
    }
}
