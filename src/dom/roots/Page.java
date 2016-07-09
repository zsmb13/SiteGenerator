package dom.roots;

import dom.elements.Section;
import io.HTMLWriter;
import io.MarkdownReader;
import io.TemplateWriter;
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
    protected String shortTitle;
    protected String description;
    protected List<Category> categories;
    protected String sitePath;
    protected String url;
    protected boolean hasBanner = false;
    protected boolean containsCode = false;

    public Page(File sourceFile) {
        sections = MarkdownReader.readSections(sourceFile);
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
        //TODO implement
    }

}
