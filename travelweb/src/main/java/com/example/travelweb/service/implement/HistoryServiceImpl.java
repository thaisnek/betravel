package com.example.travelweb.service.implement;

import com.example.travelweb.conventer.HistoryMapper;
import com.example.travelweb.dto.response.HistoryResponseDTO;
import com.example.travelweb.entity.History;
import com.example.travelweb.enums.ActionType;
import com.example.travelweb.repository.HistoryRepository;
import com.example.travelweb.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public Page<HistoryResponseDTO> getUserHistory(Long userId, ActionType actionType, Pageable pageable) {
        Page<History> histories;
        if (actionType != null) {
            histories = historyRepository.findByUserUserIDAndActionType(userId, actionType, pageable);
        } else {
            histories = historyRepository.findByUserUserID(userId, pageable);
        }
        return histories.map(historyMapper::toDto);
    }
}
