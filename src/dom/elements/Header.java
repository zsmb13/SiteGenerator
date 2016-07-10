package dom.elements;

import dom.roots.Page;
import dom.roots.PageDirectory;
import io.HTMLWriter;
import io.TextHelper;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Header implements Element {

    private int size;
    private String text;

    public Header(String text, int size) {
        this.text = TextHelper.process(text);
        this.size = size;
    }

    public static Header create(String firstLine) {
        String headerText;

        // If the string begins with double # or double @
        if(firstLine.charAt(0) == firstLine.charAt(1)) {
            PageDirectory.prepareForDesc();
            headerText = firstLine.substring(2).trim();
        }
        else {
            headerText = firstLine.substring(1).trim();
        }

        // If the header is a large header
        if(firstLine.charAt(0) == '@'){
            return new Header(headerText, 2);
        }
        else {
            return new Header(headerText, 3);
        }
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<h" + size + ">" + text + "</h" + size + ">");
    }
}
