package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.MilestoneErrorCode;

public class MilestoneCustomException extends CustomException {

    public static final MilestoneCustomException NOT_FOUND_MILESTONE =
            new MilestoneCustomException(MilestoneErrorCode.NOT_FOUND_MILESTONE);

    public MilestoneCustomException(MilestoneErrorCode milestoneErrorCode) {
        super(milestoneErrorCode);
    }
}
