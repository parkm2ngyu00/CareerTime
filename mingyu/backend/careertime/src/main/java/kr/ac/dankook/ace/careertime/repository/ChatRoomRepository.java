package kr.ac.dankook.ace.careertime.repository;

import kr.ac.dankook.ace.careertime.domain.ChatRoom;
import kr.ac.dankook.ace.careertime.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findBySenderAndReceiver(User sender, User receiver);
}
