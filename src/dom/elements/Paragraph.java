package dom.elements;

import io.HTMLWriter;
import io.TextHelper;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Paragraph implements Element{

    private String text;

    public Paragraph(String text) {
        this.text = TextHelper.process(text);
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<p>" + text + "</p>");
    }

}
