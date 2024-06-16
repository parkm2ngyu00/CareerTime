package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.ChatMessage;
import kr.ac.dankook.ace.careertime.domain.ChatRoom;
import kr.ac.dankook.ace.careertime.dto.ChatResponse;
import kr.ac.dankook.ace.careertime.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
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
        messagingTemplate.convertAndSendToUser(chatMessage.getReceiverId().toString(), "/queue/messages", savedMessage);
    }

//    @GetMapping("/{chatRoomId}/messages")
//    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable("chatRoomId") Long chatRoomId) {
//        return ResponseEntity.ok(chatService.getMessages(chatRoomId));
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ChatResponse>> getChatResponses(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(chatService.getChatResponses(userId));
    }

    @PostMapping("/{userId}/{yourId}")
    public ChatRoom createChatRoom(@PathVariable("userId") Long userId, @PathVariable("yourId") Long yourId) {

        ChatRoom chatRoom = chatService.getChatRoom(userId, yourId);
        if (chatRoom == null) {
            chatRoom = chatService.createChatRoom(userId, yourId);
        }

        return chatService.getChatRoom(userId, yourId);
    }
}