package moe.sota.decompiler.menus.file;

import com.formdev.flatlaf.extras.components.FlatMenuItem;
import moe.sota.decompiler.controllers.TabsController;
import moe.sota.decompiler.controllers.WindowController;
import moe.sota.decompiler.models.FileModel;
import moe.sota.decompiler.services.LanguageService;
import moe.sota.decompiler.types.ImageType;
import moe.sota.decompiler.views.TabView;
import moe.sota.decompiler.views.TabsView;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileFind extends FlatMenuItem implements ActionListener {

    private static FindDialog dialog;

    public FileFind() {
        setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()
        ));
        setMnemonic(KeyEvent.VK_F);
        setText(LanguageService.getTranslation("file.find"));
        addActionListener(this);
    }

    private Component parent() {
        return WindowController.INSTANCE.getComponent();
    }

    private JFrame ownerFrame() {
        Component c = parent();
        return (c instanceof JFrame) ? (JFrame) c : null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TabView tab = getSelectedTabView();
        if (tab == null) {
            JOptionPane.showMessageDialog(
                    parent(),
                    LanguageService.getTranslation("empty"),
                    LanguageService.getTranslation("file.find"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        FileModel fm = tab.getFileModel();
        if (fm.getType() instanceof ImageType) {
            JOptionPane.showMessageDialog(
                    parent(),
                    LanguageService.getTranslation("file.find.notSupported"),
                    LanguageService.getTranslation("file.find"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        RSyntaxTextArea textArea = tab.getTextArea();

        if (dialog == null || !dialog.isDisplayable()) {
            dialog = new FindDialog(ownerFrame());
        }

        dialog.attach(textArea);
        dialog.setLocationRelativeTo(parent());
        dialog.setVisible(true);
        dialog.focusQuery();
    }

    private TabView getSelectedTabView() {
        Component c = TabsController.INSTANCE.getComponent();
        if (!(c instanceof TabsView)) return null;

        TabsView tabs = (TabsView) c;
        Component selected = tabs.getSelectedComponent();
        if (selected instanceof TabView) {
            return (TabView) selected;
        }
        return null;
    }

    // -------------------------
    // Find dialog
    // -------------------------

    private static class FindDialog extends JDialog {

        private RSyntaxTextArea textArea;

        private final JTextField queryField = new JTextField();
        private final JButton prevButton = new JButton(LanguageService.getTranslation("file.find.prev"));
        private final JButton nextButton = new JButton(LanguageService.getTranslation("file.find.next"));
        private final JLabel statusLabel = new JLabel(" ");

        // Reuse the global key as requested
        private final JCheckBox caseSensitiveBox =
                new JCheckBox(LanguageService.getTranslation("file.search.caseSensitive"));

        private final List<Integer> matches = new ArrayList<>();
        private final List<Object> highlightTags = new ArrayList<>();
        private int currentIndex = -1;

        private final Highlighter.HighlightPainter painter =
                new DefaultHighlighter.DefaultHighlightPainter(getMarkColor());

        FindDialog(JFrame owner) {
            super(owner, LanguageService.getTranslation("file.find"), false);
            setModalityType(ModalityType.MODELESS);

            setLayout(new BorderLayout(10, 10));
            JComponent root = (JComponent) getContentPane();
            root.setBorder(new EmptyBorder(14, 14, 12, 14));

            // Header
            JLabel title = new JLabel(LanguageService.getTranslation("file.find"));
            title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize2D() + 2f));
            title.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Input row
            JPanel row = new JPanel(new GridBagLayout());
            row.setAlignmentX(Component.LEFT_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 0, 6);
            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            queryField.setColumns(28);
            row.add(queryField, gbc);

            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;

            gbc.gridx++;
            row.add(prevButton, gbc);

            gbc.gridx++;
            row.add(nextButton, gbc);

            // Options row
            caseSensitiveBox.setFocusPainted(false);
            caseSensitiveBox.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel optionsRow = new JPanel();
            optionsRow.setLayout(new BoxLayout(optionsRow, BoxLayout.X_AXIS));
            optionsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            optionsRow.setBorder(new EmptyBorder(6, 0, 0, 0));
            optionsRow.add(caseSensitiveBox);
            optionsRow.add(Box.createHorizontalGlue());

            // Status row (subtle)
            statusLabel.setBorder(new EmptyBorder(6, 0, 0, 0));

            JPanel top = new JPanel();
            top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
            top.add(title);
            top.add(Box.createRigidArea(new Dimension(0, 8)));
            top.add(row);
            top.add(optionsRow);
            top.add(statusLabel);

            add(top, BorderLayout.CENTER);

            // Behaviors
            queryField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { recompute(); }
                @Override public void removeUpdate(DocumentEvent e) { recompute(); }
                @Override public void changedUpdate(DocumentEvent e) { recompute(); }
            });

            caseSensitiveBox.addActionListener(e -> recompute());

            nextButton.addActionListener(e -> goNext());
            prevButton.addActionListener(e -> goPrev());

            getRootPane().setDefaultButton(nextButton);

            // ESC closes
            getRootPane().registerKeyboardAction(
                    e -> close(),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                    JComponent.WHEN_IN_FOCUSED_WINDOW
            );

            // Enter = next
            queryField.addActionListener(e -> goNext());

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    clearHighlights();
                }
            });

            pack();
            setMinimumSize(new Dimension(420, getHeight()));
        }

        void attach(RSyntaxTextArea textArea) {
            // Clear old highlights when switching target
            clearHighlights();

            this.textArea = textArea;
            recompute();
        }

        void focusQuery() {
            SwingUtilities.invokeLater(() -> {
                queryField.requestFocusInWindow();
                queryField.selectAll();
            });
        }

        private void close() {
            clearHighlights();
            clearSelection();
            dispose();
        }

        private void recompute() {
            clearHighlights();
            matches.clear();
            currentIndex = -1;

            if (textArea == null) {
                setStatus(0);
                return;
            }

            String query = queryField.getText();
            if (query == null || query.isBlank()) {
                setStatus(0);
                return;
            }

            boolean cs = caseSensitiveBox.isSelected();

            String text = textArea.getText();
            String hay = cs ? text : text.toLowerCase(Locale.ROOT);
            String needle = cs ? query : query.toLowerCase(Locale.ROOT);

            int idx = 0;
            while (true) {
                idx = hay.indexOf(needle, idx);
                if (idx < 0) break;

                matches.add(idx);
                addHighlight(idx, idx + query.length());
                idx += query.length();

                // safety guard for huge files
                if (matches.size() > 5000) break;
            }

            setStatus(matches.size());

            if (!matches.isEmpty()) {
                int startFrom = nextAnchor();
                int first = indexFirstAtOrAfter(startFrom);
                currentIndex = (first >= 0) ? first : 0;
                revealCurrent();
            }
        }

        private void setStatus(int count) {
            statusLabel.setText(String.format(
                    LanguageService.getTranslation("file.find.matches"),
                    count
            ));
            prevButton.setEnabled(count > 0);
            nextButton.setEnabled(count > 0);
        }

        // Anchor respects user clicking/caret/selection
        private int nextAnchor() {
            if (textArea == null) return 0;
            int s = textArea.getSelectionStart();
            int e = textArea.getSelectionEnd();
            return (e > s) ? e : textArea.getCaretPosition();
        }

        private int prevAnchor() {
            if (textArea == null) return 0;
            int s = textArea.getSelectionStart();
            int e = textArea.getSelectionEnd();
            return (e > s) ? s : textArea.getCaretPosition();
        }

        private int indexFirstAtOrAfter(int pos) {
            for (int i = 0; i < matches.size(); i++) {
                if (matches.get(i) >= pos) return i;
            }
            return -1;
        }

        private int indexLastBefore(int pos) {
            for (int i = matches.size() - 1; i >= 0; i--) {
                if (matches.get(i) < pos) return i;
            }
            return -1;
        }

        private void goNext() {
            if (matches.isEmpty() || textArea == null) return;

            String query = queryField.getText();
            if (query == null || query.isBlank()) return;

            int anchor = nextAnchor();
            int idx = indexFirstAtOrAfter(anchor);
            if (idx < 0) idx = 0; // wrap

            currentIndex = idx;
            revealCurrent();
        }

        private void goPrev() {
            if (matches.isEmpty() || textArea == null) return;

            String query = queryField.getText();
            if (query == null || query.isBlank()) return;

            int anchor = prevAnchor();
            int idx = indexLastBefore(anchor);
            if (idx < 0) idx = matches.size() - 1; // wrap

            currentIndex = idx;
            revealCurrent();
        }

        private void revealCurrent() {
            if (textArea == null || matches.isEmpty() || currentIndex < 0) return;

            String query = queryField.getText();
            if (query == null || query.isBlank()) return;

            int start = matches.get(currentIndex);
            int end = Math.min(textArea.getText().length(), start + query.length());

            textArea.requestFocusInWindow();
            textArea.select(start, end);

            try {
                Rectangle2D r = textArea.modelToView2D(start);
                if (r != null) {
                    textArea.scrollRectToVisible(r.getBounds());
                }
            } catch (Exception ignored) {
            }
        }

        private void addHighlight(int start, int end) {
            if (textArea == null) return;
            try {
                Object tag = textArea.getHighlighter().addHighlight(start, end, painter);
                highlightTags.add(tag);
            } catch (Exception ignored) {
            }
        }

        private void clearSelection() {
            if (textArea != null) {
                int caret = textArea.getCaretPosition();
                textArea.select(caret, caret);
            }
        }

        private void clearHighlights() {
            if (textArea == null) return;

            Highlighter h = textArea.getHighlighter();
            for (Object tag : highlightTags) {
                try {
                    h.removeHighlight(tag);
                } catch (Exception ignored) {
                }
            }
            highlightTags.clear();
        }

        private static Color getMarkColor() {
            Color accent = UIManager.getColor("Component.accentColor");
            if (accent == null) accent = UIManager.getColor("TextArea.selectionBackground");
            if (accent == null) accent = UIManager.getColor("textHighlight");
            if (accent == null) accent = new Color(255, 215, 0);

            // soft translucent overlay works in light/dark themes
            return new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 90);
        }
    }
}
