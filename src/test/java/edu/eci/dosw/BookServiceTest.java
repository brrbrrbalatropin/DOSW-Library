package edu.eci.dosw;

import edu.eci.dosw.core.exception.BookNotAvailableException;
import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.core.service.BookService;
import edu.eci.dosw.persistence.entity.BookEntity;
import edu.eci.dosw.persistence.mapper.BookPersistenceMapper;
import edu.eci.dosw.persistence.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookServiceTest {

    private BookService bookService;
    private BookRepository bookRepository;
    private BookPersistenceMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        bookMapper = Mockito.mock(BookPersistenceMapper.class);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void testAddBook() {
        Book book = new Book(null, "Clean Code", "Robert Martin", 3, 3);
        BookEntity entity = new BookEntity(null, "Clean Code", "Robert Martin", 3, 3);
        Book saved = new Book("uuid-1", "Clean Code", "Robert Martin", 3, 3);

        when(bookMapper.toEntity(book)).thenReturn(entity);
        when(bookRepository.save(entity)).thenReturn(entity);
        when(bookMapper.toModel(entity)).thenReturn(saved);

        Book result = bookService.addBook(book);
        assertNotNull(result.getId());
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void testGetAllBooks() {
        BookEntity e1 = new BookEntity("1", "Clean Code", "Martin", 2, 2);
        BookEntity e2 = new BookEntity("2", "Refactoring", "Fowler", 1, 1);
        when(bookRepository.findAll()).thenReturn(List.of(e1, e2));
        when(bookMapper.toModel(e1)).thenReturn(new Book("1", "Clean Code", "Martin", 2, 2));
        when(bookMapper.toModel(e2)).thenReturn(new Book("2", "Refactoring", "Fowler", 1, 1));

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void testGetBookById() {
        BookEntity entity = new BookEntity("uuid-1", "Clean Code", "Martin", 2, 2);
        Book book = new Book("uuid-1", "Clean Code", "Martin", 2, 2);
        when(bookRepository.findById("uuid-1")).thenReturn(Optional.of(entity));
        when(bookMapper.toModel(entity)).thenReturn(book);

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