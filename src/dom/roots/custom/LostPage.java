package dom.roots.custom;

import dom.elements.Header;
import dom.elements.Paragraph;
import dom.elements.Section;
import io.HTMLWriter;
import io.TemplateWriter;
import resources.ResourceFetcher;
import resources.StringLists;
import resources.Strings;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zsmb on 2016-07-10.
 */
public class LostPage extends CustomPage {

    public LostPage() {
        description = ResourceFetcher.getString(Strings.LostDesc);

        sections = new ArrayList<>();

        Section s = new Section(0);
        Header header = new Header(ResourceFetcher.getString(Strings.LostHeader), 2);
        Paragraph paragraph = new Paragraph(ResourceFetcher.getString(Strings.LostText));
        s.add(header);
        s.add(paragraph);

        sections.add(s);
    }

    @Override
    protected String createURL() {
        return "/lost/";
    }

    @Override
    protected String createSitePath() {
        return "";
    }

    @Override
    protected String createFilename() {
        return "lost.html";
    }

    @Override
    public void write() {
        HTMLWriter.prepareToWrite(this);

        TemplateWriter.write(0);

        // TODO make nicer
        HTMLWriter.writeLine("<meta name=\"description\" content=\"" + description + "\">");

        TemplateWriter.write(1);
        TemplateWriter.write(2);

        sections.forEach(Section::writeHTML);

        TemplateWriter.write(3);
        TemplateWriter.write(5);

        HTMLWriter.finishWriting();
    }
}
