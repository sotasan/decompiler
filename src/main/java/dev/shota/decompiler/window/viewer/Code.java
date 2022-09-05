package dev.shota.decompiler.window.viewer;

import dev.shota.decompiler.jvm.Decompiler;
import javafx.scene.control.Tab;
import lombok.SneakyThrows;
import org.fxmisc.flowless.ScaledVirtualized;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class Code extends Tab {

    private final Type type = Type.JAVA;

    @SneakyThrows
    public Code(String name, boolean clazz, byte[] data) {
        super(name);

        CodeArea codeArea = new CodeArea();
        codeArea.setEditable(false);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.textProperty().addListener((observable, oldValue, newValue) -> codeArea.setStyleSpans(0, Lexer.getSyntaxHighlighting(codeArea.getText())));
        codeArea.replaceText(new Decompiler(data).getCode());

        ScaledVirtualized<CodeArea> scaledVirtualized = new ScaledVirtualized<>(codeArea);

        setContent(new VirtualizedScrollPane<>(scaledVirtualized));
    }

    private enum Type {

        JAVA, BYTECODE

    }

}