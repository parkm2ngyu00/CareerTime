package kr.ac.dankook.ace.careertime.repository;

import java.util.List;

import kr.ac.dankook.ace.careertime.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 리포지토리 계층을 선언, 스프링이 자동으로 구현체를 생성
public interface BoardRepository extends JpaRepository<Board, Long> { // JPA 리포지토리 상속
    List<Board> findByTitleContainingOrContentContainingOrHashtagsContaining(String title, String content, String hashtag);
    List<Board> findByHashtagsContaining(String hashtag);
}
