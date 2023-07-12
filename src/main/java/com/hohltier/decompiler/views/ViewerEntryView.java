package com.hohltier.decompiler.views;

import com.hohltier.decompiler.utils.UIUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import javax.swing.*;
import java.awt.*;

public class ViewerEntryView extends JPanel {

    public ViewerEntryView(String name, String text) {
        setLayout(new BorderLayout());

        Font font = new Font("JetBrains Mono", Font.PLAIN, UIUtils.getDefaultFont().getSize() + 3);

        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setCurrentLineHighlightColor(Color.WHITE);
        textArea.setEditable(false);
        textArea.setFont(font);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setText(text);

        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        scrollPane.getGutter().setLineNumberFont(font);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
    }

}