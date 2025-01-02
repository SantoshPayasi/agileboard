package com.spdev.agileboard_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spdev.agileboard_backend.modals.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    
    Invitation findByToken(String token);

    Invitation findByEmail(String email);
}
