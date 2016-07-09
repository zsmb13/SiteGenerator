package dom.elements;

import io.HTMLWriter;
import resources.ResourceFetcher;
import resources.StringLists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public class DownloadTable implements Element {

    private List<DownloadItem> items = new ArrayList<>();

    public DownloadTable(List<String> lines) {
        //TODO implement
        //TODO check lenghts, call DownloadItem ctors with sublists()
    }

    public static DownloadTable create(String firstLine) {
        //TODO implement
        return null;
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
