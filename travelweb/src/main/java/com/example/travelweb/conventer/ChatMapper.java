package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.ChatRequest;
import com.example.travelweb.dto.response.ChatResponse;
import com.example.travelweb.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.travelweb.enums.SenderRole;

@Mapper(componentModel = "spring", imports = SenderRole.class)
public interface ChatMapper {

    @Mapping(source = "user.userID", target = "userID")
    @Mapping(source = "admin.adminID", target = "adminID")
    @Mapping(source = "senderRole", target = "senderRole")
    ChatResponse toResponse(Chat chat);

    @Mapping(target = "chatID", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "readStatus", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "senderRole", expression = "java(SenderRole.valueOf(chatRequest.getSenderRole()))")
    Chat toEntity(ChatRequest chatRequest);
}
