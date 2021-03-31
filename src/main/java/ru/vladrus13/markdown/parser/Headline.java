package ru.vladrus13.markdown.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * One header line class
 */
public class Headline {
    /**
     * Level of this header
     */
    public final int level;
    /**
     * Name (or head) of this header
     */
    public String name;
    /**
     * Childes of this header
     */
    public final ArrayList<Headline> childes = new ArrayList<>();
    /**
     * Names of classes. Usage on equals names of header
     */
    private final static Set<String> names = new HashSet<>();

    /**
     * Constructor of class header
     *
     * @param level level of header
     */
    public Headline(int level) {
        this.level = level;
    }

    /**
     * Add a child to header
     *
     * @param headline new child
     */
    public void add(Headline headline) {
        childes.add(headline);
    }

    /**
     * Set name (Builder pattern)
     *
     * @param name name of class
     * @return this class
     */
    public Headline setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Convert name to link-name
     *
     * @param name Get name of header
     * @return link-name
     * @see [https://gist.github.com/rachelhyman/b1f109155c9dafffe618](link)
     */
    public static String linkName(String name) {
        return name
                .replace(' ', '-')
                .replaceAll("[^a-zA-Z0-9-]", "")
                .toLowerCase();
    }

    /**
     * Convert header to StringBuilder view
     *
     * @return result
     */
    public StringBuilder toStringBuilder() {
        StringBuilder returned = new StringBuilder();
        for (int i = 0; i < childes.size(); i++) {
            Headline headline = childes.get(i);
            String name = null;
            if (!names.contains(linkName(headline.name))) {
                name = linkName(headline.name);
            } else {
                int current = 1;
                while (name == null) {
                    if (!names.contains(linkName(headline.name + "-" + current))) {
                        name = linkName(headline.name + "-" + current);
                    }
                    current++;
                }
            }
            names.add(name);
            returned
                    .append("\t".repeat(level))
                    .append(i + 1)
                    .append(". [").append(headline.name).append("](#")
                    .append(name).append(")")
                    .append(System.lineSeparator())
                    .append(headline.toStringBuilder());
        }
        return returned;
    }
}
