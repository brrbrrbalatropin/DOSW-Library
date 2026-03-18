package edu.eci.dosw;

import edu.eci.dosw.core.exception.BookNotAvailableException;
import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.core.model.Loan;
import edu.eci.dosw.core.model.LoanStatus;
import edu.eci.dosw.core.model.User;
import edu.eci.dosw.core.service.BookService;
import edu.eci.dosw.core.service.LoanService;
import edu.eci.dosw.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    private LoanService loanService;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        userService = new UserService();
        loanService = new LoanService(bookService, userService);
    }

    // --- EXITOSOS ---

    @Test
    void testCreateLoan() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Martin", true), 2);
        User user = userService.registerUser(new User(null, "Juan"));
        Loan loan = loanService.createLoan(book.getId(), user.getId());
        assertNotNull(loan.getId());
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
    }

    @Test
    void testReturnBook() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Martin", true), 1);
        User user = userService.registerUser(new User(null, "Juan"));
        Loan loan = loanService.createLoan(book.getId(), user.getId());
        Loan returned = loanService.returnBook(loan.getId());
        assertEquals(LoanStatus.RETURNED, returned.getStatus());
        assertNotNull(returned.getReturnDate());
    }

    @Test
    void testGetAllLoans() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Martin", true), 2);
        User user = userService.registerUser(new User(null, "Juan"));
        loanService.createLoan(book.getId(), user.getId());
        List<Loan> loans = loanService.getAllLoans();
        assertEquals(1, loans.size());
    }

    @Test
    void testGetLoansByUser() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Martin", true), 2);
        User user = userService.registerUser(new User(null, "Juan"));
        loanService.createLoan(book.getId(), user.getId());
        List<Loan> loans = loanService.getLoansByUser(user.getId());
        assertEquals(1, loans.size());
    }

    // --- DE ERROR ---

    @Test
    void testCreateLoanBookNotAvailable() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Martin", true), 1);
        User user1 = userService.registerUser(new User(null, "Juan"));
        User user2 = userService.registerUser(new User(null, "Laura"));
        loanService.createLoan(book.getId(), user1.getId());
        assertThrows(BookNotAvailableException.class, () ->
                loanService.createLoan(book.getId(), user2.getId()));
    }

    @Test
    void testCreateLoanUserNotFound() {
        Book book = bookService.addBook(new Book(null, "Clean Code", "Martin", true), 1);
        assertThrows(Exception.class, () ->
                loanService.createLoan(book.getId(), "usuario-inexistente"));
    }
}