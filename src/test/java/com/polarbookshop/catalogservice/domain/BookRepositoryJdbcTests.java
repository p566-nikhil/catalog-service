package com.polarbookshop.catalogservice.domain;

import java.util.Optional;

import com.polarbookshop.catalogservice.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)            // auditing is not part of the slice by default
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
                                    // do not swap in an embedded database
@ActiveProfiles("integration")       // use the Testcontainers URL
class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate; // lower-level setup tool

    @Test
    void findBookByIsbnWhenExisting() {
        var isbn = "1234561237";
        var book = Book.of(isbn, "Title", "Author", null, 12.90);
        jdbcAggregateTemplate.insert(book); // arrange without the repository under test

        Optional<Book> actual = bookRepository.findByIsbn(isbn);

        assertThat(actual).isPresent();
        assertThat(actual.get().isbn()).isEqualTo(isbn);
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        assertThat(bookRepository.findByIsbn("1234561238")).isEmpty();
    }

    @Test
    void findAll() {
        bookRepository.deleteAll();

        var book1 = Book.of("1234561239", "Title 1", "Author 1", null, 12.90);
        var book2 = Book.of("1234561240", "Title 2", "Author 2", null, 13.90);
        jdbcAggregateTemplate.insert(book1);
        jdbcAggregateTemplate.insert(book2);

    assertThat(bookRepository.findAll()).hasSize(2);
}

    @Test 
    void existsByIsbnTrue() {
        var book = Book.of("1234561239", "Title", "Author", null, 12.90);
        jdbcAggregateTemplate.insert(book);

        assertThat(bookRepository.existsByIsbn("1234561239")).isTrue();
    }

    @Test 
    void existsByIsbnFalse() {
        assertThat(bookRepository.existsByIsbn("1234561240")).isFalse();
    }

    @Test 
    void deleteByIsbn() {
        var book = Book.of("1234561239", "Title", "Author", null, 12.90);
        jdbcAggregateTemplate.insert(book);
        bookRepository.deleteByIsbn("1234561239");

        assertThat(bookRepository.findByIsbn("1234561239")).isEmpty();
    }

    @Test 
    void createdDate() {
        var book = Book.of("1234561239", "Title", "Author", null, 12.90);
        var saved = jdbcAggregateTemplate.insert(book);

        assertThat(saved.createdDate()).isNotNull();
    }
}