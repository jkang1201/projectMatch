package com.example.demo.repository.user;

import com.example.demo.model.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    // 이메일 확인
    boolean existsByEmail(String email);

    // 닉네임 확인
    boolean existsByNickname(String nickname);

    // 이메일로 회원 조화
    Optional<User> findByEmail(String email);
}
