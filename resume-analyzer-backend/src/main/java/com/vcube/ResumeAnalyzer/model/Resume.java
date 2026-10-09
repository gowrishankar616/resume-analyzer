package com.vcube.ResumeAnalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "resumes")
@Getter
@Setter
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    @Column(length = 5000)
    private String skills;

    @Column(length = 5000)
    private String matchedSkills;

    @Column(length = 5000)
    private String missingSkills;

    @Lob
    private String resumeText;

    @Lob
    private String jobDescription;

    private int score;
}
