package com.example.travelweb.controller;

import com.example.travelweb.dto.request.ChatRequest;
import com.example.travelweb.dto.response.ChatResponse;
import com.example.travelweb.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;


    @PostMapping("/send")
    public ChatResponse sendMessage(@RequestBody ChatRequest chatRequest) {
        return chatService.sendMessage(chatRequest);
    }

    @GetMapping("/history")
    public List<ChatResponse> getChatHistory(@RequestParam Long userID, @RequestParam Integer adminID) {
        return chatService.getChatHistory(userID, adminID);
    }
}
