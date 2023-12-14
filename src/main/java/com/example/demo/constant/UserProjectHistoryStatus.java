package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 회원 프로젝트 참여상태 enum
@AllArgsConstructor
@Getter
public enum UserProjectHistoryStatus {
    FORCED_WITHDRAWAL("강제 탈퇴"),
    WITHDRAWAL("탈퇴"),
    PARTICIPATING("참여중"),
    FINISH("완주");

    private String status;
}
