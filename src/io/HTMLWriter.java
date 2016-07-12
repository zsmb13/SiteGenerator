package io;

import dom.roots.Page;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class HTMLWriter {

    private static int indentation = 0;
    private static PrintWriter pw;

    private static String getIndentation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append("\t");
        }

        return sb.toString();
    }

    public static void prepareToWrite(Page p) {
        // TODO this is temporary for testing
        //String filename = p.getSitePath() + p.getFileName();
        String filename = "output/" + p.getSitePath() + p.getFileName();

        File outputFile = new File("output/" + p.getSitePath());
        if(!outputFile.exists()) {
            outputFile.mkdirs();
        }

        try {
            System.out.println("Writing file: " + filename);
            pw = new PrintWriter(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("HTMLWriter error: can not open file for writing.");
        }
    }

    public static void setIndentation(int indentation) {
        HTMLWriter.indentation = indentation;
    }

    public static void indent() {
        indentation++;
    }

    public static void unindent() {
        if (indentation > 0) {
            indentation--;
        }
    }

    public static void writeLine(String line) {
        writeLine(line, true);
    }

    public static void writeLine(String line, boolean indent) {
        if (pw == null) {
            //TODO handle error gently
        }

        if (indent) {
            pw.println(getIndentation() + line);
        }
        else {
            pw.println(line);
        }
    }

    public static void writeLines(List<String> lines) {
        writeLines(lines, true);
    }

    public static void writeLines(List<String> lines, boolean indent) {
        for (String line : lines) {
            writeLine(line, indent);
        }
    }

    public static void finishWriting() {
        pw.close();
    }

}
