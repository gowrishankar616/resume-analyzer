package com.vcube.ResumeAnalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vcube.ResumeAnalyzer.model.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>{

}
