package dom.elements;

import dom.roots.PageDirectory;
import io.HTMLWriter;
import io.MarkdownReader;
import resources.ResourceFetcher;
import resources.StringLists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Code implements Element {

    //private static String[] languages = {"c", "cpp", "cs", "java"};
    private static List<String> languages = ResourceFetcher.getStringList(StringLists.Languages);
    private List<String> lines;
    private String language;

    public Code(List<String> lines, String language) {
        this.language = language;
        this.lines = new ArrayList<>();

        for (String line : lines) {
            String escapedLine = line.replace("<", "&lt;").replace(">", "&gt;");
            this.lines.add(escapedLine);
        }
    }

    public static Code create(String firstLine) {
        String pieces[] = firstLine.split("\\{");
        String language = pieces[0].trim();
        String lastLine = null;

        if (languages.contains(language)) {
            lastLine = "}" + language;
        }

        if (lastLine == null) {
            //TODO error handling
            return null;
        }

        ArrayList<String> lines = new ArrayList<>();
        String line;
        while (!(line = MarkdownReader.readLine()).equals(lastLine)) {
            lines.add(line);
        }

        PageDirectory.getCurrentPage().setContainsCode(true);
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
