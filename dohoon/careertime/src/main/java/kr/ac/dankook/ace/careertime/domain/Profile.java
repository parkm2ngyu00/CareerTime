package kr.ac.dankook.ace.careertime.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "userprofiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profile_id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "company_name", nullable = false)    // 회사 이름 또는 취준
    private String company_name;

    @Column(name = "position", nullable = false)    // 현재 직종 또는 희망 직종
    private String position;

    @Column(name = "introduction", nullable = false)    // 마크 업 언어로 소개 문구 작성
    private String introduction;

}
