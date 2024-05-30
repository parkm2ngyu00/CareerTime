package kr.ac.dankook.ace.careertime.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;

@Table(name = "users")
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long user_id;

    @Column(name = "username", nullable = false, unique = true) // 유저 아이디
    private String username;

    @Column(name = "password", nullable = false)    // 유저 비밀번호
    private String password;

    @Column(name = "name")  // 회원 가입시 받을 정보
    private String name;

    @Column(name = "email")     // 회원 가입시 받을 정보
    private String email;

    @Column(name = "user_type", nullable = false)   // 회원 가입시 받을 정보
    private String user_type;

    @Column(name = "points")
    private Long points = 0L;

    @Column(name = "join_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime join_date = LocalDateTime.now();
    
}
