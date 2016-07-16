package io;

import com.sun.istack.internal.NotNull;
import dom.elements.*;
import dom.roots.Category;
import dom.roots.Page;
import dom.roots.PageDirectory;

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
    private static ArrayList<String> cachedLines = new ArrayList<>();

    /**
     * Returns the next line of the file currently being parsed
     *
     * @return the line
     */
    public static String readLine() {
        //TODO make function never return null

        if (!cachedLines.isEmpty()) {
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

    public static void cache(String str) {
        if (str != null) {
            cachedLines.add(str);
        }
    }

    /**
     * Opens a new file, and applies the properties found in it to the current Page
     *
     * @param sourceFile the file to open and read
     */
    public static void readPageProps(File sourceFile) {
        try {
            br = new BufferedReader(new FileReader(sourceFile));
        } catch (IOException e) {
            //TODO error handling
            e.printStackTrace();
        }

        String line;
        Page currentPage = PageDirectory.getCurrentPage();
        while ((line = readLine()) != null && !line.trim().isEmpty()) {
            String[] pieces = line.split("=");
            String propName = pieces[0].trim();
            String propValue = pieces[1].replace("\"", "");


            switch (propName) {
                case "date":
                    currentPage.setDate(propValue);
                    break;
                case "title":
                    currentPage.setTitle(propValue);
                    break;
                case "shorttitle":
                    currentPage.setShortTitle(propValue);
                    break;
                case "category":
                    String[] cats = propValue.split(" ");
                    ArrayList<Category> categories = new ArrayList<>();
                    for (int i = 0; i < cats.length; i++) {
                        categories.add(Category.parse(cats[i]));
                    }
                    currentPage.setCategories(categories);
                    break;
                case "post":
                    if (propValue.equals("true")) {
                        currentPage.setPost(true);
                    }
                    break;
                case "lang":
                    currentPage.setLanguage(propValue);
                    break;
                default:
                    System.err.println("Unknown property name read from file: " + propName);
                    break;
            }
        }
    }

    /**
     * Reads the sections found in the currently open file
     *
     * @return the contents read
     */
    public static java.util.List<Section> readSections() {
        sections = new ArrayList<>();

        if (br == null) {
            //TODO error handling
            // properties haven't been read!
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
                sections.add(new Section());
            }
            sections.get(sections.size() - 1).add(e);
        }

        try {
            br.close();
            br = null;
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
        // Pre-parse common simple paragraphs as they are very frequent
        if (Character.isAlphabetic(line.charAt(0))) {
            return Paragraph.create(line);
        }

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
                sections.add(new Section());
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
