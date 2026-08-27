package com.example.backend.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Common Utility Class shared across feature modules.
 */
public class DateTimeUtils {

    private static final DateTimeFormatter STANDARD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(STANDARD_FORMATTER);
    }
}
