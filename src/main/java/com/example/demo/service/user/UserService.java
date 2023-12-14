package com.example.demo.service.user;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.model.user.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    // 이메일 중복확인 로직
    @Transactional(readOnly = true)
    ResponseDto<?> checkEmail(String email);

    // 닉네임 중복확인 로직
    @Transactional(readOnly = true)
    ResponseDto<?> checkNickname(String nickname);

    User findById(Long userId);

    User save(User user);

    // 회원 수정 시 회원 정보 조회
    User getUserForUpdate(Long userId);

    // 회원의 모든 정보 조회
    User fetchUserDetails(Long userId);
}
