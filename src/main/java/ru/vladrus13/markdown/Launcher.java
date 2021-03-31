package ru.vladrus13.markdown;

import ru.vladrus13.markdown.parser.Parser;
import ru.vladrus13.markdown.reader.MarkdownReader;

import java.io.IOException;
import java.nio.file.Path;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Path path = Path.of(args[0]);
        Parser parser = new Parser(MarkdownReader.readFile(path));
        parser.run();
        parser.write(System.out);
    }
}
