package io;

import resources.ResourceFetcher;
import resources.Strings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class TemplateWriter {

    private static List<Template> templates = null;

    public static void write(int templateID) {
        if (templates == null) {
            prepareTemplates();
        }
        if (templateID >= templates.size() || templateID < 0) {
            // TODO handle error
        }

        templates.get(templateID).writeHTML();
    }

    private static void createDir(String name) {
        File f = new File("name");
        if (!f.exists()) {
            if (!f.mkdir()) {
                //TODO handle error
            }
        }
    }

    private static void prepareTemplates() {
        createDir("temp");
        createDir("temp/templates");

        templates = new ArrayList<>();

        String filename = ResourceFetcher.getString(Strings.SourceDir) + "template.html";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> currentLines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("***")) {
                    // end of a template
                    templates.add(new Template(currentLines));
                    currentLines.clear();
                }
                else {
                    currentLines.add(line);
                }
            }
            // end of the last template
            templates.add(new Template(currentLines));
        } catch (IOException e) {
            // TODO handle error
            e.printStackTrace();
        }
    }

    private static class Template {
        List<String> lines = new ArrayList<>();

        public Template(List<String> lines) {
            this.lines.addAll(lines);
        }

        public void writeHTML() {
            for (String l : lines) {
                HTMLWriter.writeLine(l, false);
            }
        }
    }

}
