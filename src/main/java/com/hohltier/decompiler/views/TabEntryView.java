package com.hohltier.decompiler.views;

import com.hohltier.decompiler.controllers.ViewerController;
import com.hohltier.decompiler.models.FileModel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import javax.swing.*;
import java.awt.*;

public class TabEntryView extends JPanel {

    @Getter private final FileModel fileModel;
    @Getter private final RSyntaxTextArea textArea;
    @Getter private final RTextScrollPane scrollPane;

    @SneakyThrows
    public TabEntryView(FileModel fileModel) {
        this.fileModel = fileModel;
        setLayout(new BorderLayout());

        Theme theme = Theme.load(getClass().getClassLoader().getResourceAsStream("themes/RSyntaxTheme.xml"));

        textArea = new RSyntaxTextArea();
        theme.apply(textArea);
        textArea.setBracketMatchingEnabled(false);
        textArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textArea.setEditable(false);
        textArea.setHighlightCurrentLine(false);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        // TODO: Controller
        setText(fileModel);

        scrollPane = new RTextScrollPane(textArea);
        theme.apply(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
    }

    public void setText(FileModel fileModel) {
        try {
            textArea.setText(ViewerController.getINSTANCE().getTransformer().getInstance().transform(fileModel));
            textArea.setCaretPosition(0);
        } catch (Exception e) {
            textArea.setText(e.getMessage());
        }
    }

}