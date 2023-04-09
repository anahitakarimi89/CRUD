package crud.sample.test;

import crud.sample.model.Book;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookControllerTest extends AbstractTest{

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getBooksList() throws Exception {
        String uri = "/books/bookLists";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Book[] bookList = super.mapFromJson(content, Book[].class);
        assertTrue(bookList.length > 0);
    }


    @Test
    public void createBook() throws Exception {
        String uri = "/books/saveBook";
        Book book = new Book();
        book.setId(10L);
        book.setName("Gone With The Winds");
        book.setWriter("margaret mitchel");
        book.setDescription("Novel");
        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Book is created successfully");
    }
    @Test
    public void updateBook() throws Exception {
        String uri = "/books/updateBook/10";
        Book book = new Book();
        book.setDescription("surreal");
        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Book is updated successfully");
    }
    @Test
    public void deleteBook() throws Exception {
        String uri = "/books/deleteBook/10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Book is deleted successfully");
    }
}
