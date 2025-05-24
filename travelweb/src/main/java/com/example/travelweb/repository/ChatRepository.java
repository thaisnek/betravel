package com.example.travelweb.repository;

import com.example.travelweb.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUser_UserIDAndAdmin_AdminIDOrderByCreatedDateAsc(Long userID, Integer adminID);
}
