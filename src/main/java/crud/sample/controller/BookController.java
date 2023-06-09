package crud.sample.controller;

import crud.sample.model.Book;
import crud.sample.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {


    @Autowired
    private BookService bookService;

    private final Logger log = LoggerFactory.getLogger(BookController.class);


    @PostMapping("/book")
    public ResponseEntity saveBook(
             @RequestBody Book book)
    {
        Book returnValue = new Book();
        try {
            returnValue= bookService.createBook(book);

        }catch (Exception e){

            log.error("problem in saving book"+e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }


    @GetMapping("/books")
    public List<Book> fetchBookList()
    {
        List<Book> returnResult = new ArrayList<>();
        try {
            returnResult = bookService.fetchBookList();
        }catch (Exception e){

            log.error("problem in fetching book list" + e.getMessage());

        }
        return returnResult;
    }


    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long bookId,@RequestBody Book book)

    {
        Book returnValue = new Book();
        try {
            returnValue = bookService.updateBook(bookId,book);
        }catch (Exception e){
            log.error("problem in updating book" + e.getMessage());
        }
        return new ResponseEntity<>(returnValue, HttpStatus.OK);


    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long bookId)

    {
        try {
            bookService.deleteBook(bookId);
        }catch (Exception e){

            log.error("problem in deleting book"+e.getMessage());
        }

        return new ResponseEntity<>("Book deleted successfully!.", HttpStatus.NO_CONTENT);
    }



}
