import { useState } from "react";
import { analyzeResume } from "../api.js";

const MAX_BYTES = 5 * 1024 * 1024; // matches spring.servlet.multipart.max-file-size

function validateFile(file) {
  const name = file.name.toLowerCase();
  if (!name.endsWith(".pdf") && !name.endsWith(".docx")) {
    return "Upload a PDF or DOCX file.";
  }
  if (file.size > MAX_BYTES) {
    return "The file is larger than 5 MB. Upload a smaller resume.";
  }
  return null;
}

export default function AnalyzeForm({ onResult }) {
  const [jobDescription, setJobDescription] = useState("");
  const [file, setFile] = useState(null);
  const [dragging, setDragging] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  function pickFile(selected) {
    if (!selected) return;
    const problem = validateFile(selected);
    if (problem) {
      setError(problem);
      setFile(null);
      return;
    }
    setError("");
    setFile(selected);
  }

  function handleDrop(e) {
    e.preventDefault();
    setDragging(false);
    pickFile(e.dataTransfer.files[0]);
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (!jobDescription.trim()) {
      setError("Paste the job description and requirements.");
      return;
    }
    if (!file) {
      setError("Choose a resume to analyze.");
      return;
    }

    setError("");
    setLoading(true);
    try {
      const data = await analyzeResume(file, jobDescription);
      onResult(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} noValidate>
      <label className="field-label" htmlFor="jd">
        Job description and requirements
      </label>
      <textarea
        id="jd"
        value={jobDescription}
        onChange={(e) => setJobDescription(e.target.value)}
        placeholder="Example: Java developer with Spring Boot, Hibernate, MySQL and REST API experience. Git and Docker are a plus."
      />

      <span className="field-label" id="file-label">
        Resume
      </span>
      <label
        className={"dropzone" + (dragging ? " dragging" : "")}
        onDragOver={(e) => {
          e.preventDefault();
          setDragging(true);
        }}
        onDragLeave={() => setDragging(false)}
        onDrop={handleDrop}
      >
        <input
          type="file"
          className="sr-only"
          accept=".pdf,.docx"
          aria-labelledby="file-label"
          onChange={(e) => pickFile(e.target.files[0])}
        />
        <span className="dropzone-main">
          {file ? file.name : "Drop a resume here or click to choose a file"}
        </span>
        <span className="dropzone-hint">PDF or DOCX, up to 5 MB</span>
      </label>

      {error && (
        <p className="error" role="alert">
          {error}
        </p>
      )}

      <button type="submit" className="primary" disabled={loading}>
        {loading ? "Analyzing…" : "Analyze resume"}
      </button>
    </form>
  );
}
