function CheckIcon() {
  return (
    <svg width="16" height="16" viewBox="0 0 16 16" aria-hidden="true">
      <path d="M3 8.5l3.2 3L13 4.5" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
    </svg>
  );
}

function CrossIcon() {
  return (
    <svg width="16" height="16" viewBox="0 0 16 16" aria-hidden="true">
      <path d="M4 4l8 8M12 4l-8 8" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" />
    </svg>
  );
}

export default function ResultPanel({ result }) {
  if (!result) {
    return (
      <p className="empty">
        Paste a job description and choose a resume. The result shows which
        required skills the candidate has and which are missing.
      </p>
    );
  }

  const required = result.requiredSkills || [];
  const matched = new Set(result.matchedSkills || []);
  const other = (result.resumeSkills || []).filter((s) => !required.includes(s));

  return (
    <div>
      <h3 className="headline">
        {matched.size} of {required.length} required skills found
      </h3>
      <p className="sub">
        {result.verdict} · {result.matchPercentage}% match
      </p>

      {/* One segment per required skill: solid = found, hatched = missing */}
      <div className="segments" aria-hidden="true">
        {required.map((skill, i) => (
          <span
            key={skill}
            className={"seg " + (matched.has(skill) ? "found" : "gap")}
            style={{ "--i": i }}
          />
        ))}
      </div>

      <ul className="ledger">
        {required.map((skill) => {
          const isFound = matched.has(skill);
          return (
            <li key={skill}>
              <span>{skill}</span>
              <span className={"status " + (isFound ? "found" : "gap")}>
                {isFound ? <CheckIcon /> : <CrossIcon />}
                {isFound ? "Found" : "Missing"}
              </span>
            </li>
          );
        })}
      </ul>

      {other.length > 0 && (
        <p className="also">
          <strong>Also in the resume:</strong> {other.join(", ")}
        </p>
      )}

      <dl className="contact">
        <dt>Name</dt>
        <dd>{result.name || "Not found"}</dd>
        <dt>Email</dt>
        <dd>{result.email || "Not found"}</dd>
        <dt>Phone</dt>
        <dd>{result.phone || "Not found"}</dd>
      </dl>
    </div>
  );
}
