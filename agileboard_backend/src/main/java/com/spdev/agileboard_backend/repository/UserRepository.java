package com.spdev.agileboard_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spdev.agileboard_backend.modals.User;



public interface UserRepository extends JpaRepository<User, Long> {
   

    public User findByEmail(String email);
}
