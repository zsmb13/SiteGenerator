package dom.elements;

import io.HTMLWriter;
import io.MarkdownReader;
import resources.ResourceFetcher;
import resources.StringLists;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Code implements Element {

    private List<String> lines = new ArrayList<>();
    private String language;

    public Code(List<String> lines, String language) {
        lines.add(0, "<pre><code class=\"" + language + "\">");
        lines.add("</code></pre>");
        this.lines = lines;
        this.language = language;
    }

    //private static String[] languages = {"c", "cpp", "cs", "java"};
    private static List<String> languages = ResourceFetcher.getStringList(StringLists.Languages);

    public static Code create(String firstLine) {
        String pieces[] = firstLine.split("\\{");
        String language = pieces[0].trim();
        String lastLine = null;

        if(languages.contains(language)) {
            lastLine = "}" + language;
        }

        if(lastLine == null) {
            //TODO error handling
            return null;
        }

        ArrayList<String> lines = new ArrayList<>();
        String line;
        while(!(line = MarkdownReader.readLine()).equals(lastLine)) {
            lines.add(line);
        }

        return new Code(lines, language);
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<pre><code class=\"" + language + "\">");
        for (String l : lines) {
            HTMLWriter.writeLine(l, false);
        }
        HTMLWriter.writeLine("</code></pre>");
    }

}
