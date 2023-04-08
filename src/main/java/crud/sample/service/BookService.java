package crud.sample.service;

import crud.sample.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {
     Book createBook(Book book);
     List<Book> fetchBookList();
     void deleteBook(Long bookId);
     Book updateBook(Long bookId,Book book);










}
