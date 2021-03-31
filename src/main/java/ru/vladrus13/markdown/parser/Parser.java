package ru.vladrus13.markdown.parser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Parser class
 */
public class Parser {
    /**
     * Input lines
     */
    private final ArrayList<String> input;
    /**
     * Current position on input lines
     */
    private int position = 0;
    /**
     * Result headers
     */
    private Headline result;

    /**
     * Parser constructor class
     *
     * @param input stream of lines
     */
    public Parser(Stream<String> input) {
        this.input = input.collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns a current line
     *
     * @return current line
     */
    private String current() {
        return input.get(position);
    }

    /**
     * Go to a next line
     */
    private void next() {
        position++;
    }

    private boolean isEnd() {
        return input.size() <= position;
    }

    /**
     * Get level of current line
     *
     * @return 0 if this is not header. Return positive level of header and return negative level of old-style header
     * @see [https://www.markdownguide.org/basic-syntax/](Basic syntax)
     */
    private int isHeadline() {
        int level = 0;
        while (current().length() > level && current().charAt(level) == '#') {
            level++;
        }
        if (level > 0 && current().length() > level && current().charAt(level) == ' ' && level < 7) {
            return level;
        }
        if (input.size() > position + 1) {
            String nextLine = input.get(position + 1);
            if (nextLine.length() % 2 == 0 && nextLine.matches("^[=]+$")) {
                return -1;
            }
            if (nextLine.length() % 2 == 0 && nextLine.matches("^[-]+$")) {
                return -3;
            }
        }
        return 0;
    }

    /**
     * Recursive function to get header
     *
     * @param level current level
     * @return header
     */
    public Headline headline(int level) {
        Headline current = new Headline(level);
        while (!isEnd()) {
            int currentLevel = isHeadline();
            if (currentLevel != 0) {
                if (Math.abs(currentLevel) <= level) {
                    return current;
                }
                String name = current().substring(Math.max(0, currentLevel + 1));
                next();
                if (currentLevel < 0) {
                    currentLevel = -currentLevel;
                    next();
                }
                current.add(headline(currentLevel).setName(name));
            } else {
                next();
            }
        }
        return current;
    }

    /**
     * Run a parser
     */
    public void run() {
        result = headline(0);
    }

    /**
     * Write to print stream new markdown
     *
     * @param outputStream print stream
     */
    public void write(PrintStream outputStream) {
        outputStream.println(result.toStringBuilder().toString());
        outputStream.println(String.join(System.lineSeparator(), input));
    }
}
