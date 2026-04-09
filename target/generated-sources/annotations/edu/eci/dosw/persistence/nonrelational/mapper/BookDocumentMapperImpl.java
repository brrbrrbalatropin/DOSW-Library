package edu.eci.dosw.persistence.nonrelational.mapper;

import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.persistence.nonrelational.document.BookDocument;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T23:21:32-0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class BookDocumentMapperImpl implements BookDocumentMapper {

    @Override
    public BookDocument toDocument(Book model) {
        if ( model == null ) {
            return null;
        }

        BookDocument bookDocument = new BookDocument();

        bookDocument.setAvailability( bookToBookAvailability( model ) );
        bookDocument.setId( model.getId() );
        bookDocument.setTitle( model.getTitle() );
        bookDocument.setAuthor( model.getAuthor() );

        return bookDocument;
    }

    @Override
    public Book toModel(BookDocument document) {
        if ( document == null ) {
            return null;
        }

        Book book = new Book();

        book.setTotalCopies( documentAvailabilityTotalCopies( document ) );
        book.setAvailableCopies( documentAvailabilityAvailableCopies( document ) );
        book.setId( document.getId() );
        book.setTitle( document.getTitle() );
        book.setAuthor( document.getAuthor() );

        return book;
    }

    protected BookDocument.BookAvailability bookToBookAvailability(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDocument.BookAvailability bookAvailability = new BookDocument.BookAvailability();

        bookAvailability.setTotalCopies( book.getTotalCopies() );
        bookAvailability.setAvailableCopies( book.getAvailableCopies() );

        return bookAvailability;
    }

    private int documentAvailabilityTotalCopies(BookDocument bookDocument) {
        BookDocument.BookAvailability availability = bookDocument.getAvailability();
        if ( availability == null ) {
            return 0;
        }
        return availability.getTotalCopies();
    }

    private int documentAvailabilityAvailableCopies(BookDocument bookDocument) {
        BookDocument.BookAvailability availability = bookDocument.getAvailability();
        if ( availability == null ) {
            return 0;
        }
        return availability.getAvailableCopies();
    }
}
