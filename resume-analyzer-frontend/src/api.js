// All calls to the Spring Boot backend live here.

async function request(url, options) {
  let res;
  try {
    res = await fetch(url, options);
  } catch {
    throw new Error(
      "Cannot reach the server. Check that the Spring Boot app is running on port 8080."
    );
  }

  let body = null;
  try {
    body = await res.json();
  } catch {
    // response had no JSON body
  }

  if (!res.ok) {
    if (!body && res.status >= 500) {
      throw new Error(
        "Cannot reach the server. Check that the Spring Boot app is running on port 8080."
      );
    }
    // ErrorResponse from GlobalExceptionHandler has a "message" field
    throw new Error(body?.message || "Request failed (" + res.status + ").");
  }
  return body;
}

// POST /resume/analyze  (form-data: file + jobDescription)
export function analyzeResume(file, jobDescription) {
  const data = new FormData();
  data.append("file", file);
  data.append("jobDescription", jobDescription);
  return request("/resume/analyze", { method: "POST", body: data });
}

// GET /resume/all
export function getAllResumes() {
  return request("/resume/all");
}
