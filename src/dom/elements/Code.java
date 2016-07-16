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

    // Allowed languages
    private static List<String> languages = ResourceFetcher.getStringList(StringLists.Languages);

    // Lines of code, indented as necessary
    private List<String> lines;
    // The language of the contained code, in accordance with the highlightjs naming schemes
    // http://highlightjs.readthedocs.io/en/latest/css-classes-reference.html#language-names-and-aliases
    private String language;

    public Code(List<String> lines, String language) {
        this.language = language;
        this.lines = new ArrayList<>();

        // Escape "<" and ">" characters to make the HTML output more valid
        for (String line : lines) {
            String escapedLine = line.replace("<", "&lt;").replace(">", "&gt;");
            this.lines.add(escapedLine);
        }
    }

    public static Code create(String firstLine) {
        // The incoming line should be in format "language{"
        String pieces[] = firstLine.split("\\{");
        String language = pieces[0].trim();
        String lastLine = null;

        // We'll read input until we find the closing line of the code block
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
        // Write code to file without indentation
        for (String l : lines) {
            HTMLWriter.writeLine(l, false);
        }
        HTMLWriter.writeLine("</code></pre>");
    }

}
