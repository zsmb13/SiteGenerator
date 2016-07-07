package dom.elements;

import io.HTMLWriter;

import java.util.ArrayList;
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

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<pre><code class=\"" + language + "\">");
        for(String l : lines) {
            HTMLWriter.writeLine(l, false);
        }
        HTMLWriter.writeLine("</code></pre>");
    }

}
