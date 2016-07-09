package io;

import dom.elements.Section;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zsmb on 2016-07-07.
 */
public class MarkdownReader {

    private static BufferedReader br;
    private static ArrayList<Section> sections;

    public static String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        }
    }

    public static List<Section> readSections(File sourceFile) {
        sections = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(sourceFile));
        } catch (IOException e) {
            //TODO error handling
            e.printStackTrace();
        }

        String line;
        while ((line = readLine()) != null) {
            boolean result = parseSingleChar(line);
            if(!result) {
                // Nothing special, just a paragraph
                // ...
            }
        }

        return sections;
    }

    private static boolean parseSingleChar(String line) {
        // TODO fill out all these
        switch (line.charAt(0)) {
            case '[':
                // image
                // ...
                return true;
            case '-':
                // ul
                // ...
                return true;
            case '#':
            case '@':
                // header
                // TODO start new section here
                // check if it's a promoted header that marks description
                // ...
                return true;
            case '%':
                // dltable
                // ...
                return true;
            default:
                // something more complicated
                return parseComplex(line);
        }
    }

    private static boolean parseComplex(String line) {
        // TODO fill out all these
        if(line.startsWith("//")) {
            // Comment
            // This should just throw away the line instead of inserting a HTML comment
            // as that was the implementation in the previous code
            // ...
            return true;
        }
        else if(line.endsWith("{")) {
            // Code, but maybe just a paragraph, don't forget to check
            // ...
            return true;
        }
        else if(line.contains(".")) {
            String[] pieces = line.split("\\.");

            try {
                Integer.parseInt(pieces[0]);
                // Ordered list
                // ...
                return true;
            } catch (NumberFormatException e) {
                // not a number before the first '.'
                return false;
            }
        }

        // I don't think this statement is reachable but IntelliJ likes it when it's here
        return false;
    }

}
