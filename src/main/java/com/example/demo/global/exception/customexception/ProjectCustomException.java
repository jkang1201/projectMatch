package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ProjectErrorCode;

public class ProjectCustomException extends CustomException {

    public static final ProjectCustomException NOT_FOUND_PROJECT =
            new ProjectCustomException(ProjectErrorCode.NOT_FOUND_PROJECT);

    public ProjectCustomException(ProjectErrorCode projectErrorCode) {
        super(projectErrorCode);
    }
}
