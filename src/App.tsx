import { useEffect, useState } from "react";
import { invoke } from "@tauri-apps/api/core";
import Editor from "@monaco-editor/react";
import "./App.css";

function App() {
  const [greeting, setGreeting] = useState("loading…");

  useEffect(() => {
    invoke<string>("greet_from_jvm")
      .then(setGreeting)
      .catch((err) => setGreeting(`error: ${err}`));
  }, []);

  return (
    <div style={{ display: "flex", flexDirection: "column", height: "100vh" }}>
      <header
        style={{
          padding: "8px 16px",
          borderBottom: "1px solid #333",
          fontFamily: "system-ui, sans-serif",
          fontSize: 13,
        }}
      >
        JVM says: {greeting}
      </header>
      <div style={{ flex: 1, minHeight: 0 }}>
        <Editor
          defaultLanguage="java"
          defaultValue={`public class Hello {\n    public static void main(String[] args) {\n        System.out.println("Hello from Monaco");\n    }\n}\n`}
          theme="vs-dark"
          loading={null}
          options={{
            readOnly: true,
            minimap: { enabled: false },
            contextmenu: false,
          }}
        />
      </div>
    </div>
  );
}

export default App;
