package ru.vladrus13.markdown.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private final ArrayList<String> input;
    private int position = 0;
    private Headline result;

    public Parser(Stream<String> input) {
        this.input = input.collect(Collectors.toCollection(ArrayList::new));
    }

    private String current() {
        return input.get(position);
    }

    private void next() {
        position++;
    }

    private boolean isEnd() {
        return input.size() <= position;
    }

    // https://www.markdownguide.org/basic-syntax/

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

    public void run() {
        result = headline(0);
    }

    public void write(PrintStream outputStream) {
        outputStream.println(result.toStringBuilder().toString());
        outputStream.println(String.join(System.lineSeparator(), input));
    }
}
