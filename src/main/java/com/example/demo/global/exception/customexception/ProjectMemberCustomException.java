package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ProjectMemberErrorCode;

public class ProjectMemberCustomException extends CustomException {

    public static final ProjectMemberCustomException NOT_FOUND_PROJECT_MEMBER =
            new ProjectMemberCustomException(ProjectMemberErrorCode.NOT_FOUND_PROJECT_MEMBER);

    public ProjectMemberCustomException(ProjectMemberErrorCode projectMemberErrorCode) {
        super(projectMemberErrorCode);
    }
}
