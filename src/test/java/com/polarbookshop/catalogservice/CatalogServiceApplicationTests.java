package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookCreated() {
        var expected = Book.of("123456789X", "Title", "Author", 9.90);

        webTestClient
            .post().uri("/books")
            .bodyValue(expected)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Book.class).value(actual -> {
                assertThat(actual).isNotNull();
                assertThat(actual.isbn()).isEqualTo(expected.isbn());
            });
    }

    @Test
    void workflowTest() {
        var book = Book.of("9781234567897", "Title", "Author", 9.90);

        webTestClient
            .post().uri("/books")
            .bodyValue(book)
            .exchange()
            .expectStatus().isCreated();

        webTestClient
            .get().uri("/books/{isbn}", book.isbn())
            .exchange()
            .expectStatus().isOk()
            .expectBody(Book.class).value(actual -> {
                assertThat(actual).isNotNull();
                assertThat(actual.isbn()).isEqualTo(book.isbn());
                assertThat(actual.title()).isEqualTo(book.title());
                assertThat(actual.author()).isEqualTo(book.author());
                assertThat(actual.price()).isEqualTo(book.price());
            });

        webTestClient
            .put().uri("/books/{isbn}", book.isbn())
            .bodyValue(Book.of(book.isbn(), "New Title", "New Author", 19.90))
            .exchange()
            .expectStatus().isOk()
            .expectBody(Book.class).value(actual -> {
                assertThat(actual).isNotNull();
                assertThat(actual.isbn()).isEqualTo(book.isbn());
                assertThat(actual.title()).isEqualTo("New Title");
                assertThat(actual.author()).isEqualTo("New Author");
                assertThat(actual.price()).isEqualTo(19.90);
            });

        webTestClient
            .delete().uri("/books/{isbn}", book.isbn())
            .exchange()
            .expectStatus().isNoContent();

        webTestClient
            .get().uri("/books/{isbn}", book.isbn())
            .exchange()
            .expectStatus().isNotFound();
    }
}