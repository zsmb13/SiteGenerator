package dom.elements;

import dom.roots.Page;
import dom.roots.PageDirectory;
import io.HTMLWriter;
import io.MarkdownReader;

/**
 * Created by zsmb on 2016-07-07.
 */
public class DownloadItem implements Element {

    // Properties of a DownloadItem
    private String filename;
    private String description;
    private String size;
    private String date;

    // The Page that the DownloadItem is a part of
    // This is necessary to be able to generate the download links
    private Page page;

    public DownloadItem() {
        page = PageDirectory.getCurrentPage();

        filename = MarkdownReader.readLine();
        description = MarkdownReader.readLine();
        size = MarkdownReader.readLine();
        date = MarkdownReader.readLine();
    }

    @Override
    public void writeHTML() {
        HTMLWriter.writeLine("<tr>");
        HTMLWriter.indent();

        HTMLWriter.writeLine("<td class=\"dlcolumn\"><a href=\""
                + page.getUrl() + filename
                + "\"><i class=\"fa fa-download\"></i></a></td>");
        writeCell(filename);
        writeCell(description);
        writeCell(size);
        writeCell(date);

        HTMLWriter.unindent();
        HTMLWriter.writeLine("</tr>");
    }

    /**
     * Helper function: writes the given String to the HTML output as a table cell
     * @param content the String to write
     */
    private void writeCell(String content) {
        HTMLWriter.writeLine("<td>" + content + "</td>");
    }

}
