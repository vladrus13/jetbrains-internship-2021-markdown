package ru.vladrus13.markdown.parser;

import java.util.*;

public class Headline {
    public final int level;
    public String name;
    public final ArrayList<Headline> childes = new ArrayList<>();
    private final static Set<String> childesNames = new HashSet<>();

    public Headline(int level) {
        this.level = level;
    }

    public void add(Headline headline) {
        childes.add(headline);
    }

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

    public StringBuilder toStringBuilder() {
        StringBuilder returned = new StringBuilder();
        for (int i = 0; i < childes.size(); i++) {
            Headline headline = childes.get(i);
            String name = null;
            if (!childesNames.contains(linkName(headline.name))) {
                name = linkName(headline.name);
            } else {
                int current = 1;
                while (name == null) {
                    if (!childesNames.contains(linkName(headline.name + "-" + current))) {
                        name = linkName(headline.name + "-" + current);
                    }
                    current++;
                }
            }
            childesNames.add(name);
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
