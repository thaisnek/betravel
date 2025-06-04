package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.ContactRequest;
import com.example.travelweb.dto.response.ContactResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.entity.Contact;
import com.example.travelweb.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactResponse toDto(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        ContactResponse.ContactResponseBuilder contactResponse = ContactResponse.builder();

        contactResponse.userId( contactUserUserID( contact ) );
        contactResponse.adminId( contactAdminAdminID( contact ) );
        contactResponse.chatID( contact.getChatID() );
        contactResponse.fullName( contact.getFullName() );
        contactResponse.phoneNumber( contact.getPhoneNumber() );
        contactResponse.email( contact.getEmail() );
        contactResponse.message( contact.getMessage() );
        contactResponse.isReply( contact.getIsReply() );

        return contactResponse.build();
    }

    @Override
    public Contact toEntity(ContactRequest request) {
        if ( request == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setFullName( request.getFullName() );
        contact.setPhoneNumber( request.getPhoneNumber() );
        contact.setEmail( request.getEmail() );
        contact.setMessage( request.getMessage() );

        return contact;
    }

    private Long contactUserUserID(Contact contact) {
        if ( contact == null ) {
            return null;
        }
        User user = contact.getUser();
        if ( user == null ) {
            return null;
        }
        Long userID = user.getUserID();
        if ( userID == null ) {
            return null;
        }
        return userID;
    }

    private Long contactAdminAdminID(Contact contact) {
        if ( contact == null ) {
            return null;
        }
        Admin admin = contact.getAdmin();
        if ( admin == null ) {
            return null;
        }
        Long adminID = admin.getAdminID();
        if ( adminID == null ) {
            return null;
        }
        return adminID;
    }
}
