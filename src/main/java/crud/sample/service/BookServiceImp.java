package crud.sample.service;

import crud.sample.model.Book;
import crud.sample.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class BookServiceImp implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {

        return bookRepository.save(book);
    }

    @Override
    public List<Book> fetchBookList() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book updateBook(Long bookId,Book book) {

       return bookRepository.findById(bookId)
                .map(savedBook -> {
                    savedBook.setName(book.getName());
                    savedBook.setWriter(book.getWriter());
                    savedBook.setDescription(book.getDescription());
                    return bookRepository.save(savedBook);

                })
                .orElseThrow();

    }
}
