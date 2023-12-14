package com.example.demo.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  날짜데이터 관련 타입 변환을 담당하는 클래스
 */
public class DateTimeConverter {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    // LocalDateTime -> String 변환
    public static String toStringConvert(LocalDateTime dateTime) {
        return dateTimeFormatter.format(dateTime);
    }

    // String -> LocalDateTime 변환
    public static LocalDateTime toLocalDateTimeConvert(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
