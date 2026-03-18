package edu.eci.dosw;

import edu.eci.dosw.core.exception.BookNotAvailableException;
import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.core.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    // --- EXITOSOS ---

    @Test
    void testAddBook() {
        Book book = new Book(null, "Clean Code", "Robert Martin", true);
        Book result = bookService.addBook(book, 3);
        assertNotNull(result.getId());
        assertEquals("Clean Code", result.getTitle());
        assertTrue(result.isAvailable());
    }

    @Test
    void testGetAllBooks() {
        bookService.addBook(new Book(null, "Clean Code", "Robert Martin", true), 2);
        bookService.addBook(new Book(null, "Refactoring", "Fowler", true), 1);
        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void testGetBookById() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Robert Martin", true), 2);
        Book found = bookService.getBookById(book.getId());
        assertEquals(book.getId(), found.getId());
    }

    @Test
    void testUpdateAvailability() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Robert Martin", true), 2);
        Book updated = bookService.updateAvailability(book.getId(), false);
        assertFalse(updated.isAvailable());
    }

    // --- DE ERROR ---

    @Test
    void testGetBookByIdNotFound() {
        assertThrows(BookNotAvailableException.class, () -> bookService.getBookById("id-inexistente"));
    }

    @Test
    void testAddBookEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () ->
                bookService.addBook(new Book(null, "", "Autor", true), 1));
    }
}