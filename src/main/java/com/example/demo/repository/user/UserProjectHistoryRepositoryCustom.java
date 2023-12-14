package com.example.demo.repository.user;

import com.example.demo.dto.user.response.UserProjectHistoryInfoResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserProjectHistoryRepositoryCustom {

    // 회원 프로젝트 이력 전체 개수 조회
    Long countUserProjectHistoryByUserId(Long userId);

    // 회원 프로젝트 이력 목록 조회 (수정날짜 기준 최신순 정렬, 페이징 offset 방법 활용)
    List<UserProjectHistoryInfoResponseDto> findAllByUserIdOrderByUpdateDateDesc(
            Long userId, Pageable pageable);
}
