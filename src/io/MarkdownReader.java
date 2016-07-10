package io;

import dom.elements.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zsmb on 2016-07-07.
 */
public class MarkdownReader {

    private static BufferedReader br;
    private static ArrayList<Section> sections;

    /**
     * Returns the next line of the file currently being parsed
     *
     * @return the line
     */
    public static String readLine() {
        if(!cachedLines.isEmpty()) {
            return cachedLines.remove(0);
        }

        try {
            return br.readLine();
        } catch (IOException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String> cachedLines = new ArrayList<>();

    public static void cache(String toCache) {
        cachedLines.add(toCache);
    }

    public static void readPageProps(File sourceFile) {

    }

    /**
     * Processes a given file
     *
     * @param sourceFile the given file
     * @return the contents read
     */
    public static java.util.List<Section> readSections(File sourceFile) {
        sections = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(sourceFile));
        } catch (IOException e) {
            //TODO error handling
            e.printStackTrace();
        }

        String line;
        while ((line = readLine()) != null) {
            if (line.isEmpty()) {
                // Throw away empty lines
                continue;
            }

            Element e = parseSingleChar(line);
            if (e == null) {
                // Nothing special, just a paragraph
                e = Paragraph.create(line);
            }

            if (sections.isEmpty()) {
                // This should not happen, as every page is supposed to start with a header
                sections.add(new Section(sections.size()));
            }
            sections.get(sections.size() - 1).add(e);
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO error handling
        }

        return sections;
    }

    /**
     * Parses elements of the page that are denoted by a single starting character
     *
     * @param line the line that starts the next element
     * @return the parsed element
     */
    private static Element parseSingleChar(String line) {
        switch (line.charAt(0)) {
            case '[':
                // image
                return Image.create(line);
            case '-':
                // ul
                return List.create(line);
            case '#':
            case '@':
                // header
                sections.add(new Section(sections.size()));
                return Header.create(line);
            case '%':
                // dltable
                return DownloadTable.create(line);
            default:
                // something more complicated
                return parseComplex(line);
        }
    }

    /**
     * Parses elements of the page that are harder to recognise
     *
     * @param line the line that starts the next element
     * @return the parsed element
     */
    private static Element parseComplex(String line) {
        if (line.startsWith("//")) {
            // Comment
            // This should just throw away the line instead of inserting a HTML comment
            // as that was the implementation in the previous code
            return Comment.create(line);
            //return null;
            //TODO switch commented state of the above two lines before release
        }
        else if (line.endsWith("{")) {
            // Code, but maybe just a paragraph, don't forget to check
            // Update: actually, this will return null if it's not code, so we're fine
            return Code.create(line);
        }
        else if (line.contains(".")) {
            String[] pieces = line.split("\\.");

            try {
                Integer.parseInt(pieces[0]);
                // Ordered list
                return List.create(line);
            } catch (NumberFormatException e) {
                // Not a number before the first '.'
                return null;
            }
        }

        // I don't think this statement is reachable but IntelliJ likes it when it's here
        return null;
    }

}
