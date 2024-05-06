package kr.ac.dankook.ace.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 리포지토리 계층을 선언, 스프링이 자동으로 구현체를 생성
public interface BoardRepository extends JpaRepository<Board, Integer> { // JPA 리포지토리 상속
}
