package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.ChatMessage;
import kr.ac.dankook.ace.careertime.domain.ChatRoom;
import kr.ac.dankook.ace.careertime.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        ChatRoom chatRoom = chatService.getChatRoom(chatMessage.getSenderId(), chatMessage.getReceiverId());
        if (chatRoom == null) {
            chatRoom = chatService.createChatRoom(chatMessage.getSenderId(), chatMessage.getReceiverId());
        }
        ChatMessage savedMessage = chatService.saveMessage(chatRoom.getRoom_id(), chatMessage.getSenderId(), chatMessage.getReceiverId(), chatMessage.getMessage());
        messagingTemplate.convertAndSendToUser(chatMessage.getSenderId().toString(), "/queue/messages", savedMessage);
        messagingTemplate.convertAndSendToUser(chatMessage.getReceiverId().toString(), "/queue/messages", savedMessage);
    }

    @GetMapping("/api/chat/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable("chatRoomId") Long chatRoomId) {
        return ResponseEntity.ok(chatService.getMessages(chatRoomId));
    }
}