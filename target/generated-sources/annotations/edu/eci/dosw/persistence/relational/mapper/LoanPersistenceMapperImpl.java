package edu.eci.dosw.persistence.relational.mapper;

import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.core.model.Loan;
import edu.eci.dosw.core.model.User;
import edu.eci.dosw.persistence.relational.entity.BookEntity;
import edu.eci.dosw.persistence.relational.entity.LoanEntity;
import edu.eci.dosw.persistence.relational.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T23:21:33-0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class LoanPersistenceMapperImpl implements LoanPersistenceMapper {

    @Override
    public Loan toModel(LoanEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Loan loan = new Loan();

        loan.setId( entity.getId() );
        loan.setBook( bookEntityToBook( entity.getBook() ) );
        loan.setUser( userEntityToUser( entity.getUser() ) );
        loan.setLoanDate( entity.getLoanDate() );
        loan.setReturnDate( entity.getReturnDate() );
        loan.setStatus( entity.getStatus() );

        return loan;
    }

    @Override
    public LoanEntity toEntity(Loan model) {
        if ( model == null ) {
            return null;
        }

        LoanEntity loanEntity = new LoanEntity();

        loanEntity.setId( model.getId() );
        loanEntity.setBook( bookToBookEntity( model.getBook() ) );
        loanEntity.setUser( userToUserEntity( model.getUser() ) );
        loanEntity.setLoanDate( model.getLoanDate() );
        loanEntity.setReturnDate( model.getReturnDate() );
        loanEntity.setStatus( model.getStatus() );

        return loanEntity;
    }

    protected Book bookEntityToBook(BookEntity bookEntity) {
        if ( bookEntity == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookEntity.getId() );
        book.setTitle( bookEntity.getTitle() );
        book.setAuthor( bookEntity.getAuthor() );
        book.setTotalCopies( bookEntity.getTotalCopies() );
        book.setAvailableCopies( bookEntity.getAvailableCopies() );

        return book;
    }

    protected User userEntityToUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        user.setId( userEntity.getId() );
        user.setName( userEntity.getName() );
        user.setUsername( userEntity.getUsername() );
        user.setPassword( userEntity.getPassword() );
        user.setRole( userEntity.getRole() );

        return user;
    }

    protected BookEntity bookToBookEntity(Book book) {
        if ( book == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId( book.getId() );
        bookEntity.setTitle( book.getTitle() );
        bookEntity.setAuthor( book.getAuthor() );
        bookEntity.setTotalCopies( book.getTotalCopies() );
        bookEntity.setAvailableCopies( book.getAvailableCopies() );

        return bookEntity;
    }

    protected UserEntity userToUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setName( user.getName() );
        userEntity.setUsername( user.getUsername() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setRole( user.getRole() );

        return userEntity;
    }
}
