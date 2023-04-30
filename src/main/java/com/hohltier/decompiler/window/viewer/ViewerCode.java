package com.hohltier.decompiler.window.viewer;

import javafx.scene.control.Tab;
import org.fxmisc.flowless.ScaledVirtualized;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ViewerCode extends Tab {

    private final Type type = Type.JAVA;

    public ViewerCode(String name, String text) {
        super(name);

        CodeArea codeArea = new CodeArea();
        codeArea.setEditable(false);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        try {
            // TODO: codeArea.textProperty().addListener((observable, oldValue, newValue) -> codeArea.setStyleSpans(0, Lexer.getSyntaxHighlighting(codeArea.getText())));
            codeArea.replaceText(text);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            codeArea.replaceText(stringWriter.toString().trim());
        }

        setContent(new VirtualizedScrollPane<>(new ScaledVirtualized<>(codeArea)));
        setContextMenu(new CloseMenu(this));
    }

    private enum Type {

        JAVA, BYTECODE

    }

}