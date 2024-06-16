package kr.ac.dankook.ace.careertime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private Long room_id;
    private Long receiver_id;
    private String receiver_name;
    private String receiver_profileImage;
    private String lastChatDate;
    private String lastChatMessage;
    private List<ChatMessageDTO> messages;
}