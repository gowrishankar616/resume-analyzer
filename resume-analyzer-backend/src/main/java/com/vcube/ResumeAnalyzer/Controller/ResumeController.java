package com.vcube.ResumeAnalyzer.Controller;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vcube.ResumeAnalyzer.dto.AnalysisResponse;
import com.vcube.ResumeAnalyzer.dto.ResumeRequest;
import com.vcube.ResumeAnalyzer.dto.ResumeResponse;
import com.vcube.ResumeAnalyzer.service.ResumeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    // MAIN FEATURE: POST /resume/analyze  (form-data: file + jobDescription)
    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AnalysisResponse analyze(@ModelAttribute ResumeRequest request) {
        return resumeService.analyzeWithJob(request.getFile(), request.getJobDescription());
    }

    // Resume only: POST /resume/upload  (form-data: file)
    @PostMapping("/upload")
    public ResumeResponse uploadResume(@RequestParam("file") MultipartFile file) {
        return resumeService.analyzeResume(file);
    }

    @GetMapping("/{id}")
    public ResumeResponse getResume(@PathVariable Long id) {
        return resumeService.getResumeById(id);
    }

    @GetMapping("/all")
    public List<ResumeResponse> getAllResumes() {
        return resumeService.getAllResumes();
    }
}