import { useEffect, useState } from "react";
import { invoke } from "@tauri-apps/api/core";
import "./App.css";

function App() {
  const [jvmMessage, setJvmMessage] = useState("loading...");

  useEffect(() => {
    invoke<string>("greet_from_jvm")
      .then(setJvmMessage)
      .catch((err) => setJvmMessage(`error: ${err}`));
  }, []);

  return (
    <main className="container">
      <h1>Decompiler</h1>
      <p>JVM says: {jvmMessage}</p>
    </main>
  );
}

export default App;
