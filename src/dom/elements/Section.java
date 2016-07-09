package dom.elements;

import io.HTMLWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Section implements Element {
    private List<Element> subElements = new ArrayList<Element>();

    @Override
    public void writeHTML() {
        //TODO add section ids
        HTMLWriter.writeLine("<section>");
        HTMLWriter.indent();
        subElements.forEach(Element::writeHTML);
        HTMLWriter.unindent();
        HTMLWriter.writeLine("</section>");
    }


}
