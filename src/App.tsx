import { useEffect, useState } from "react";
import { invoke } from "@tauri-apps/api/core";
import Editor from "@monaco-editor/react";
import { Group, Panel, Separator } from "react-resizable-panels";
import { NodeRendererProps, Tree } from "react-arborist";
import "./App.css";

type FileNode = {
  id: string;
  name: string;
  children?: FileNode[];
};

const sampleTree: FileNode[] = [
  {
    id: "moe",
    name: "moe",
    children: [
      {
        id: "moe.sota",
        name: "sota",
        children: [
          {
            id: "moe.sota.decompiler",
            name: "decompiler",
            children: [
              { id: "Main.class", name: "Main.class" },
              { id: "Application.class", name: "Application.class" },
            ],
          },
        ],
      },
    ],
  },
];

function Node({ node, style, dragHandle }: NodeRendererProps<FileNode>) {
  return (
    <div
      ref={dragHandle}
      style={style}
      onClick={() => node.toggle()}
      className="cursor-pointer truncate px-1 text-sm hover:bg-nord-2"
    >
      <span className="inline-block w-4 text-nord-9">
        {node.isInternal ? (node.isOpen ? "▾" : "▸") : ""}
      </span>
      {node.data.name}
    </div>
  );
}

function App() {
  const [greeting, setGreeting] = useState("loading…");

  useEffect(() => {
    invoke<string>("greet_from_jvm")
      .then(setGreeting)
      .catch((err) => setGreeting(`error: ${err}`));
  }, []);

  useEffect(() => {
    const onContextMenu = (e: MouseEvent) => e.preventDefault();
    document.addEventListener("contextmenu", onContextMenu);
    return () => document.removeEventListener("contextmenu", onContextMenu);
  }, []);

  return (
    <div className="flex h-screen flex-col">
      <header className="px-4 py-2 font-sans text-sm">
        JVM says: {greeting}
      </header>
      <Group orientation="horizontal" className="flex min-h-0 flex-1">
        <Panel defaultSize={25} minSize={15}>
          <Tree<FileNode> initialData={sampleTree} openByDefault={false}>
            {Node}
          </Tree>
        </Panel>
        <Separator className="w-px bg-nord-2 transition-colors hover:bg-nord-12" />
        <Panel>
          <Editor
            defaultLanguage="java"
            defaultValue={`public class Hello {\n    public static void main(String[] args) {\n        System.out.println("Hello from Monaco");\n    }\n}\n`}
            theme="nord"
            loading={null}
            options={{
              readOnly: true,
              minimap: { enabled: false },
              contextmenu: false,
              fontSize: 16,
            }}
          />
        </Panel>
      </Group>
    </div>
  );
}

export default App;
