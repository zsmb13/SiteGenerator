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
    protected String sitePath;
    protected String url;

    // Settable properties
    protected String description = null;
    protected String date = null;
    protected String title = null;
    protected String shortTitle = null;
    protected String language = null;

    protected List<Category> categories = null;

    private boolean containsCode = false;
    private boolean post = false;

    public Page(File sourceFile) {
        PageDirectory.setCurrentPage(this);

        if (sourceFile != null) {
            MarkdownReader.readPageProps(sourceFile);
            sections = MarkdownReader.readSections();
        }

        sitePath = createSitePath();
        url = createURL();
        fileName = createFilename();

        PageDirectory.setCurrentPage(null);
    }

    // Setters
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setDescription(String description) {
        if (this.description == null) {
            this.description = description;
        }
    }

    public void setContainsCode(boolean containsCode) {
        this.containsCode = containsCode;
    }

    public void setPost(boolean post) {
        this.post = post;

        if (post) {
            PageDirectory.post(this);
        }
    }

    public void setLanguage(String language) {
        if (ResourceFetcher.getStringList(StringLists.Languages).contains(language)) {
            this.language = language;
        }
    }

    public void setDate(String date) {
        String[] pieces = date.split(" ");
        this.date = pieces[0] + "." + pieces[1] + "." + pieces[2] + ".";
    }

    // Getters

    public String getFileName() {
        return fileName;
    }

    public String getSitePath() {
        return sitePath;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getDescription() {
        return description;
    }

    protected abstract String createURL();

    protected abstract String createSitePath();

    protected abstract String createFilename();

    public void write() {
        HTMLWriter.prepareToWrite(this);

        TemplateWriter.write(0);

        // TODO make nicer
        HTMLWriter.writeLine("<meta name=\"description\" content=\"" + TextHelper.stripHTML(description) + "\">");

        TemplateWriter.write(1);

        if (containsCode) {
            HTMLWriter.writeLine(ResourceFetcher.getString(
                    Strings.CodeHighlightStylesheet));
        }

        TemplateWriter.write(2);

        if (containsCode) {
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

        HTMLWriter.finishWriting();
    }

    private void writeBanner() {
        HTMLWriter.writeLine("<section id=\"content_header\">");
        HTMLWriter.writeLine("<h2 id=\"title\">" + title + "</h2>");

        HTMLWriter.writeLine("<h5 id=\"date\"><i class=\"fa fa-calendar-o\"></i>&nbsp;");
        HTMLWriter.writeLine(date);
        HTMLWriter.writeLine("</h5>");

        HTMLWriter.writeLine("<img id=\"banner\" src=\"/images/"
                + TextHelper.simplify(shortTitle) + "_ban.png\""
                + " alt=\"" + title + "\">");
        HTMLWriter.writeLine("</section>");
    }

    public String getCategoriesString() {
        if (categories.isEmpty()) {
            return "";
        }

        String temp = categories.get(0).toString();

        for (int i = 1; i < categories.size(); i++) {
            temp += " / " + categories.get(i).toString();
        }

        return temp;
    }

    public String getPostTitle() {
        return categories.contains(Category.NewProject) ? "New project: " + title : title;
    }

    public String getLanguage() {
        return language;
    }
}
