package com.example.travelweb.service;

import com.example.travelweb.dto.request.ChatRequest;
import com.example.travelweb.dto.response.ChatResponse;

import java.util.List;

public interface ChatService {
    ChatResponse sendMessage(ChatRequest chatRequest);
    List<ChatResponse> getChatHistory(Long userID, Integer adminID);
}
