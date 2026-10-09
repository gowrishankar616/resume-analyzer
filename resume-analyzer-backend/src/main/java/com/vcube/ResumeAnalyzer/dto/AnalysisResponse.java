package com.vcube.ResumeAnalyzer.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;

    private List<String> resumeSkills;    // all skills found in the resume
    private List<String> requiredSkills;  // skills found in the job description
    private List<String> matchedSkills;   // required skills the candidate has
    private List<String> missingSkills;   // required skills the candidate lacks

    private int matchPercentage;          // 0 - 100
    private String verdict;               // Strong / Good / Partial / Weak match
}
