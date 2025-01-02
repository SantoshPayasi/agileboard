package com.spdev.agileboard_backend.services;


import com.spdev.agileboard_backend.modals.Invitation;

public interface InvitationService {

    void SendInvitation(String email, Long projectId) throws Exception;

    Invitation AcceptInvitation(String token, Long uid) throws Exception;

    String getTokenByUserMail(String userEmail) throws Exception;


    void deleteToken(String token) throws Exception;

}
