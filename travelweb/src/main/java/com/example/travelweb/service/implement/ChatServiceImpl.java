package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.ChatMapper;
import com.example.travelweb.dto.request.ChatRequest;
import com.example.travelweb.dto.response.ChatResponse;
import com.example.travelweb.entity.Chat;
import com.example.travelweb.repository.AdminRepository;
import com.example.travelweb.repository.ChatRepository;
import com.example.travelweb.repository.UserRepository;
import com.example.travelweb.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public ChatResponse sendMessage(ChatRequest chatRequest) {
        Chat chat = chatMapper.toEntity(chatRequest);
        chat.setUser(userRepository.findById(chatRequest.getUserID()).orElseThrow());
        chat.setAdmin(adminRepository.findById(chatRequest.getAdminID()).orElseThrow());
        chat.setCreatedDate(LocalDate.now());
        chat.setReadStatus(false);
        Chat saved = chatRepository.save(chat);
        return chatMapper.toResponse(saved);
    }

    public List<ChatResponse> getChatHistory(Long userID, Integer adminID) {
        List<Chat> chats = chatRepository.findByUser_UserIDAndAdmin_AdminIDOrderByCreatedDateAsc(userID, adminID);
        return chats.stream().map(chatMapper::toResponse).collect(Collectors.toList());
    }
}
