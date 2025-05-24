package com.example.travelweb.controller;

import com.example.travelweb.dto.response.HistoryResponseDTO;
import com.example.travelweb.enums.ActionType;
import com.example.travelweb.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "http://localhost:3000")
public class Historycontroller {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/user/{userId}")
    public Page<HistoryResponseDTO> getUserHistory(@PathVariable Long userId,
                                                   @RequestParam(required = false) ActionType actionType,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyService.getUserHistory(userId, actionType, pageable);
    }
}
