package dom.roots.custom;

import dom.elements.CustomHTML;
import dom.elements.Section;
import dom.roots.Page;
import dom.roots.PageDirectory;
import io.TextHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zsmb on 2016-07-09.
 */
public class IndexPage extends CustomPage {

    private static final int postsPerPage = 10;
    private int index;

    public IndexPage(List<Section> sections, int index) {
        this.sections = sections;
        this.index = index;

        //TODO extract to resource
        description = "The index page of the website";
    }

    public static List<IndexPage> create() {
        List<Page> posts = PageDirectory.getPostedPages();

        int postCount = posts.size();

        // Determine the number of required index pages
        int indexCount = postCount / 10;
        if (indexCount * 10 < postCount) {
            indexCount++;
        }

        List<IndexPage> indexPages = new ArrayList<>();
        for (int i = 0; i < indexCount; i++) {
            IndexPage indexPage = createPage(posts.subList(i * 10, Math.min((i + 1) * 10, postCount)), i, indexCount - 1);
            indexPages.add(indexPage);
        }

        return indexPages;
    }

    private static String prevPageLink(int index) {
        if (index < 1) {
            return null;
        }

        if (index == 1) {
            return "/";
        }

        return "/page/" + index + "/";
    }

    private static String nextPageLink(int index, int lastIndex) {
        if (index == lastIndex) {
            return null;
        }

        return "/page/" + (index + 2) + "/";
    }

    //TODO do something with this mess
    private static IndexPage createPage(List<Page> pages, int index, int lastIndex) {
        List<Section> sections = new ArrayList<>();
        for (Page p : pages) {
            sections.add(createSection(p));
        }

        // There is more than one page
        if (lastIndex > 0) {
            List<String> navigation = new ArrayList<>();
            navigation.add("<section class=\"index\" id=\"indexnav\">");

            // This is the first page
            if (index == 0) {
                navigation.add("<div id=\"navsingle\">");
                navigation.add("<a href=\"" + nextPageLink(index, lastIndex) + "\" class=\"menubutton\">Older posts</a>");
            }
            // This is the last page
            else if (index == lastIndex) {
                navigation.add("<div id=\"navsingle\">");
                navigation.add("<a href=\"" + prevPageLink(index) + "\" class=\"menubutton\">Newer posts</a>");
            }
            // This is one of the middle pages
            else {
                navigation.add("<div id=\"navdouble\">");
                navigation.add("<a href=\"" + prevPageLink(index) + "\" class=\"menubutton\">Newer posts</a>");
                navigation.add("<a href=\"" + nextPageLink(index, lastIndex) + "\" class=\"menubutton\">Older posts</a>");
            }

            Section navSection = new Section("index", "indexnav");
            navSection.add(new CustomHTML(navigation));
            sections.add(navSection);
        }

        return new IndexPage(sections, index);
    }

    //TODO do something with this mess
    private static Section createSection(Page p) {
        Section s = new Section("index");

        String postName = p.getPostTitle();

        String[] lines = {
                "<h2><a href=\"" + p.getUrl() + "\">",
                postName,
                "</a></h2>",
                "<h5 class=\"date\"><i class=\"fa fa-calendar-o\"></i>&nbsp;",
                p.getDate(),
                "</h5>",
                "<a href=\"" + p.getUrl() + "\">",
                "<img class=\"articleimage\" " +
                        "src=\"/images/" + TextHelper.simplify(p.getShortTitle()) + "_ban.png " +
                        "alt=\"" + postName + "\">",
                "</a>",
                "<p>" + p.getDescription() + " <a href=\"" + p.getUrl() + "\">Read more...</a></p>"
        };

        s.add(new CustomHTML(Arrays.asList(lines)));
        return s;
    }

    @Override
    protected String createURL() {
        if (index == 0) {
            return "/";
        }
        //TODO ensure this function is actually called
        return "/page/" + (index + 1) + "/";
    }

    @Override
    protected String createSitePath() {
        return "";
    }

    @Override
    protected String createFilename() {
        if (index == 0) {
            return "index.html";
        }

        return "index" + (index + 1) + ".html";
    }

}
