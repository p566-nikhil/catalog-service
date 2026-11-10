package com.polarbookshop.catalogservice.domain;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = Book.of("123456789X", "Title", "Author", null,9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = Book.of("a234567890", "Title", "Author", null,9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("Invalid ISBN");
    }

    @Test
    void whenTitleBlank() {
        var book = Book.of("123456789X", "", "Author", null,9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book title must be defined.");
    }

    @Test
    void whenAuthorBlank() {
        var book = Book.of("123456789X", "Title", "", null, 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book author must be defined.");
    }

    @Test
    void whenPriceMissing() {
        var book = Book.of("123456789X", "Title", "Author", null, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book price must be defined.");
    }

    @Test
    void whenPriceZero() {
        var book = Book.of("123456789X", "Title", "Author", null, 0.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book price must be greater than zero.");
    }

    @Test
    void whenPriceNegative() {
        var book = Book.of("123456789X", "Title", "Author", null,-5.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("The book price must be greater than zero.");
    }

    @Test 
    void whenIsbn10Valid() {
        var book = Book.of("123456789X", "Title", "Author", null, 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test 
    void whenIsbn13Valid() {
        var book = Book.of("9781234567897", "Title", "Author", null, 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test 
    void whenIsbn10Invalid() {
        var book = Book.of("1234567890", "Title", "Author", null, 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("Invalid ISBN");
    }

    @Test
    void whenIsbn13Invalid() {
        var book = Book.of("9781234567890", "Title", "Author", null, 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .isEqualTo("Invalid ISBN");
    }
}
