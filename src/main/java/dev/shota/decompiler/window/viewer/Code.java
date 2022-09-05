package dev.shota.decompiler.window.viewer;

import dev.shota.decompiler.JavaLexer;
import dev.shota.decompiler.jvm.Decompiler;
import javafx.scene.control.Tab;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.fxmisc.flowless.ScaledVirtualized;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import java.io.ByteArrayInputStream;

public class Code extends Tab {

    private final Type type = Type.JAVA;

    @SneakyThrows
    public Code(String name, boolean clazz, byte[] data) {
        super(name);

        Lexer lexer = new JavaLexer(CharStreams.fromStream((new ByteArrayInputStream(data))));

        CodeArea codeArea = new CodeArea(new Decompiler(data).getCode());
        codeArea.setEditable(false);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        ScaledVirtualized<CodeArea> scaledVirtualized = new ScaledVirtualized<>(codeArea);

        setContent(new VirtualizedScrollPane<>(scaledVirtualized));
    }

    private enum Type {

        JAVA, BYTECODE

    }

}