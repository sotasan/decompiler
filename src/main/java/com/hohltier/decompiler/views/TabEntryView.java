package com.hohltier.decompiler.views;

import com.hohltier.decompiler.controllers.ViewerController;
import com.hohltier.decompiler.models.FileModel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class TabEntryView extends JPanel implements MouseWheelListener {

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
        textArea.addMouseWheelListener(this);
        textArea.setBracketMatchingEnabled(false);
        textArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textArea.setDropTarget(null);
        textArea.setEditable(false);
        textArea.setHighlightCurrentLine(false);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        // TODO: Controller
        setText(fileModel);

        scrollPane = new RTextScrollPane(textArea);
        theme.apply(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);

        setFontSize(getFont().getSize() + 2);
    }

    // TODO: global font size
    @Override
    public void mouseWheelMoved(@NotNull MouseWheelEvent event) {
        if (event.isControlDown() || event.isMetaDown())
            setFontSize(Math.min(50, Math.max(10, textArea.getFont().getSize() - event.getWheelRotation())));
        else
            for (MouseWheelListener listener : scrollPane.getMouseWheelListeners())
                listener.mouseWheelMoved(event);
    }

    private void setFontSize(float size) {
        Font font = textArea.getFont().deriveFont(size);
        textArea.setFont(font);
        scrollPane.getGutter().setLineNumberFont(font);
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