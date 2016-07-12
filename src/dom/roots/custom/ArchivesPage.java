package dom.roots.custom;

import dom.elements.CustomHTML;
import dom.elements.Element;
import dom.elements.Section;
import dom.roots.PageDirectory;
import dom.roots.Project;
import resources.ResourceFetcher;
import resources.StringLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zsmb on 2016-07-11.
 */
public class ArchivesPage extends CustomPage {
    private ArchivesPage(List<Section> sections) {
        this.sections = sections;
    }

    public static ArchivesPage create() {
        List<Section> sections = new ArrayList<>();
        Section archiveSection = new Section(null, "archives");

        // Archives table
        archiveSection.add(new CustomHTML(ResourceFetcher.getStringList(StringLists.ArchiveHeader)));

        List<Project> projects = PageDirectory.getProjects();
        for (Project p : projects) {
            archiveSection.add(createRow(p));
        }

        archiveSection.add(new CustomHTML("</table>"));

        // Kittens
        Section kittenSection = new Section(null, "kittens");
        archiveSection.add(new CustomHTML(ResourceFetcher.getStringList(StringLists.ArchiveKittens)));

        // Add created sections
        sections.add(archiveSection);
        sections.add(kittenSection);

        return new ArchivesPage(sections);
    }

    private static Element createRow(Project p) {
        String[] lines = {
                "<tr>",
                "<td class=\"articletable\"><a href=\"" + p.getUrl() + "\">" + p.getPostTitle() + "</a></td>",
                "<td>" + p.getCategoriesString() + "</td>",
                "<td>" + p.getDate() + "</td>",
                "</tr>"
        };

        return new CustomHTML(Arrays.asList(lines));
    }

    @Override
    protected String createURL() {
        return "/archives/";
    }

    @Override
    protected String createSitePath() {
        return "";
    }

    @Override
    protected String createFilename() {
        return "archives.html";
    }
}
