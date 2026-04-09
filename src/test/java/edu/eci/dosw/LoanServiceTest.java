package edu.eci.dosw;

import edu.eci.dosw.core.exception.BookNotAvailableException;
import edu.eci.dosw.core.model.Loan;
import edu.eci.dosw.core.service.BookService;
import edu.eci.dosw.core.service.LoanService;
import edu.eci.dosw.core.service.UserService;
import edu.eci.dosw.persistence.relational.entity.*;
import edu.eci.dosw.persistence.relational.mapper.LoanPersistenceMapper;
import edu.eci.dosw.persistence.relational.repository.LoanRepository;
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
    private LoanRepository loanRepository;
    private LoanPersistenceMapper loanMapper;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        loanRepository = Mockito.mock(LoanRepository.class);
        loanMapper = Mockito.mock(LoanPersistenceMapper.class);
        bookService = Mockito.mock(BookService.class);
        userService = Mockito.mock(UserService.class);
        loanService = new LoanService(loanRepository, loanMapper, bookService, userService);
    }

    @Test
    void testCreateLoan() {
        BookEntity book = new BookEntity("b1", "Clean Code", "Martin", 2, 2);
        UserEntity user = new UserEntity("u1", "Juan", "juanito", "pass", Role.USER);
        LoanEntity loanEntity = new LoanEntity("l1", book, user, LocalDateTime.now(), null, LoanStatus.ACTIVE);
        Loan loan = new Loan("l1", null, null, LocalDateTime.now(), null, LoanStatus.ACTIVE);

        when(bookService.getEntityById("b1")).thenReturn(book);
        when(userService.getEntityById("u1")).thenReturn(user);
        when(loanRepository.save(any())).thenReturn(loanEntity);
        when(loanMapper.toModel(loanEntity)).thenReturn(loan);

        Loan result = loanService.createLoan("b1", "u1");
        assertNotNull(result.getId());
        assertEquals(LoanStatus.ACTIVE, result.getStatus());
    }

    @Test
    void testCreateLoanBookNotAvailable() {
        BookEntity book = new BookEntity("b1", "Clean Code", "Martin", 2, 0);
        when(bookService.getEntityById("b1")).thenReturn(book);
        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan("b1", "u1"));
    }

    @Test
    void testReturnBook() {
        BookEntity book = new BookEntity("b1", "Clean Code", "Martin", 2, 1);
        UserEntity user = new UserEntity("u1", "Juan", "juanito", "pass", Role.USER);
        LoanEntity loanEntity = new LoanEntity("l1", book, user, LocalDateTime.now(), null, LoanStatus.ACTIVE);
        Loan loan = new Loan("l1", null, null, LocalDateTime.now(), LocalDateTime.now(), LoanStatus.RETURNED);

        when(loanRepository.findById("l1")).thenReturn(Optional.of(loanEntity));
        when(loanRepository.save(any())).thenReturn(loanEntity);
        when(loanMapper.toModel(any())).thenReturn(loan);

        Loan result = loanService.returnBook("l1");
        assertEquals(LoanStatus.RETURNED, result.getStatus());
    }

    @Test
    void testReturnAlreadyReturned() {
        BookEntity book = new BookEntity("b1", "Clean Code", "Martin", 2, 1);
        UserEntity user = new UserEntity("u1", "Juan", "juanito", "pass", Role.USER);
        LoanEntity loanEntity = new LoanEntity("l1", book, user, LocalDateTime.now(), LocalDateTime.now(), LoanStatus.RETURNED);

        when(loanRepository.findById("l1")).thenReturn(Optional.of(loanEntity));
        assertThrows(IllegalStateException.class, () -> loanService.returnBook("l1"));
    }

    @Test
    void testGetAllLoans() {
        when(loanRepository.findAll()).thenReturn(List.of());
        List<Loan> loans = loanService.getAllLoans();
        assertNotNull(loans);
    }
}