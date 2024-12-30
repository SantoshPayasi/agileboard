package com.spdev.agileboard_backend.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spdev.agileboard_backend.modals.Chat;
import com.spdev.agileboard_backend.repository.ChatRepository;
import com.spdev.agileboard_backend.services.ChatService;


@Service
public class ChatServiceImpl implements ChatService {


    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) throws Exception {
        try {
            return chatRepository.save(chat);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
