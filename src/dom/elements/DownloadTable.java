package dom.elements;

import io.HTMLWriter;
import io.MarkdownReader;
import resources.ResourceFetcher;
import resources.StringLists;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class DownloadTable implements Element {

    private List<DownloadItem> items;

    public DownloadTable(List<DownloadItem> items) {
        this.items = items;
    }

    public static DownloadTable create(String firstLine) {
        ArrayList<DownloadItem> items = new ArrayList<>();
        while(firstLine != null && firstLine.equals("%")) {
            items.add(new DownloadItem());
            firstLine = MarkdownReader.readLine();
        }

        //An extra line was consumed, give it back to the reader
        MarkdownReader.cache(firstLine);

        return new DownloadTable(items);
    }

    @Override
    public void writeHTML() {
        List<String> header = ResourceFetcher.getStringList(StringLists.DLTableHeader);
        List<String> footer = ResourceFetcher.getStringList(StringLists.DLTableFooter);

        for (String s : header) {
            HTMLWriter.writeLine(s);
        }

        for (DownloadItem di : items) {
            di.writeHTML();
        }

        for (String s : footer) {
            HTMLWriter.writeLine(s);
        }
    }

}
