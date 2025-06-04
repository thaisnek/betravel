package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.UpdateUserRequest;
import com.example.travelweb.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public void updateUserFromDto(UpdateUserRequest dto, User user) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getEmail() != null ) {
            user.setEmail( dto.getEmail() );
        }
        if ( dto.getFullName() != null ) {
            user.setFullName( dto.getFullName() );
        }
        if ( dto.getPhoneNumber() != null ) {
            user.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getAddress() != null ) {
            user.setAddress( dto.getAddress() );
        }
    }
}
