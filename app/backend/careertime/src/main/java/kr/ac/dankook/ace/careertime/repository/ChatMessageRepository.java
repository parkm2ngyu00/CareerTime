package kr.ac.dankook.ace.careertime.repository;

import kr.ac.dankook.ace.careertime.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
}