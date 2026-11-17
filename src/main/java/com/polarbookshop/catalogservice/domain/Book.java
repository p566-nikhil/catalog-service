package com.polarbookshop.catalogservice.domain;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

public record Book(

    @Id
    Long id,

    @NotBlank(message = "The book ISBN must be defined.")
    @ValidIsbn(message = "Invalid ISBN")
    String isbn,

    @NotBlank(message = "The book title must be defined.")
    String title,

    @NotBlank(message = "The book author must be defined.")
    String author,

    String publisher,

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    Double price,

    @CreatedDate
    Instant createdDate,

    @LastModifiedDate
    Instant lastModifiedDate,

    @CreatedBy
    String createdBy,

    @LastModifiedBy
    String lastModifiedBy,

    @Version
    int version

) {
    public static Book of(
            String isbn,
            String title,
            String author,
            String publisher,
            Double price) {

        return new Book(
            null, isbn, title, author, publisher, price,
            null, null, null, null, 0
        );
    }
}