package com.vcube.ResumeAnalyzer.util;

import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

public class PDFExtractor {

    public static String extractText(MultipartFile file) throws IOException {
        // try-with-resources closes the document even if an error occurs
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            return new PDFTextStripper().getText(document);
        }
    }
}

