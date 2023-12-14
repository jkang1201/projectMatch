package com.example.demo.repository.user;

import com.example.demo.model.user.User;

public interface UserRepositoryCustom {

    // 회원 업데이트를 위한 사용자 세부 정보 가져오기(포지션, 회원 기술스택, 기술스택)
    User fetchUserDetailsForUpdate(Long userId);

    // 회원 모든 정보 가져오기 (포지션, 회원 기술스택, 기술스택, 신뢰점수, 신뢰등급)
    User fetchUserByUserIdWithAllAttributes(Long userId);
}
