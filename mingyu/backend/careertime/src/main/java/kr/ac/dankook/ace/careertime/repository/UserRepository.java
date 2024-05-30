package kr.ac.dankook.ace.careertime.repository;

import java.util.Optional;

import kr.ac.dankook.ace.careertime.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
