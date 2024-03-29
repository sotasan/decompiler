package com.sotasan.decompiler.views;

import com.sotasan.decompiler.controllers.TabController;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

@Getter
public class TabView extends JPanel implements MouseWheelListener {

    private final RSyntaxTextArea textArea;
    private final RTextScrollPane scrollPane;
    @Setter private TabController controller;

    @SneakyThrows
    public TabView() {
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

}