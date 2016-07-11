package dom.elements;

import io.HTMLWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-11.
 */
public class CustomHTML implements Element {

    private List<String> lines;

    @Override
    public void writeHTML() {
        HTMLWriter.writeLines(lines);
    }

    public CustomHTML(String line) {
        lines = new ArrayList<>();
        lines.add(line);
    }

    public CustomHTML(List<String> lines) {
        this.lines = lines;
    }

}
