package kr.ac.dankook.ace.board.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kr.ac.dankook.ace.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer comment_id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Board board;

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @Column(name = "comment_text", nullable = false)
    private String comment_text;

    @Column(name = "comment_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime comment_date = LocalDateTime.now();
}
