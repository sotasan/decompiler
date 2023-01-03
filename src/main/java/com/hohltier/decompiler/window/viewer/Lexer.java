package com.hohltier.decompiler.window.viewer;

import lombok.experimental.UtilityClass;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Lexer {

    private static final String[] KEYWORDS = {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final Pattern PATTERN = Pattern.compile(
            String.format("(?<%s>%s)|(?<%s>%s)|(?<%s>%s)",
                    Group.KEYWORD.name(), Group.KEYWORD.regex,
                    Group.STRING.name(), Group.STRING.regex,
                    Group.COMMENT.name(), Group.COMMENT.regex
            )
    );

    public static StyleSpans<Collection<String>> getSyntaxHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();

        int end = 0;
        while (matcher.find()) {
            Set<String> strings = Collections.emptySet();
            for (Group group : Group.values())
                if (matcher.group(group.name()) != null)
                    strings = Collections.singleton(group.name().toLowerCase());
            builder.add(Collections.emptyList(), matcher.start() - end);
            builder.add(strings, matcher.end() - matcher.start());
            end = matcher.end();
        }

        builder.add(Collections.emptyList(), text.length() - end);
        return builder.create();
    }

    private enum Group {

        KEYWORD(String.format("\\b(%s)\\b", String.join("|", KEYWORDS))),
        STRING("\"([^\"\\\\]|\\\\.)*\""),
        COMMENT("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/");

        private final String regex;

        Group(String regex) {
            this.regex = regex;
        }

    }

}