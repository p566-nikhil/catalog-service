package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.dao.OptimisticLockingFailureException;

@WebMvcTest(BookController.class)
class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "7373731394";
        given(bookService.viewBookDetails(isbn))
            .willThrow(BookNotFoundException.class);

        mockMvc.perform(get("/books/" + isbn))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenPostBook() throws Exception {
        String isbn = "1111111111";
        given(bookService.addBookToCatalog(any()))
            .willThrow(new BookAlreadyExistsException(isbn));

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "isbn": "1111111111",
                      "title": "Title",
                      "author": "Author",
                      "price": 1.00
                    }
                    """))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void whenPostInvalid() throws Exception {
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "isbn": "AAAAAAAAAA",
                      "title": "",
                      "author": "Author",
                      "price": 1.00
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test 
    void whenPutCreateBook() throws Exception {
        String isbn = "1111111111";
        mockMvc.perform(put("/books/" + isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "isbn": "1111111111",
                      "title": "Title",
                      "author": "Author",
                      "price": 1.00
                    }
                    """))
            .andExpect(status().isCreated());
    }

    @Test 
    void whenPutUpdateBook() throws Exception {
        String isbn = "1111111111";
        given(bookService.isBookInCatalog(isbn)).willReturn(false, true);

        mockMvc.perform(put("/books/" + isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "isbn": "1111111111",
                      "title": "Title",
                      "author": "Author",
                      "price": 1.00
                    }
                    """))
            .andExpect(status().isCreated());

        mockMvc.perform(put("/books/" + isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "isbn": "1111111111",
                      "title": "Title Updated",
                      "author": "Author Updated",
                      "price": 2.00
                    }
                    """))
            .andExpect(status().isOk());
    }

    @Test 
    void whenIsbnDontMatch() throws Exception {
        String isbn = "1111111111";
        mockMvc.perform(put("/books/" + isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "isbn": "2222222222",
                      "title": "Title",
                      "author": "Author",
                      "price": 1.00
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test 
    void whenGetBooksByAuthor() throws Exception {
        String author = "Author";
        given(bookService.viewBooksByAuthor(author))
            .willReturn(List.of(
                Book.of("1111111111", "Title 1", author, null, 1.00),
                Book.of("2222222222", "Title 2", author, null, 2.00)
            ));

        mockMvc.perform(get("/books?author=" + author))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].isbn").value("1111111111"))
            .andExpect(jsonPath("$[0].title").value("Title 1"))
            .andExpect(jsonPath("$[0].author").value(author))
            .andExpect(jsonPath("$[0].price").value(1.00))
            .andExpect(jsonPath("$[1].isbn").value("2222222222"))
            .andExpect(jsonPath("$[1].title").value("Title 2"))
            .andExpect(jsonPath("$[1].author").value(author))
            .andExpect(jsonPath("$[1].price").value(2.00));
    }
    
    @Test 
    void optimisticLockingFailure() throws Exception {
        String isbn = "1111111111";
        given(bookService.editBookDetails(eq(isbn), any()))
            .willThrow(new OptimisticLockingFailureException("Optimistic locking failure"));

        mockMvc.perform(put("/books/" + isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "isbn": "1111111111",
                      "title": "Title",
                      "author": "Author",
                      "price": 1.00
                    }
                    """))
            .andExpect(status().isConflict());
    }
}
