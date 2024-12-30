package com.spdev.agileboard_backend.services;

import com.spdev.agileboard_backend.modals.User;

public interface UserService {
      
    User findUserByjwt(String jwt) throws Exception;
    
    User findUserByEmail(String email) throws Exception;

    User findUserByUserID(Long id) throws Exception;

    User updateUserProjectSize(User user, int number) throws Exception;

    
}
