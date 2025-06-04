package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.AdminCreateRequest;
import com.example.travelweb.dto.response.AdminResponse;
import com.example.travelweb.entity.Admin;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
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
        admin.setFullName( request.getFullName() );
        admin.setAddress( request.getAddress() );
        admin.setPhone( request.getPhone() );
        admin.setEmail( request.getEmail() );

        return admin;
    }

    @Override
    public AdminResponse toResponse(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminResponse.AdminResponseBuilder adminResponse = AdminResponse.builder();

        if ( admin.getAdminID() != null ) {
            adminResponse.adminID( admin.getAdminID().intValue() );
        }
        adminResponse.username( admin.getUsername() );
        adminResponse.password( admin.getPassword() );
        adminResponse.email( admin.getEmail() );
        adminResponse.fullName( admin.getFullName() );
        adminResponse.address( admin.getAddress() );
        adminResponse.phone( admin.getPhone() );
        adminResponse.createdDate( admin.getCreatedDate() );

        return adminResponse.build();
    }
}
