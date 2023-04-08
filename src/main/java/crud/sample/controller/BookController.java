package crud.sample.controller;

import crud.sample.model.Book;
import crud.sample.service.BookService;
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


    @PostMapping("/saveBook")
    public ResponseEntity saveBook(
             @RequestBody Book book)
    {
        Book returnValue = new Book();
        try {
            returnValue= bookService.createBook(book);

        }catch (Exception e){

            //LOGBACK "problem in saving book"
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }


    @GetMapping("/bookLists")
    public List<Book> fetchBookList()
    {
        List<Book> returnResult = new ArrayList<>();
        try {
            returnResult = bookService.fetchBookList();
        }catch (Exception e){
            //LOGBACK "problem in fetching book list"
        }
        return returnResult;
    }


    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long bookId,@RequestBody Book book)

    {
        Book returnValue = new Book();
        try {
            returnValue = bookService.updateBook(bookId,book);
        }catch (Exception e){
            //LOGBACK "problem in updating book"
        }
        return new ResponseEntity<>(returnValue, HttpStatus.OK);


    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long bookId)

    {
        try {
            bookService.deleteBook(bookId);
        }catch (Exception e){
            //LOGBACK "problem in deleting book"
        }

        return new ResponseEntity<>("Book deleted successfully!.", HttpStatus.OK);
    }



}
