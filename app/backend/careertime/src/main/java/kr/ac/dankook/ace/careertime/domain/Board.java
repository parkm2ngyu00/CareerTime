package kr.ac.dankook.ace.careertime.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity // JPA 엔티티임을 선언
@Table(name = "posts") // 이 엔티티가 저장될 DB 테이블을 지정
@Data
@AllArgsConstructor @NoArgsConstructor // Lombok을 사용하여 생성자 자동 생성
public class Board {
    @Id // 기본 키임을 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성 전략을 지정 (DB가 자동으로 생성)
    private Long post_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    @Lob
    private String content;

    @Column(name = "hashtags")
    private String hashtags;

    @Column(name = "post_date") // 게시글의 작성일을 저장하는 칼럼
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime post_date;

//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments;

    @PrePersist
    protected void onCreate() {
        post_date = LocalDateTime.now();
    }
}