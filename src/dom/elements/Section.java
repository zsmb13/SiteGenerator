package dom.elements;

import io.HTMLWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Section implements Element {
    private List<Element> subElements = new ArrayList<Element>();
    private String className;
    private String ID;

    public Section() {
    }

    public Section(String className) {
        this.className = className;
    }

    public Section(String className, String ID) {
        this.className = className;
        this.ID = ID;
    }

    @Override
    public void writeHTML() {
        // TODO determine if we really need these IDs for anything
        HTMLWriter.writeLine("<section"
                + (className != null ? " class=\"" + className + "\"" : "")
                + (ID != null ? " id=\"" + ID+ "\"" : "")
                + ">");
        HTMLWriter.indent();
        subElements.forEach(Element::writeHTML);
        HTMLWriter.unindent();
        HTMLWriter.writeLine("</section>");
    }

    public void add(Element e) {
        if (e == null) {
            // Should not happen, but just in case
            return;
        }
        subElements.add(e);
    }
}
