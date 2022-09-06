package dev.shota.decompiler.window.viewer;

import dev.shota.decompiler.jvm.Decompiler;
import javafx.scene.control.Tab;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Code extends Tab {

    private final Type type = Type.JAVA;

    public Code(String name, byte[] data, boolean clazz) {
        super(name);

        CodeArea codeArea = new CodeArea();
        codeArea.setEditable(false);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        try {
            String code = new Decompiler(data).getCode();
            codeArea.textProperty().addListener((observable, oldValue, newValue) -> codeArea.setStyleSpans(0, Lexer.getSyntaxHighlighting(codeArea.getText())));
            codeArea.replaceText(code);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            codeArea.replaceText(stringWriter.toString().trim());
        }

        setContent(new VirtualizedScrollPane<>(codeArea));
    }

    private enum Type {

        JAVA, BYTECODE

    }

}