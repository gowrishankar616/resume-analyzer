import { useCallback, useEffect, useState } from "react";
import { getAllResumes } from "../api.js";

export default function History() {
  const [rows, setRows] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const load = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      const data = await getAllResumes();
      setRows([...data].sort((a, b) => b.id - a.id)); // newest first
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  return (
    <section aria-labelledby="history-heading">
      <div className="history-head">
        <h2 id="history-heading">Analyzed resumes</h2>
        <button type="button" className="secondary" onClick={load} disabled={loading}>
          {loading ? "Loading…" : "Refresh list"}
        </button>
      </div>

      {error && (
        <p className="error" role="alert">
          {error}
        </p>
      )}

      {!error && rows && rows.length === 0 && (
        <p className="empty">
          No resumes analyzed yet. Analyze one and it will appear here.
        </p>
      )}

      {rows && rows.length > 0 && (
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Skills</th>
                <th>Score</th>
              </tr>
            </thead>
            <tbody>
              {rows.map((r) => (
                <tr key={r.id}>
                  <td>{r.id}</td>
                  <td>{r.name || "Not found"}</td>
                  <td>{r.email || "Not found"}</td>
                  <td>{r.phone || "Not found"}</td>
                  <td>{r.skills && r.skills.length ? r.skills.join(", ") : "None found"}</td>
                  <td>{r.score}%</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}
