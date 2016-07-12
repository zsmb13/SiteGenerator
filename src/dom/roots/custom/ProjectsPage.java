package dom.roots.custom;

import dom.elements.*;
import dom.roots.Project;
import io.TextHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zsmb on 2016-07-11.
 */
public class ProjectsPage extends CustomPage {
    private ProjectsPage(List<Section> sections) {
        this.sections = sections;

        //TODO extract to resource
        description = "the projects page";
    }

    public static ProjectsPage create(List<ProjectCategory> categories) {
        List<Section> sections = new ArrayList<>();

        for (ProjectCategory pc : categories) {
            sections.add(createSection(pc));
        }

        return new ProjectsPage(sections);
    }

    private static Section createSection(ProjectCategory pc) {
        Section s = new Section("projectsection", TextHelper.simplify(pc.name));

        s.add(new Header(pc.name, 2));
        s.add(new Paragraph(pc.description));
        s.add(new CustomHTML("<div class=\"boxcontainer\">"));

        for (Project p : pc.projects) {
            s.add(createBox(p));
        }

        s.add(new CustomHTML("</div>"));

        return s;
    }

    private static Element createBox(Project p) {
        String simpleTitle = TextHelper.simplify(p.getShortTitle());

        String[] lines = {
                "\t<div class=\"projectbox\">",
                "\t\t<a href=\"" + p.getUrl() + "\">",
                "\t\t<img class=\"projectlang\" src=\"/images/lang_" + p.getLanguage() + ".png\">",
                "\t\t<img class=\"projectbg\" src=\"/images/" + simpleTitle + "_pr.png\" alt=\"" + p.getTitle() + "\">",
                "\t\t<img class=\"projectimage\" src=\"/images/" + simpleTitle + "_pr.png\" alt=\"" + p.getTitle() + "\">",
                "\t\t<h4>" + p.getTitle().toLowerCase() + "</h4>",
                "\t\t</a>",
                "\t</div>"
        };

        return new CustomHTML(Arrays.asList(lines));
    }

    @Override
    protected String createURL() {
        return "/projects/";
    }

    @Override
    protected String createSitePath() {
        return "";
    }

    @Override
    protected String createFilename() {
        return "projects.html";
    }

    public static class ProjectCategory {
        public String name;
        public String description;
        public List<Project> projects;

        public ProjectCategory(String name, String description, List<Project> projects) {
            this.name = name;
            this.description = description;
            this.projects = projects;

            this.projects.sort(new Comparator<Project>() {
                @Override
                public int compare(Project o1, Project o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
        }
    }


}
