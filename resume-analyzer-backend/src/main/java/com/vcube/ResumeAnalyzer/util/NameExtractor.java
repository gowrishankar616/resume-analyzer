package com.vcube.ResumeAnalyzer.util;
import java.util.regex.Pattern;

public class NameExtractor {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z .'-]{1,40}$");

    /**
     * Simple heuristic: the name is usually one of the first lines of a resume,
     * contains only letters, and has 1-4 words.
     */
    public static String extractName(String text) {
        String[] lines = text.split("\\R");
        int checked = 0;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            if (++checked > 10) {
                break; // name should be near the top
            }

            String lower = trimmed.toLowerCase();
            if (lower.contains("resume") || lower.contains("curriculum")) {
                continue;
            }

            int words = trimmed.split("\\s+").length;
            if (NAME_PATTERN.matcher(trimmed).matches() && words <= 4) {
                return trimmed;
            }
        }
        return null;
    }
}
