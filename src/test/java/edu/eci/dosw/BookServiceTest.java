package edu.eci.dosw;

import edu.eci.dosw.core.exception.BookNotAvailableException;
import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.core.service.BookService;
import edu.eci.dosw.persistence.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookServiceTest {

    private BookService bookService;
    private IBookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(IBookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void testAddBook() {
        Book book = new Book(null, "Clean Code", "Robert Martin", 3, 3);
        Book saved = new Book("uuid-1", "Clean Code", "Robert Martin", 3, 3);
        when(bookRepository.save(book)).thenReturn(saved);
        Book result = bookService.addBook(book);
        assertNotNull(result.getId());
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(
                new Book("1", "Clean Code", "Martin", 2, 2),
                new Book("2", "Refactoring", "Fowler", 1, 1)
        ));
        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void testGetBookById() {
        Book book = new Book("uuid-1", "Clean Code", "Martin", 2, 2);
        when(bookRepository.findById("uuid-1")).thenReturn(Optional.of(book));
        Book found = bookService.getBookById("uuid-1");
        assertEquals("uuid-1", found.getId());
    }

    @Test
    void testGetBookByIdNotFound() {
        when(bookRepository.findById("no-existe")).thenReturn(Optional.empty());
        assertThrows(BookNotAvailableException.class, () -> bookService.getBookById("no-existe"));
    }

    @Test
    void testAddBookInvalidCopies() {
        Book book = new Book(null, "Clean Code", "Martin", 0, 0);
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(book));
    }
}