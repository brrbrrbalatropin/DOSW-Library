package edu.eci.dosw.persistence.nonrelational.mapper;

import edu.eci.dosw.core.model.User;
import edu.eci.dosw.persistence.nonrelational.document.UserDocument;
import edu.eci.dosw.persistence.relational.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T23:21:32-0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class UserDocumentMapperImpl implements UserDocumentMapper {

    @Override
    public UserDocument toDocument(User model) {
        if ( model == null ) {
            return null;
        }

        UserDocument userDocument = new UserDocument();

        userDocument.setId( model.getId() );
        userDocument.setName( model.getName() );
        userDocument.setUsername( model.getUsername() );
        userDocument.setPassword( model.getPassword() );
        if ( model.getRole() != null ) {
            userDocument.setRole( model.getRole().name() );
        }

        return userDocument;
    }

    @Override
    public User toModel(UserDocument document) {
        if ( document == null ) {
            return null;
        }

        User user = new User();

        user.setId( document.getId() );
        user.setName( document.getName() );
        user.setUsername( document.getUsername() );
        user.setPassword( document.getPassword() );
        if ( document.getRole() != null ) {
            user.setRole( Enum.valueOf( Role.class, document.getRole() ) );
        }

        return user;
    }
}
