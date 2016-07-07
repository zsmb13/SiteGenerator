package io;

import dom.roots.Page;

import java.io.*;

/**
 * Created by zsmb on 2016-07-07.
 */
public class HTMLWriter {

    private static int indentation = 0;

    private static String getIndentation() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < indentation; i++) {
            sb.append("\t");
        }

        return sb.toString();
    }

    private static PrintWriter pw;

    public static void prepareToWrite(Page p) {
        String filename = p.getFileName();

        try {
            pw = new PrintWriter(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("HTMLWriter error: can not open file for writing.");
        }
    }

    public static void setIndentation(int indentation) {
        HTMLWriter.indentation = indentation;
    }

    public static void writeLine(String line) {
        writeLine(line, true);
    }

    public static void writeLine(String line, boolean indent) {
        if(pw == null) {
            //TODO handle error gently
        }

        if(indent) {
            pw.println(getIndentation() + line);
        }
        else {
            pw.println(line);
        }
    }

}
