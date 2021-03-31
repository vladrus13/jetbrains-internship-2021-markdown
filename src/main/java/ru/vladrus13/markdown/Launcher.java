package ru.vladrus13.markdown;

import ru.vladrus13.markdown.parser.Parser;
import ru.vladrus13.markdown.reader.MarkdownReader;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Launcher class
 */
public class Launcher {
    /**
     * Main method of launcher
     *
     * @param args args[0] - path to file we need to convert
     * @throws IOException if we can't get file
     */
    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0) {
            System.out.println("Wrong arguments. Arguments must contain one path to md file");
            return;
        }
        Path path = Path.of(args[0]);
        Parser parser = new Parser(MarkdownReader.readFile(path));
        parser.run();
        parser.write(System.out);
    }
}
