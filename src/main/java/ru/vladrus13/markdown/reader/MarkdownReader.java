package ru.vladrus13.markdown.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Reader class
 */
public class MarkdownReader {
    /**
     * Get all lines from file
     *
     * @param path path to file
     * @return stream of lines from file
     * @throws IOException if we can't get file
     */
    public static Stream<String> readFile(Path path) throws IOException {
        return Files.newBufferedReader(path).lines();
    }
}
