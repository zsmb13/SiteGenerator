package dom.elements;

import io.HTMLWriter;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Comment implements Element {

    private String text;

    public Comment(String text) {
        this.text = text;
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<!-- " + text + " -->");
    }

}
