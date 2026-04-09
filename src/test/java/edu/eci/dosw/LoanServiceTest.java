package edu.eci.dosw;

import edu.eci.dosw.core.exception.BookNotAvailableException;
import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.core.model.Loan;
import edu.eci.dosw.core.model.User;
import edu.eci.dosw.core.service.BookService;
import edu.eci.dosw.core.service.LoanService;
import edu.eci.dosw.core.service.UserService;
import edu.eci.dosw.persistence.ILoanRepository;
import edu.eci.dosw.persistence.relational.entity.LoanStatus;
import edu.eci.dosw.persistence.relational.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoanServiceTest {

    private LoanService loanService;
    private ILoanRepository loanRepository;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        loanRepository = Mockito.mock(ILoanRepository.class);
        bookService = Mockito.mock(BookService.class);
        userService = Mockito.mock(UserService.class);
        loanService = new LoanService(loanRepository, bookService, userService);
    }

    @Test
    void testCreateLoan() {
        Book book = new Book("b1", "Clean Code", "Martin", 2, 2);
        User user = new User("u1", "Juan", "juanito", "pass", Role.USER);
        Loan loan = new Loan("l1", book, user, LocalDateTime.now(), null, LoanStatus.ACTIVE);

        when(bookService.getEntityById("b1")).thenReturn(book);
        when(userService.getEntityById("u1")).thenReturn(user);
        when(loanRepository.save(any())).thenReturn(loan);

        Loan result = loanService.createLoan("b1", "u1");
        assertNotNull(result.getId());
        assertEquals(LoanStatus.ACTIVE, result.getStatus());
    }

    @Test
    void testCreateLoanBookNotAvailable() {
        Book book = new Book("b1", "Clean Code", "Martin", 2, 0);
        when(bookService.getEntityById("b1")).thenReturn(book);
        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan("b1", "u1"));
    }

    @Test
    void testReturnBook() {
        Book book = new Book("b1", "Clean Code", "Martin", 2, 1);
        User user = new User("u1", "Juan", "juanito", "pass", Role.USER);
        Loan loan = new Loan("l1", book, user, LocalDateTime.now(), null, LoanStatus.ACTIVE);
        Loan returned = new Loan("l1", book, user, LocalDateTime.now(), LocalDateTime.now(), LoanStatus.RETURNED);

        when(loanRepository.findById("l1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(returned);

        Loan result = loanService.returnBook("l1");
        assertEquals(LoanStatus.RETURNED, result.getStatus());
    }

    @Test
    void testReturnAlreadyReturned() {
        Book book = new Book("b1", "Clean Code", "Martin", 2, 1);
        User user = new User("u1", "Juan", "juanito", "pass", Role.USER);
        Loan loan = new Loan("l1", book, user, LocalDateTime.now(), LocalDateTime.now(), LoanStatus.RETURNED);
        when(loanRepository.findById("l1")).thenReturn(Optional.of(loan));
        assertThrows(IllegalStateException.class, () -> loanService.returnBook("l1"));
    }

    @Test
    void testGetAllLoans() {
        when(loanRepository.findAll()).thenReturn(List.of());
        List<Loan> loans = loanService.getAllLoans();
        assertNotNull(loans);
    }
}