package dom.elements;

import io.HTMLWriter;

import java.util.ArrayList;

/**
 * Created by zsmb on 2016-07-07.
 */
public class List implements Element {

    private boolean ordered;
    private java.util.List<String> entries = new ArrayList<>();

    public List(java.util.List<String> entries) {
        for (String e : entries) {
            this.entries.add("<li>" + e + "</li>");
        }
    }

    public static Image create(String firstLine) {
        //TODO implement
        return null;
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine(ordered ? "<ol>" : "<ul>");

        HTMLWriter.writeLines(entries);

        HTMLWriter.writeLine(ordered ? "</ol>" : "</ul>");
    }
}
