package com.spdev.agileboard_backend.Implementations;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import com.spdev.agileboard_backend.modals.Invitation;
import com.spdev.agileboard_backend.repository.InvitationRepository;
import com.spdev.agileboard_backend.services.EmailService;
import com.spdev.agileboard_backend.services.InvitationService;

@Service
public class InvitationServiceImpl implements InvitationService {
    
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EmailService emailService;

  
    @Override
    public void SendInvitation(String email, Long projectId) throws Exception {
       try {
           String invitationToken = UUID.randomUUID().toString();
           Invitation invitation = new Invitation();
           invitation.setEmail(email);
           invitation.setProjectId(projectId);
           invitation.setToken(invitationToken);

           invitationRepository.save(invitation);

           String InvitationLink = "http://localhost:5173/accept_invitation?token="+invitationToken;
           emailService.SendEmailWIthToken(email, InvitationLink);
       } catch (Exception e) {
            if(e instanceof MailSendException){
                throw new MailSendException(e.getMessage());
       }
          throw new Exception(e.getMessage());
    }
    }
    @Override
    public Invitation AcceptInvitation(String token, Long uid) throws Exception {
        try {
            Invitation invitation = invitationRepository.findByToken(token);
            if(invitation == null){
                throw new Exception("Invalid Invitation token");
            }
            return invitation;
        } catch (Exception e) {
          throw new InternalError(e.getMessage());
        }
    }

    @Override
    public String getTokenByUserMail(String userEmail) throws Exception {
       try {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        if(invitation == null){
            throw new Exception("Invitation expired");
        }
         return invitation.getToken();
       } catch (Exception e) {
         throw new Exception(e.getMessage());
       }
    }

    @Override
    public void deleteToken(String token) throws Exception {
       try {
        Invitation invitation = invitationRepository.findByToken(token);
         invitationRepository.deleteById(invitation.getId());
       } catch (Exception e) {
          throw new InternalError(e.getMessage());
       }
    }

}
