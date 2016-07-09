package dom.elements;

import io.MarkdownReader;

/**
 * Created by zsmb on 2016-07-07.
 */
public class DownloadItem implements Element {

    private String filename;
    private String description;
    private String size;
    private String date;

    public DownloadItem() {
        //TODO get current page's sitepath as it will be needed in  the writeHTML function
        filename = MarkdownReader.readLine();
        description = MarkdownReader.readLine();
        size = MarkdownReader.readLine();
        date = MarkdownReader.readLine();
    }

    @Override
    public void writeHTML() {
        //TODO implement
    }

}
