package dom.roots;

import dom.elements.Section;
import io.HTMLWriter;
import io.MarkdownReader;
import io.TemplateWriter;
import io.TextHelper;
import resources.ResourceFetcher;
import resources.StringLists;
import resources.Strings;

import java.io.File;
import java.util.List;

/**
 * Created by zsmb on 2016-07-07.
 */
public abstract class Page {
    // Contents
    protected List<Section> sections;
    // Properties
    protected String fileName;
    protected String title;
    protected String date;
    protected String shortTitle;
    protected String description;
    protected List<Category> categories;
    protected String sitePath;
    protected String url;
    protected boolean hasBanner = false;
    protected boolean containsCode = false;
    public static boolean grabDescription = false;

    public Page(File sourceFile) {
        sections = MarkdownReader.readSections(sourceFile);
        sitePath = createSitePath();
        url = createURL();
    }

    public String getFileName() {
        // TODO implements things here
        // TODO This is a placeholder function
        return fileName;
    }

    protected abstract String createURL();

    protected abstract String createSitePath();

    public void write() {
        HTMLWriter.prepareToWrite(this);

        TemplateWriter.write(0);

        // TODO make nicer
        HTMLWriter.writeLine("<meta name=\"description\" content=\"" + description + "\">");

        TemplateWriter.write(1);

        if(containsCode) {
            HTMLWriter.writeLine(ResourceFetcher.getString(
                    Strings.CodeHighlightStylesheet));
        }

        TemplateWriter.write(2);

        if(containsCode) {
            HTMLWriter.writeLines(ResourceFetcher.getStringList(
                    StringLists.CodeHighlightScript));
        }

        if (hasBanner) {
            writeBanner();
        }

        sections.forEach(Section::writeHTML);

        TemplateWriter.write(3);
        TemplateWriter.write(4);
        TemplateWriter.write(5);
    }

    private void writeBanner() {
        HTMLWriter.writeLine("<section id =\"content_header\">");
        HTMLWriter.writeLine("<h2 id=\"title\">" + title + "</h2>");

        HTMLWriter.writeLine("<h5 id=\"date\"><i class=\"fa fa-calendar-o\"></i>@nbsp;");
        HTMLWriter.writeLine(date);
        HTMLWriter.writeLine("</h5>");

        HTMLWriter.writeLine("<img id=\"banner\" src=\"/images/"
                + TextHelper.simplify(shortTitle) + "_ban.png\"" + title + "\">");
        HTMLWriter.writeLine("</section>");
    }

}
