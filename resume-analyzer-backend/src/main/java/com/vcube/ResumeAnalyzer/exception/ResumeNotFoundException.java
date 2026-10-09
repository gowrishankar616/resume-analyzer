package com.vcube.ResumeAnalyzer.exception;

public class ResumeNotFoundException extends RuntimeException {

  
	private static final long serialVersionUID = 1L;

	public ResumeNotFoundException(String message) {
        super(message);
    }
}
