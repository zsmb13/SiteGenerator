import dom.roots.Article;
import dom.roots.Page;
import dom.roots.Project;
import dom.roots.custom.*;
import resources.ResourceFetcher;
import resources.Strings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zsmb on 2016-07-06.
 */
public class Main {

    private static List<File> getSourceFilesFromDir(String dirName) {
        File directory = new File(ResourceFetcher.getString(Strings.SourceDir) + dirName);

        if (!directory.exists() || !directory.isDirectory()) {
            // TODO error handling
            System.err.println("Directory error: " + dirName);
            return null;
        }

        return Arrays.asList(directory.listFiles());
    }

    public static void main(String[] args) {
        // Get source files
        // TODO? make this more generic and customizable
        List<File> articleFiles = getSourceFilesFromDir("articles/");
        List<File> abandonedProjectFiles = getSourceFilesFromDir("projects/abandoned/");
        List<File> completedProjectFiles = getSourceFilesFromDir("projects/completed/");

        List<Article> articles = new ArrayList<>();
        List<Project> abandonedProjects = new ArrayList<>();
        List<Project> completedProjects = new ArrayList<>();

        for (File f : articleFiles) {
            articles.add(new Article(f));
        }
        for (File f : abandonedProjectFiles) {
            abandonedProjects.add(new Project(f));
        }
        for (File f : completedProjectFiles) {
            completedProjects.add(new Project(f));
        }

        List<Page> pages = new ArrayList<>();
        pages.addAll(articles);
        pages.addAll(abandonedProjects);
        pages.addAll(completedProjects);

        //TODO create directories if they don't exist


        AboutPage aboutPage = new AboutPage();
        pages.add(aboutPage);

        ArchivesPage archivesPage = ArchivesPage.create();
        pages.add(archivesPage);

        List<IndexPage> indexPages = IndexPage.create();
        pages.addAll(indexPages);

        LostPage lostPage = new LostPage();
        pages.add(lostPage);

        ProjectsPage.ProjectCategory completed = new ProjectsPage.ProjectCategory(
                "Completed projects", "This is the description of the completed projects", completedProjects
        );
        ProjectsPage.ProjectCategory abandoned = new ProjectsPage.ProjectCategory(
                "Abandoned projects", "This is the description of the abandoned projects", abandonedProjects
        );
        List<ProjectsPage.ProjectCategory> categories = new ArrayList<>();
        categories.add(completed);
        categories.add(abandoned);
        ProjectsPage projectsPage = ProjectsPage.create(categories);
        pages.add(projectsPage);


        // Write everything

        for (Page p : pages) {
            p.write();
        }
    }
}
