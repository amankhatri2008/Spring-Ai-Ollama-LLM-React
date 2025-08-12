import React, { useState, useEffect } from 'react';
import axios from "axios";
import './App.css';

function App() {
  const [message, setMessage] = useState('');
const [file, setFile] = useState(null);
  const [policyId, setPolicyId] = useState(null);
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [progress, setProgress] = useState(0);


const upload = async () => {
    if (!file) return alert("Pick a file");
    const form = new FormData();
    form.append("file", file);
    try {
      const res = await axios.post("/api/policies/upload", form, {
        headers: { "Content-Type": "multipart/form-data" },
        onUploadProgress: (ev) => {
          setProgress(Math.round((ev.loaded * 100) / ev.total));
        }
      });
      setPolicyId(res.data.policyId || res.data.id);
      setProgress(0);
      alert("Uploaded");
    } catch (e) {
      console.error(e);
      alert("Upload failed");
    }
  };

  const ask = async () => {
    if (!policyId) return alert("Upload first");
    setAnswer("Thinking...Response could be slow because it a local model");
    try {
      const res = await axios.post(`/api/policies/${policyId}/ask`, { question });
      setAnswer(res.data);
    } catch (e) {
      console.error(e);
      setAnswer("Error: " + (e?.response?.data || e.message));
    }
  };

  return (
    <div style={{maxWidth: 720, margin: '1rem auto'}}>
      <h2>Spring AI + Ollama + Fine Tune Model + Embedding: mxbai-embed-large + LLM Chat Model: gemma3:latest + Gen AI + Spring Boot + React JS + GCP</h2>
      <h3>Upload a File</h3>
      <input type="file" onChange={e => setFile(e.target.files[0])} />
      <button onClick={upload}>Upload</button>
      {progress > 0 && <div>Upload: {progress}%</div>}

      <hr />

      <h3>Ask about Recent File upload</h3>
      <textarea value={question} onChange={e => setQuestion(e.target.value)} rows={4} style={{width: '100%'}}/>
      <button onClick={ask} disabled={!policyId}>Ask</button>

      <h4>Answer</h4>
      <pre style={{whiteSpace:'pre-wrap'}}>{answer}</pre>
    </div>
  );
}

export default App;