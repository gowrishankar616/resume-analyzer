package com.vcube.ResumeAnalyzer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneExtractor {

    // Matches things like: +91 98765 43210, 9876543210, (011) 2345-6789, 98765-43210
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("(\\+?\\d[\\d\\s().-]{8,16}\\d)");

    public static String extractPhone(String text) {
        Matcher matcher = PHONE_PATTERN.matcher(text);
        while (matcher.find()) {
            String candidate = matcher.group().trim();
            int digits = candidate.replaceAll("\\D", "").length();
            // A valid phone number has 10 to 13 digits (with country code)
            if (digits >= 10 && digits <= 13) {
                return candidate;
            }
        }
        return null;
    }
}
