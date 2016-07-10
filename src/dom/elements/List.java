package dom.elements;

import io.HTMLWriter;
import io.MarkdownReader;
import io.TextHelper;

import java.util.ArrayList;

/**
 * Created by zsmb on 2016-07-07.
 */
public class List implements Element {

    private boolean ordered;
    private java.util.List<String> entries = new ArrayList<>();

    public List(java.util.List<String> entries, boolean ordered) {
        for (String e : entries) {
            this.entries.add("<li>" + TextHelper.process(e) + "</li>");
        }

        this.ordered = ordered;
    }

    public static List create(String firstLine) {
        boolean ordered = (firstLine.charAt(0) != '-');
        return ordered ? createOrdered(firstLine) : createUnordered(firstLine);
    }

    private static List createUnordered(String firstLine) {
        ArrayList<String> entries = new ArrayList<>();

        while(firstLine.charAt(0) == '-') {
            String entry = firstLine.substring(1).trim();
            entries.add(entry);
            firstLine = MarkdownReader.readLine();
        }

        MarkdownReader.cache(firstLine);

        return new List(entries, false);
    }

    private static List createOrdered(String firstLine) {
        ArrayList<String> entries = new ArrayList<>();

        while(isOrderedItem(firstLine)) {
            int i = firstLine.indexOf('.');
            String entry = firstLine.substring(i+1).trim();
            entries.add(entry);
            firstLine = MarkdownReader.readLine();
        }

        // We read a line that wasn't a list item, this should be processed again
        MarkdownReader.cache(firstLine);

        return new List(entries, true);
    }

    private static boolean isOrderedItem(String line) {
        if (line.contains(".")) {
            String[] pieces = line.split("\\.");

            try {
                Integer.parseInt(pieces[0]);
                // Ordered list
                return true;
            } catch (NumberFormatException e) {
                // Not a number before the first '.'
                return false;
            }
        }

        return false;
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine(ordered ? "<ol>" : "<ul>");

        HTMLWriter.writeLines(entries);

        HTMLWriter.writeLine(ordered ? "</ol>" : "</ul>");
    }
}
