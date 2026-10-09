package com.vcube.ResumeAnalyzer.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SkillMatcher {

    // Add more skills here any time - both the resume and the job description use this list
    private static final List<String> SKILLS = List.of(
            // Java & backend
            "Java", "Spring Boot", "Spring", "Spring Security", "Hibernate", "JPA", "JDBC",
            "Servlets", "JSP", "REST API", "RESTful", "Microservices", "JWT", "Maven", "Gradle",
            "JUnit", "Mockito", "Lombok", "Kafka", "RabbitMQ", "Redis",
            // Databases
            "MySQL", "PostgreSQL", "Oracle", "MongoDB", "SQL",
            // Frontend
            "React", "Angular", "Vue", "JavaScript", "TypeScript", "Node.js", "Express",
            "HTML", "CSS", "Bootstrap", "Tailwind",
            // Other languages
            "Python", "Django", "Flask", "C++",
            // Tools & cloud
            "Git", "GitHub", "Docker", "Kubernetes", "Jenkins", "CI/CD", "DevOps", "AWS", "Azure",
            "GCP", "Linux", "Postman", "Swagger", "Jira", "Selenium",
            // Concepts
            "OOP", "Data Structures", "Algorithms", "Agile", "Scrum");

    // Whole-word patterns: "Java" will NOT match "JavaScript", "SQL" will NOT match "MySQL"
    private static final Map<String, Pattern> PATTERNS = new LinkedHashMap<>();

    static {
        for (String skill : SKILLS) {
            PATTERNS.put(skill, Pattern.compile(
                    "(?<![A-Za-z0-9])" + Pattern.quote(skill) + "(?![A-Za-z0-9])",
                    Pattern.CASE_INSENSITIVE));
        }
    }

    public static List<String> extractSkills(String text) {
        List<String> found = new ArrayList<>();
        if (text == null) {
            return found;
        }
        for (Map.Entry<String, Pattern> entry : PATTERNS.entrySet()) {
            if (entry.getValue().matcher(text).find()) {
                found.add(entry.getKey());
            }
        }
        return found;
    }

    // Used by the old /upload endpoint: 10 points per skill, max 100
    public static int calculateScore(List<String> skills) {
        return Math.min(100, skills.size() * 10);
    }

    // Required skills that the candidate HAS
    public static List<String> findMatched(List<String> required, List<String> resumeSkills) {
        return required.stream().filter(resumeSkills::contains).toList();
    }

    // Required skills that the candidate DOES NOT have
    public static List<String> findMissing(List<String> required, List<String> resumeSkills) {
        return required.stream().filter(s -> !resumeSkills.contains(s)).toList();
    }

    public static int calculateMatchPercentage(int matched, int required) {
        if (required == 0) {
            return 0;
        }
        return (int) Math.round(matched * 100.0 / required);
    }

    public static String getVerdict(int percentage) {
        if (percentage >= 75) return "Strong match";
        if (percentage >= 50) return "Good match";
        if (percentage >= 25) return "Partial match";
        return "Weak match";
    }
}

