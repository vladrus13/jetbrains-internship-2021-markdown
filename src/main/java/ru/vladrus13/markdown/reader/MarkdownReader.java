package ru.vladrus13.markdown.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class MarkdownReader {
    public static Stream<String> readFile(Path path) throws IOException {
        return Files.newBufferedReader(path).lines();
    }
}
