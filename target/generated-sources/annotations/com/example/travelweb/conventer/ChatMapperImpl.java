package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.ChatRequest;
import com.example.travelweb.dto.response.ChatResponse;
import com.example.travelweb.entity.Admin;
import com.example.travelweb.entity.Chat;
import com.example.travelweb.entity.User;
import com.example.travelweb.enums.SenderRole;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T16:57:46+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ChatMapperImpl implements ChatMapper {

    @Override
    public ChatResponse toResponse(Chat chat) {
        if ( chat == null ) {
            return null;
        }

        ChatResponse.ChatResponseBuilder chatResponse = ChatResponse.builder();

        chatResponse.userID( chatUserUserID( chat ) );
        chatResponse.adminID( chatAdminAdminID( chat ) );
        if ( chat.getSenderRole() != null ) {
            chatResponse.senderRole( chat.getSenderRole().name() );
        }
        chatResponse.chatID( chat.getChatID() );
        chatResponse.messages( chat.getMessages() );
        chatResponse.readStatus( chat.isReadStatus() );
        chatResponse.createdDate( chat.getCreatedDate() );

        return chatResponse.build();
    }

    @Override
    public Chat toEntity(ChatRequest chatRequest) {
        if ( chatRequest == null ) {
            return null;
        }

        Chat chat = new Chat();

        chat.setMessages( chatRequest.getMessages() );

        chat.setSenderRole( SenderRole.valueOf(chatRequest.getSenderRole()) );

        return chat;
    }

    private Long chatUserUserID(Chat chat) {
        if ( chat == null ) {
            return null;
        }
        User user = chat.getUser();
        if ( user == null ) {
            return null;
        }
        Long userID = user.getUserID();
        if ( userID == null ) {
            return null;
        }
        return userID;
    }

    private Integer chatAdminAdminID(Chat chat) {
        if ( chat == null ) {
            return null;
        }
        Admin admin = chat.getAdmin();
        if ( admin == null ) {
            return null;
        }
        Integer adminID = admin.getAdminID();
        if ( adminID == null ) {
            return null;
        }
        return adminID;
    }
}
