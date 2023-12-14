package com.example.demo.dto.user.response;

import com.example.demo.constant.UserProjectHistoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProjectHistoryInfoResponseDto {

    private Long userProjectHistoryId;

    private UserProjectHistoryStatus status;

    private String projectName;

    private String updateDate;
}
