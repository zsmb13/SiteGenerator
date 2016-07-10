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

    // Private properties
    protected String fileName;
    protected boolean hasBanner = false;
    private String sitePath;
    private String url;

    // Settable properties
    private String description = null;
    private String date = null;
    private String title = null;
    private String shortTitle = null;
    private String language = null;

    private List<Category> categories = null;

    private boolean containsCode = false;
    private boolean post = false;

    // Public static properties
    public static boolean grabDescription = false;
    public static Page current = null;

    // Setters
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategories(List<Category> categories) {
        //TODO maybe check?
        this.categories = categories;
    }

    public void setDescription(String description) {
        grabDescription = false;
        this.description = description;
    }

    public void setContainsCode(boolean containsCode) {
        this.containsCode = containsCode;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public void setLanguage(String language) {
        if(ResourceFetcher.getStringList(StringLists.Languages).contains(language)) {
            this.language = language;
        }
    }

    public void setDate(String date) {
        //TODO validate
        this.date = date;
    }


    public Page(File sourceFile) {
        current = this;

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
