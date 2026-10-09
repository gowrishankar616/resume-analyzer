import { useState } from "react";
import AnalyzeForm from "./components/AnalyzeForm.jsx";
import ResultPanel from "./components/ResultPanel.jsx";
import History from "./components/History.jsx";

export default function App() {
  const [tab, setTab] = useState("analyze");
  // The result lives here so it is still there when you come back from History
  const [result, setResult] = useState(null);

  return (
    <div className="app">
      <header className="topbar">
        <h1>Resume Analyzer</h1>
        <nav aria-label="Main">
          <button
            type="button"
            aria-current={tab === "analyze" ? "page" : undefined}
            onClick={() => setTab("analyze")}
          >
            Analyze
          </button>
          <button
            type="button"
            aria-current={tab === "history" ? "page" : undefined}
            onClick={() => setTab("history")}
          >
            History
          </button>
        </nav>
      </header>

      {tab === "analyze" ? (
        <main className="workspace">
          <section className="pane-input" aria-labelledby="input-heading">
            <h2 id="input-heading">Compare a resume to a job</h2>
            <AnalyzeForm onResult={setResult} />
          </section>

          <section
            className="pane-result"
            aria-labelledby="result-heading"
            aria-live="polite"
          >
            <h2 id="result-heading">Result</h2>
            <ResultPanel result={result} />
          </section>
        </main>
      ) : (
        <main>
          <History />
        </main>
      )}
    </div>
  );
}
