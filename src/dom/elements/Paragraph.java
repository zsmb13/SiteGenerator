package dom.elements;

import dom.roots.Page;
import io.HTMLWriter;
import io.TextHelper;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Paragraph implements Element {

    private String text;

    public Paragraph(String text) {
        this.text = TextHelper.process(text);
    }

    public static Paragraph create(String firstLine) {
        String text = TextHelper.process(firstLine);

        if(Page.grabDescription) {
            //TODO set current page's description to 'text'
        }

        return new Paragraph(text);
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<p>" + text + "</p>");
    }
}
