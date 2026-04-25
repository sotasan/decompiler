import { loader } from "@monaco-editor/react";
import * as monaco from "monaco-editor";
import EditorWorker from "monaco-editor/esm/vs/editor/editor.worker?worker";
import theme from "./theme.json";

self.MonacoEnvironment = {
  getWorker: () => new EditorWorker(),
};

monaco.editor.defineTheme(
  "nord",
  theme as monaco.editor.IStandaloneThemeData,
);

loader.config({ monaco });
