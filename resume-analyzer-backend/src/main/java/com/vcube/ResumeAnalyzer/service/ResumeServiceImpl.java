package com.vcube.ResumeAnalyzer.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vcube.ResumeAnalyzer.dto.AnalysisResponse;
import com.vcube.ResumeAnalyzer.dto.ResumeResponse;
import com.vcube.ResumeAnalyzer.exception.FileException;
import com.vcube.ResumeAnalyzer.exception.ResumeNotFoundException;
import com.vcube.ResumeAnalyzer.model.Resume;
import com.vcube.ResumeAnalyzer.repository.ResumeRepository;
import com.vcube.ResumeAnalyzer.util.DOCXExtractor;
import com.vcube.ResumeAnalyzer.util.EmailExtractor;
import com.vcube.ResumeAnalyzer.util.NameExtractor;
import com.vcube.ResumeAnalyzer.util.PDFExtractor;
import com.vcube.ResumeAnalyzer.util.PhoneExtractor;
import com.vcube.ResumeAnalyzer.util.SkillMatcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    // ---------- 1. Resume only ----------
    @Override
    public ResumeResponse analyzeResume(MultipartFile file) {

        String resumeText = readResumeText(file);

        List<String> skills = SkillMatcher.extractSkills(resumeText);
        int score = SkillMatcher.calculateScore(skills);

        Resume resume = new Resume();
        resume.setName(NameExtractor.extractName(resumeText));
        resume.setEmail(EmailExtractor.extractEmail(resumeText));
        resume.setPhone(PhoneExtractor.extractPhone(resumeText));
        resume.setSkills(String.join(",", skills));
        resume.setResumeText(resumeText);
        resume.setScore(score);

        return toResponse(resumeRepository.save(resume));
    }

    // ---------- 2. Resume + Job description (main feature) ----------
    @Override
    public AnalysisResponse analyzeWithJob(MultipartFile file, String jobDescription) {

        if (jobDescription == null || jobDescription.isBlank()) {
            throw new FileException("Please paste the job description and requirements.");
        }

        String resumeText = readResumeText(file);

        // Skills the job needs
        List<String> required = SkillMatcher.extractSkills(jobDescription);
        if (required.isEmpty()) {
            throw new FileException(
                    "No known skills found in the job description. Mention skills such as Java, SQL, React, etc.");
        }

        // Skills the candidate has
        List<String> resumeSkills = SkillMatcher.extractSkills(resumeText);

        // Compare
        List<String> matched = SkillMatcher.findMatched(required, resumeSkills);
        List<String> missing = SkillMatcher.findMissing(required, resumeSkills);
        int percentage = SkillMatcher.calculateMatchPercentage(matched.size(), required.size());
        String verdict = SkillMatcher.getVerdict(percentage);

        // Save
        Resume resume = new Resume();
        resume.setName(NameExtractor.extractName(resumeText));
        resume.setEmail(EmailExtractor.extractEmail(resumeText));
        resume.setPhone(PhoneExtractor.extractPhone(resumeText));
        resume.setSkills(String.join(",", resumeSkills));
        resume.setMatchedSkills(String.join(",", matched));
        resume.setMissingSkills(String.join(",", missing));
        resume.setResumeText(resumeText);
        resume.setJobDescription(jobDescription);
        resume.setScore(percentage);
        Resume saved = resumeRepository.save(resume);

        return new AnalysisResponse(
                saved.getId(), saved.getName(), saved.getEmail(), saved.getPhone(),
                resumeSkills, required, matched, missing, percentage, verdict);
    }

    @Override
    public ResumeResponse getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found with id: " + id));
        return toResponse(resume);
    }

    @Override
    public List<ResumeResponse> getAllResumes() {
        return resumeRepository.findAll().stream().map(this::toResponse).toList();
    }

    // ---------- helpers ----------

    // Validates the file and returns its text (PDF or DOCX)
    private String readResumeText(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new FileException("Please upload a valid resume.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new FileException("Invalid file name.");
        }
        String lower = fileName.toLowerCase();

        try {
            String text;
            if (lower.endsWith(".pdf")) {
                text = PDFExtractor.extractText(file);
            } else if (lower.endsWith(".docx")) {
                text = DOCXExtractor.extractText(file);
            } else {
                throw new FileException("Only PDF and DOCX files are allowed.");
            }

            if (text == null || text.isBlank()) {
                throw new FileException("No readable text found. Scanned/image resumes are not supported.");
            }
            return text;

        } catch (IOException e) {
            throw new FileException("Unable to process resume: " + e.getMessage(), e);
        }
    }

    private ResumeResponse toResponse(Resume r) {
        List<String> skills = (r.getSkills() == null || r.getSkills().isBlank())
                ? Collections.emptyList()
                : Arrays.asList(r.getSkills().split(","));
        return new ResumeResponse(r.getId(), r.getName(), r.getEmail(), r.getPhone(), skills, r.getScore());
    }
}