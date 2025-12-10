package moe.sota.decompiler.menus.file;

import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.formdev.flatlaf.extras.components.FlatTree;
import moe.sota.decompiler.controllers.TabsController;
import moe.sota.decompiler.controllers.TreeController;
import moe.sota.decompiler.controllers.WindowController;
import moe.sota.decompiler.models.FileModel;
import moe.sota.decompiler.services.LanguageService;
import moe.sota.decompiler.types.ClassType;
import moe.sota.decompiler.types.Type;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class FileSearch extends FlatMenuItem implements ActionListener {

    private static final int MAX_LINE_MATCHES_PER_FILE = 80;
    private static final int MAX_TOTAL_ITEMS = 2000;

    public FileSearch() {
        setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | InputEvent.SHIFT_DOWN_MASK
        ));
        setMnemonic(KeyEvent.VK_F);
        setText(LanguageService.getTranslation("file.search"));
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
        List<FileModel> files = collectFiles();
        if (files.isEmpty()) {
            JOptionPane.showMessageDialog(
                    parent(),
                    LanguageService.getTranslation("empty"),
                    LanguageService.getTranslation("file.search"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        SearchDialog dialog = new SearchDialog(ownerFrame(), files);
        dialog.setVisible(true);
    }

    private List<FileModel> collectFiles() {
        FlatTree tree = TreeController.getINSTANCE().view.getTree();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        Object rootObj = model.getRoot();
        if (!(rootObj instanceof DefaultMutableTreeNode))
            return List.of();

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) rootObj;

        List<FileModel> files = new ArrayList<>();
        Enumeration<?> enumeration = root.depthFirstEnumeration();
        while (enumeration.hasMoreElements()) {
            Object nodeObj = enumeration.nextElement();
            if (nodeObj instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeObj;
                Object user = node.getUserObject();
                if (user instanceof FileModel)
                    files.add((FileModel) user);
            }
        }
        return files;
    }

    // -------------------------
    // Search data structures
    // -------------------------

    private static class LineMatch {
        final int lineNumber; // 1-based
        final String lineText;

        LineMatch(int lineNumber, String lineText) {
            this.lineNumber = lineNumber;
            this.lineText = lineText;
        }
    }

    private static class FileResult {
        final FileModel file;
        final List<LineMatch> lines;

        FileResult(FileModel file, List<LineMatch> lines) {
            this.file = file;
            this.lines = lines;
        }

        int count() {
            return lines.size();
        }
    }

    private interface ResultItem {
        FileModel file();
        boolean isHeader();
        String toHtml(String query, boolean caseSensitive);
    }

    private static class FileHeaderItem implements ResultItem {
        final FileResult result;

        FileHeaderItem(FileResult result) {
            this.result = result;
        }

        @Override public FileModel file() { return result.file; }
        @Override public boolean isHeader() { return true; }

        @Override
        public String toHtml(String query, boolean caseSensitive) {
            String path = escapeHtml(result.file.getPath());
            return "<html><b>" + path + "</b> &nbsp; <span style='opacity:0.7'>(" +
                    result.count() + ")</span></html>";
        }

    }

    private static class LineItem implements ResultItem {
        final FileModel file;
        final LineMatch match;

        LineItem(FileModel file, LineMatch match) {
            this.file = file;
            this.match = match;
        }

        @Override public FileModel file() { return file; }
        @Override public boolean isHeader() { return false; }

        @Override
        public String toHtml(String query, boolean caseSensitive) {
            String indent = "&nbsp;&nbsp;&nbsp;&nbsp;";
            String ln = String.valueOf(match.lineNumber);
            String highlighted = highlightToHtml(match.lineText, query, caseSensitive);
            return "<html>" + indent +
                    "<span style='opacity:0.6'>" + ln + ":</span> " +
                    highlighted + "</html>";
        }
    }

    // -------------------------
    // Modern search dialog
    // -------------------------

    private class SearchDialog extends JDialog {
        private final List<FileModel> files;

        private final JTextField queryField = new JTextField();
        private final JCheckBox caseSensitiveBox =
                new JCheckBox(LanguageService.getTranslation("file.search.caseSensitive"));

        private final JButton searchButton =
                new JButton(LanguageService.getTranslation("file.search.start"));
        private final JButton cancelButton =
                new JButton(LanguageService.getTranslation("file.search.cancel"));

        private final JProgressBar progress = new JProgressBar();
        private final JLabel status = new JLabel(" ");

        private SearchWorker worker;

        SearchDialog(JFrame owner, List<FileModel> files) {
            super(owner, LanguageService.getTranslation("file.search"), true);
            this.files = files;

            setLayout(new BorderLayout());
            JComponent root = (JComponent) getContentPane();
            root.setBorder(BorderFactory.createEmptyBorder(18, 18, 16, 18));

            // Header
            JLabel title = new JLabel(LanguageService.getTranslation("file.search"));
            title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize2D() + 4f));
            title.setAlignmentX(Component.LEFT_ALIGNMENT);
            title.setHorizontalAlignment(SwingConstants.LEFT);

            JLabel subtitle = new JLabel(LanguageService.getTranslation("file.search.prompt"));
            subtitle.setBorder(BorderFactory.createEmptyBorder(6, 0, 3, 0));
            subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            subtitle.setHorizontalAlignment(SwingConstants.LEFT);

            // Input panel (clean grid)
            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
            inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 0, 4, 0);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            queryField.setColumns(34);
            inputPanel.add(queryField, gbc);

            gbc.gridy++;
            caseSensitiveBox.setFocusPainted(false);
            inputPanel.add(caseSensitiveBox, gbc);

            // Center status area
            JPanel statusPanel = new JPanel();
            statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
            statusPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 6, 0));
            statusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            progress.setIndeterminate(true);
            progress.setVisible(false);
            progress.setAlignmentX(Component.LEFT_ALIGNMENT);

            status.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
            status.setAlignmentX(Component.LEFT_ALIGNMENT);

            statusPanel.add(progress);
            statusPanel.add(status);

            // Buttons
            JPanel buttons = new JPanel();
            buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
            buttons.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            buttons.add(Box.createHorizontalGlue());
            buttons.add(searchButton);
            buttons.add(Box.createRigidArea(new Dimension(8, 0)));
            buttons.add(cancelButton);

            // Assemble
            JPanel top = new JPanel();
            top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
            top.add(title);
            top.add(subtitle);
            top.add(inputPanel);

            add(top, BorderLayout.NORTH);
            add(statusPanel, BorderLayout.CENTER);
            add(buttons, BorderLayout.SOUTH);

            getRootPane().setDefaultButton(searchButton);

            // ESC cancels/closes
            getRootPane().registerKeyboardAction(
                    ev -> onCancel(),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                    JComponent.WHEN_IN_FOCUSED_WINDOW
            );

            searchButton.addActionListener(ev -> onSearch());
            cancelButton.addActionListener(ev -> onCancel());

            pack();
            setMinimumSize(new Dimension(420, getHeight()));
            setLocationRelativeTo(parent());
        }

        private void setBusy(boolean busy) {
            searchButton.setEnabled(!busy);
            queryField.setEnabled(!busy);
            caseSensitiveBox.setEnabled(!busy);
            progress.setVisible(busy);

            if (!busy) {
                status.setText(" ");
            }
        }

        private void onSearch() {
            String query = queryField.getText();
            if (query == null || query.isBlank())
                return;

            boolean caseSensitive = caseSensitiveBox.isSelected();

            setBusy(true);
            status.setText(LanguageService.getTranslation("file.search.progress"));

            worker = new SearchWorker(files, query, caseSensitive);
            worker.execute();
        }

        private void onCancel() {
            if (worker != null && !worker.isDone()) {
                worker.cancel(true);
                status.setText(LanguageService.getTranslation("file.search.canceled"));
                setBusy(false);
                return;
            }
            dispose();
        }

        private class SearchWorker extends SwingWorker<List<FileResult>, Void> {
            private final List<FileModel> files;
            private final String query;
            private final boolean caseSensitive;

            SearchWorker(List<FileModel> files, String query, boolean caseSensitive) {
                this.files = files;
                this.query = query;
                this.caseSensitive = caseSensitive;
            }

            @Override
            protected List<FileResult> doInBackground() {
                return search(files, query, caseSensitive, this);
            }

            @Override
            protected void done() {
                if (isCancelled()) return;

                try {
                    List<FileResult> results = get();

                    if (results.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                parent(),
                                LanguageService.getTranslation("file.search.noResults"),
                                LanguageService.getTranslation("file.search"),
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        setBusy(false);
                        return;
                    }

                    dispose();
                    showResultsDialog(results, query, caseSensitive);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            parent(),
                            ex.getMessage(),
                            LanguageService.getTranslation("file.search"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    setBusy(false);
                }
            }
        }
    }


    // -------------------------
    // Search implementation
    // -------------------------

    private List<FileResult> search(
            List<FileModel> files,
            String query,
            boolean caseSensitive,
            SwingWorker<?, ?> worker
    ) {
        List<FileResult> results = new ArrayList<>();

        for (FileModel file : files) {
            if (worker != null && worker.isCancelled())
                break;

            try {
                String text;

                Type type = file.getType();
                if (type instanceof ClassType) {
                    text = TabsController.INSTANCE.getTransformer().newInstance().transform(file);
                } else {
                    text = new String(file.getBytes(), StandardCharsets.UTF_8);
                }

                List<LineMatch> matches = findMatchingLines(text, query, caseSensitive, worker);
                if (!matches.isEmpty()) {
                    results.add(new FileResult(file, matches));
                }

            } catch (Exception ignored) {
            }
        }

        results.sort(Comparator
                .comparingInt(FileResult::count).reversed()
                .thenComparing(r -> r.file.getPath(), String.CASE_INSENSITIVE_ORDER));

        return results;
    }

    private List<LineMatch> findMatchingLines(
            String text,
            String query,
            boolean caseSensitive,
            SwingWorker<?, ?> worker
    ) {
        List<LineMatch> out = new ArrayList<>();

        String[] lines = text.split("\\R", -1);

        String q = caseSensitive ? query : query.toLowerCase();

        for (int i = 0; i < lines.length; i++) {
            if (worker != null && worker.isCancelled())
                break;

            String line = lines[i];
            String hay = caseSensitive ? line : line.toLowerCase();

            if (hay.contains(q)) {
                out.add(new LineMatch(i + 1, line));
                if (out.size() >= MAX_LINE_MATCHES_PER_FILE)
                    break;
            }
        }
        return out;
    }


    // -------------------------
    // Results UI
    // -------------------------

    private void showResultsDialog(List<FileResult> results, String query, boolean caseSensitive) {
        List<ResultItem> items = new ArrayList<>();

        for (FileResult r : results) {
            items.add(new FileHeaderItem(r));
            for (LineMatch lm : r.lines) {
                if (items.size() >= MAX_TOTAL_ITEMS)
                    break;
                items.add(new LineItem(r.file, lm));
            }
            if (items.size() >= MAX_TOTAL_ITEMS)
                break;
        }

        DefaultListModel<ResultItem> model = new DefaultListModel<>();
        for (ResultItem it : items)
            model.addElement(it);

        JList<ResultItem> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ResultRenderer(query, caseSensitive));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(760, 460));

        JButton openButton = new JButton(LanguageService.getTranslation("file.search.open"));
        JButton closeButton = new JButton(LanguageService.getTranslation("file.search.close"));

        JDialog dialog = new JDialog(
                ownerFrame(),
                LanguageService.getTranslation("file.search"),
                true
        );
        dialog.setLayout(new BorderLayout(12, 12));
        ((JComponent) dialog.getContentPane()).setBorder(new EmptyBorder(14, 14, 14, 14));

        JLabel header = new JLabel(String.format(
                LanguageService.getTranslation("file.search.results"),
                query
        ));
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        dialog.add(header, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        bottom.add(Box.createHorizontalGlue());
        bottom.add(openButton);
        bottom.add(Box.createRigidArea(new Dimension(8, 0)));
        bottom.add(closeButton);

        dialog.add(bottom, BorderLayout.SOUTH);

        Runnable openSelected = () -> {
            ResultItem selected = list.getSelectedValue();
            if (selected != null) {
                TabsController.INSTANCE.addTab(selected.file());
                dialog.dispose();
            }
        };

        openButton.addActionListener(ev -> openSelected.run());
        closeButton.addActionListener(ev -> dialog.dispose());

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                if (ev.getClickCount() == 2)
                    openSelected.run();
            }
        });

        dialog.getRootPane().registerKeyboardAction(
                ev -> dialog.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        dialog.pack();
        dialog.setLocationRelativeTo(parent());
        dialog.setVisible(true);
    }

    private static class ResultRenderer extends DefaultListCellRenderer {
        private final String query;
        private final boolean caseSensitive;

        ResultRenderer(String query, boolean caseSensitive) {
            this.query = query;
            this.caseSensitive = caseSensitive;
        }

        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof ResultItem) {
                ResultItem item = (ResultItem) value;
                setText(item.toHtml(query, caseSensitive));
                setBorder(new EmptyBorder(4, item.isHeader() ? 2 : 14, 4, 2));

                if (!isSelected && item.isHeader()) {
                    Color fg = list.getForeground();
                    if (fg != null) {
                        setForeground(fg.darker());
                    }
                }
            }

            return this;
        }
    }

    // -------------------------
    // HTML utilities
    // -------------------------

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private static String highlightToHtml(String line, String query, boolean caseSensitive) {
        if (line == null) return "";
        if (query == null || query.isEmpty())
            return escapeHtml(line);

        String source = line;
        String hay = caseSensitive ? source : source.toLowerCase(Locale.ROOT);
        String needle = caseSensitive ? query : query.toLowerCase(Locale.ROOT);

        StringBuilder sb = new StringBuilder();
        int from = 0;
        int idx;

        while ((idx = hay.indexOf(needle, from)) >= 0) {
            int end = idx + query.length();
            if (end > source.length()) break;

            String before = source.substring(from, idx);
            String match = source.substring(idx, end);

            sb.append(escapeHtml(before));
            sb.append("<b>").append(escapeHtml(match)).append("</b>");

            from = end;
        }

        sb.append(escapeHtml(source.substring(from)));
        return sb.toString();
    }

}
