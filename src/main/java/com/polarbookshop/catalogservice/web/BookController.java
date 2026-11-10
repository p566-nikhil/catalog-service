package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Iterable<Book> get(@RequestParam(required = false) String author) {
        if (author != null) {
            return bookService.viewBooksByAuthor(author);
        }

        return bookService.viewBookList();
    }

    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book) {
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}")
    public ResponseEntity<Book> put(
        @PathVariable String isbn,
        @Valid @RequestBody Book book) {
    
    if (!isbn.equals(book.isbn())) {
        return ResponseEntity.badRequest().build();
    }

    boolean exists = bookService.isBookInCatalog(isbn);
    Book saved = bookService.editBookDetails(isbn, book);

    return exists 
        ? ResponseEntity.ok(saved) 
        : ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
}