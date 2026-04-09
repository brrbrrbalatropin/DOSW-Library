package edu.eci.dosw.persistence.relational.mapper;

import edu.eci.dosw.core.model.User;
import edu.eci.dosw.persistence.relational.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T23:21:33-0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class UserPersistenceMapperImpl implements UserPersistenceMapper {

    @Override
    public User toModel(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = new User();

        user.setId( entity.getId() );
        user.setName( entity.getName() );
        user.setUsername( entity.getUsername() );
        user.setPassword( entity.getPassword() );
        user.setRole( entity.getRole() );

        return user;
    }

    @Override
    public UserEntity toEntity(User model) {
        if ( model == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( model.getId() );
        userEntity.setName( model.getName() );
        userEntity.setUsername( model.getUsername() );
        userEntity.setPassword( model.getPassword() );
        userEntity.setRole( model.getRole() );

        return userEntity;
    }
}
