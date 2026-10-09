package com.vcube.ResumeAnalyzer.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vcube.ResumeAnalyzer.dto.AnalysisResponse;
import com.vcube.ResumeAnalyzer.dto.ResumeResponse;

public interface ResumeService {

    // Resume only (no job description)
    ResumeResponse analyzeResume(MultipartFile file);

    // Resume + job description -> match result
    AnalysisResponse analyzeWithJob(MultipartFile file, String jobDescription);

    ResumeResponse getResumeById(Long id);

    List<ResumeResponse> getAllResumes();
}