package dom.elements;

import dom.roots.Page;
import io.HTMLWriter;
import io.MarkdownReader;

/**
 * Created by zsmb on 2016-07-07.
 */
public class DownloadItem implements Element {

    private String filename;
    private String description;
    private String size;
    private String date;

    private Page page;

    public DownloadItem() {
        page = Page.current;

        filename = MarkdownReader.readLine();
        description = MarkdownReader.readLine();
        size = MarkdownReader.readLine();
        date = MarkdownReader.readLine();
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<tr>");
        HTMLWriter.writeLine("<td class=\"dlcolumn\"><a href=\""
                + page.getUrl() + filename
                + "\"><i class=\"fa fa-download\"></i></a></td>");
        writeCell(filename);
        writeCell(description);
        writeCell(size);
        writeCell(date);
        HTMLWriter.writeLine("</tr>");
    }

    private void writeCell(String content) {
        HTMLWriter.writeLine("<td>" + content + "</td>");
    }

}
