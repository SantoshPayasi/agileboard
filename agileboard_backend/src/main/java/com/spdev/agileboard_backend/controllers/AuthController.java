package com.spdev.agileboard_backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spdev.agileboard_backend.config.jwtProvider;
import com.spdev.agileboard_backend.modals.User;
import com.spdev.agileboard_backend.repository.UserRepository;
import com.spdev.agileboard_backend.response.AuthResponse;
import com.spdev.agileboard_backend.services.CustomUserDetailsImpl;
import com.spdev.agileboard_backend.types.LoginRequestDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetailsImpl;

   
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createuserHandler(@RequestBody  User user) throws Exception{

        try {
            User isalreadyExist = userRepository.findByEmail(user.getEmail());
            if(isalreadyExist != null){
                throw new Exception("User Already Exist");
            }

            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            User savedUser = userRepository.save(newUser);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            

            String jwt  =  jwtProvider.generateToken(authentication);

            AuthResponse response = new AuthResponse();
            
            response.setJwt(jwt);
            response.setMessage("User SignUp Successfully");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }

    }

    
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>signin(@RequestBody LoginRequestDto loginBodyDto) throws Exception{
        try {
            String userName = loginBodyDto.getEmail();
            String password = loginBodyDto.getPassword();

            UsernamePasswordAuthenticationToken authentication = authenticate(userName, password);
                        
                        SecurityContextHolder.getContext().setAuthentication(authentication);
            
                        String jwt  =  jwtProvider.generateToken(authentication);
                       
                        AuthResponse response = new AuthResponse();
                        
                        response.setJwt(jwt);
                        response.setMessage("User SignIn Successfully");
            
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } catch (Exception e) {
                       throw new Exception(e.getMessage());
                    }
                }
            
            
                private UsernamePasswordAuthenticationToken authenticate(String userName, String password) throws Exception {
                  try {
                      UserDetails userDetails = customUserDetailsImpl.loadUserByUsername(userName);
                      if(userDetails == null){
                          throw new BadCredentialsException("Invalid userName");
                      }

                      if(!passwordEncoder.matches(password, userDetails.getPassword())){
                          throw new BadCredentialsException("Invalid Password");
                      }

                      return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                  } catch (Exception e) {
                      throw new Exception(e.getMessage());
                  }
                }
}
