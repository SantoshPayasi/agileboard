package com.spdev.agileboard_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spdev.agileboard_backend.modals.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    
}
