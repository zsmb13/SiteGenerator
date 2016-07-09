package dom.elements;

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
        //TODO implement
        return null;
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<h" + size + ">" + text + "</h" + size + ">");
    }
}
