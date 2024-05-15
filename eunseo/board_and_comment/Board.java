package kr.ac.dankook.ace.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity // JPA 엔티티임을 선언
@Table(name = "posts") // 이 엔티티가 저장될 DB 테이블을 지정
@Getter @Setter // Lombok을 사용하여 Getter, Setter 자동 생성
@AllArgsConstructor @NoArgsConstructor // Lombok을 사용하여 생성자 자동 생성
public class Board {
    @Id // 기본 키임을 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성 전략을 지정 (DB가 자동으로 생성)
    private Integer post_id;

    @Column(name = "user_id") // DB의 칼럼 이름을 명시
    private String user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "hashtags")
    private String hashtags;

    @Column(name = "post_date") // 게시글의 작성일을 저장하는 칼럼
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime post_date;

    @PrePersist
    protected void onCreate() {
        post_date = LocalDateTime.now();
    }

    public void setPostId(Integer postId) {
        this.post_id = postId;
    }
    public Integer getPostId() {
        return this.post_id;
    }
}