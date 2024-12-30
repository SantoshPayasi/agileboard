package com.spdev.agileboard_backend.services;

import com.spdev.agileboard_backend.modals.Chat;

public interface ChatService {
   
    Chat createChat(Chat chat) throws Exception;
}
