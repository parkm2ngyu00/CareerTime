package kr.ac.dankook.ace.careertime.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kr.ac.dankook.ace.careertime.domain.ChatMessage;
import kr.ac.dankook.ace.careertime.domain.ChatRoom;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.ChatMessageDTO;
import kr.ac.dankook.ace.careertime.dto.ChatResponse;
import kr.ac.dankook.ace.careertime.repository.ChatMessageRepository;
import kr.ac.dankook.ace.careertime.repository.ChatRoomRepository;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ChatRoom createChatRoom(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID: " + receiverId));

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setSender(sender);
        chatRoom.setReceiver(receiver);
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom getChatRoom(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID: " + receiverId));
        return chatRoomRepository.findBySenderAndReceiver(sender, receiver);
    }

    public ChatMessage saveMessage(Long chatRoomId, Long senderId, Long receiverId, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoomId(chatRoomId);
        chatMessage.setSenderId(senderId);
        chatMessage.setReceiverId(receiverId);
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessages(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }

    public List<ChatResponse> getChatResponses(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll().stream()
                .filter(chatRoom -> chatRoom.getSender().getUser_id().equals(userId) || chatRoom.getReceiver().getUser_id().equals(userId))
                .collect(Collectors.toList());

        return chatRooms.stream()
                .collect(Collectors.groupingBy(chatRoom -> {
                    if (chatRoom.getSender().getUser_id().equals(userId)) {
                        return chatRoom.getReceiver().getUser_id();
                    } else {
                        return chatRoom.getSender().getUser_id();
                    }
                }))
                .entrySet()
                .stream()
                .map(entry -> {
                    Long otherUserId = entry.getKey();
                    List<ChatRoom> groupedChatRooms = entry.getValue();
                    User otherUser = userRepository.findById(otherUserId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + otherUserId));
                    Optional<Profile> profileOptional = profileRepository.findByUser(otherUser);
                    String profilePicture = profileOptional.map(Profile::getProfilePicture).orElse("");

                    List<ChatMessageDTO> messages = groupedChatRooms.stream()
                            .flatMap(chatRoom -> getMessages(chatRoom.getRoom_id()).stream()
                                    .map(msg -> new ChatMessageDTO(msg.getSenderId().equals(userId) ? "me" : "other", msg.getMessage(), msg.getTimestamp().toString())))
                            .collect(Collectors.toList());

                    ChatMessageDTO lastMessage = messages.isEmpty() ? null : messages.get(messages.size() - 1);

                    return new ChatResponse(
                            groupedChatRooms.get(0).getRoom_id(),
                            otherUserId,
                            otherUser.getName(),
                            profilePicture,
                            lastMessage == null ? "" : lastMessage.getTime(),
                            lastMessage == null ? "" : lastMessage.getContent(),
                            messages
                    );
                }).collect(Collectors.toList());
    }
}