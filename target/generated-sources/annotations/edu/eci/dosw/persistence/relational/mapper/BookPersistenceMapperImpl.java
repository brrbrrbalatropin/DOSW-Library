package edu.eci.dosw.persistence.relational.mapper;

import edu.eci.dosw.core.model.Book;
import edu.eci.dosw.persistence.relational.entity.BookEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T23:21:32-0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class BookPersistenceMapperImpl implements BookPersistenceMapper {

    @Override
    public Book toModel(BookEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( entity.getId() );
        book.setTitle( entity.getTitle() );
        book.setAuthor( entity.getAuthor() );
        book.setTotalCopies( entity.getTotalCopies() );
        book.setAvailableCopies( entity.getAvailableCopies() );

        return book;
    }

    @Override
    public BookEntity toEntity(Book model) {
        if ( model == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId( model.getId() );
        bookEntity.setTitle( model.getTitle() );
        bookEntity.setAuthor( model.getAuthor() );
        bookEntity.setTotalCopies( model.getTotalCopies() );
        bookEntity.setAvailableCopies( model.getAvailableCopies() );

        return bookEntity;
    }
}
