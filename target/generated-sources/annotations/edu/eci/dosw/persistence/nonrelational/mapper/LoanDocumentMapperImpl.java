package edu.eci.dosw.persistence.nonrelational.mapper;

import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.core.model.Loan;
import edu.eci.dosw.core.model.User;
import edu.eci.dosw.persistence.nonrelational.document.LoanDocument;
import edu.eci.dosw.persistence.relational.entity.LoanStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T23:21:32-0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class LoanDocumentMapperImpl implements LoanDocumentMapper {

    @Override
    public LoanDocument toDocument(Loan model) {
        if ( model == null ) {
            return null;
        }

        LoanDocument loanDocument = new LoanDocument();

        loanDocument.setBookId( modelBookId( model ) );
        loanDocument.setUserId( modelUserId( model ) );
        loanDocument.setId( model.getId() );
        loanDocument.setLoanDate( model.getLoanDate() );
        loanDocument.setReturnDate( model.getReturnDate() );
        if ( model.getStatus() != null ) {
            loanDocument.setStatus( model.getStatus().name() );
        }

        return loanDocument;
    }

    @Override
    public Loan toModel(LoanDocument document) {
        if ( document == null ) {
            return null;
        }

        Loan loan = new Loan();

        loan.setId( document.getId() );
        loan.setLoanDate( document.getLoanDate() );
        loan.setReturnDate( document.getReturnDate() );
        if ( document.getStatus() != null ) {
            loan.setStatus( Enum.valueOf( LoanStatus.class, document.getStatus() ) );
        }
        loan.setBookId( document.getBookId() );
        loan.setUserId( document.getUserId() );

        return loan;
    }

    private String modelBookId(Loan loan) {
        Book book = loan.getBook();
        if ( book == null ) {
            return null;
        }
        return book.getId();
    }

    private String modelUserId(Loan loan) {
        User user = loan.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
