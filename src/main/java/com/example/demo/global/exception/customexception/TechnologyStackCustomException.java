package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.TechnologyStackErrorCode;

public class TechnologyStackCustomException extends CustomException {

    public static final TechnologyStackCustomException NOT_FOUND_TECHNOLOGY_STACK =
            new TechnologyStackCustomException(TechnologyStackErrorCode.NOT_FOUND_TECHNOLOGY_STACK);

    public TechnologyStackCustomException(TechnologyStackErrorCode technologyStackErrorCode) {
        super(technologyStackErrorCode);
    }
}
