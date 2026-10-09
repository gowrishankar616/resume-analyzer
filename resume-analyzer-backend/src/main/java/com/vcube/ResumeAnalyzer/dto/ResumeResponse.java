package com.vcube.ResumeAnalyzer.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<String> skills;
    private int score;
}

