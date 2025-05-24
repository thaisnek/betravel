package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.response.AdminResponse;
import com.example.travelweb.entity.Admin;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T16:57:46+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public Admin toEntity(AdminCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setUsername( request.getUsername() );
        admin.setPassword( request.getPassword() );
        admin.setEmail( request.getEmail() );

        return admin;
    }

    @Override
    public AdminResponse toResponse(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminResponse.AdminResponseBuilder adminResponse = AdminResponse.builder();

        adminResponse.adminID( admin.getAdminID() );
        adminResponse.username( admin.getUsername() );
        adminResponse.email( admin.getEmail() );
        adminResponse.createdDate( admin.getCreatedDate() );

        return adminResponse.build();
    }
}
