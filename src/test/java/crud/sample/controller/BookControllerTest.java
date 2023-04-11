package crud.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud.sample.model.Book;
import crud.sample.service.BookService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.http.MediaType;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveBook() throws Exception{


        Book book = Book.builder().id(100L).
                name("The Stranger").writer("Albert Camus").description("surreal").build();
        given(bookService.createBook(any(Book.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/books/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(book.getName())))
                .andExpect(jsonPath("$.writer",
                        is(book.getWriter())))
                .andExpect(jsonPath("$.description",
                        is(book.getDescription())));


    }

    @Test
    void fetchBookList() throws Exception{


        List<Book> listOfBooks = new ArrayList<>();
        listOfBooks.add(Book.builder().id(101L).name("The Stranger").writer("Albert Camus").description("surreal").build());
        listOfBooks.add(Book.builder().id(102L).name("Gone With The Winds").writer("Margaret mitchel").description("Novel").build());
        given(bookService.fetchBookList()).willReturn(listOfBooks);

        ResultActions response = mockMvc.perform(get("/books/books"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfBooks.size())));

    }



    @Test
    void updateBook() throws Exception{

        long bookId = 105L;

        Book updateBook = Book.builder().
                name("My Brilliant Friend").writer("Elena Ferrante").description("Novel").build();

        given(bookService.updateBook(105L,any(Book.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));


        ResultActions response = mockMvc.perform(put("/books/book/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBook)));


        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(updateBook.getName())))
                .andExpect(jsonPath("$.writer",
                        is(updateBook.getWriter())))
                .andExpect(jsonPath("$.description",
                        is(updateBook.getDescription())));

    }

    @Test
    void deleteBookById() throws Exception {

        long bookId = 100L;
        willDoNothing().given(bookService).deleteBook(bookId);
        ResultActions response = mockMvc.perform(delete("/books/book/{id}", bookId));
        response.andExpect(status().isOk())
                .andDo(print());
    }
}