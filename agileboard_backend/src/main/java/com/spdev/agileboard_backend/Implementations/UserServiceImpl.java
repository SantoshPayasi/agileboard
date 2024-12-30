package com.spdev.agileboard_backend.Implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spdev.agileboard_backend.config.jwtProvider;
import com.spdev.agileboard_backend.modals.User;
import com.spdev.agileboard_backend.repository.UserRepository;
import com.spdev.agileboard_backend.services.UserService;

@Service
public class UserServiceImpl implements  UserService{


    @Autowired
    UserRepository userRepository;


    @Override
    public User findUserByjwt(String jwt) throws Exception {
        try {
            String email = jwtProvider.getEmailfromToken(jwt);
            return findUserByEmail(email);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
       try {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User Not Found");  
        }
        return user;
       } catch (Exception e) {
         throw new Exception(e.getMessage());
       }
    }

    @Override
    public User findUserByUserID(Long id) throws Exception {
      try {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
          throw new Exception("User Not Found");
        }
        return user.get();
      } catch (Exception e) {
        throw new Exception(e.getMessage());
      }
    }

    @Override
    public User updateUserProjectSize(User user, int number) throws Exception {
         try{
            user.setProjectsSize(user.getProjectsSize()+number);
            return userRepository.save(user);
         }
         catch(Exception e){
             throw new Exception(e.getMessage());
         }
    }

}
