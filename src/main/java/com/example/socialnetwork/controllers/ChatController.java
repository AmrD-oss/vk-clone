package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.InputMessage;
import com.example.socialnetwork.models.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/chat")
    public String showChat() {
        return "chat";
    }

    @MessageMapping("/secured/chat")
    public void sendSpecific(
            @Header("simpSessionId") String sessionId,
            Principal user,
            @Payload InputMessage inputMessage) throws Exception {

        OutputMessage outputMessage = new OutputMessage(
                inputMessage.getFrom(),
                inputMessage.getText(),
                LocalDate.now());

        simpMessagingTemplate.convertAndSendToUser(
                inputMessage.getTo(),
                "/secured/user/queue/specific-user",
                outputMessage);
    }
}
