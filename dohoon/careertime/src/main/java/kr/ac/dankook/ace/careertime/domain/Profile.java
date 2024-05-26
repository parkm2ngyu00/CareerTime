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

    @Column(name = "company_name")    // 회사 이름 또는 취준
    private String company_name;

    @Column(name = "position")    // 현재 직종 또는 희망 직종
    private String position;

    @Column(name = "hashtags")
    private String hashtags;    // 흥미분야 해쉬태그

    @Column(name = "introduction")    // 마크 업 언어로 소개 문구 작성
    private String introduction;

}
