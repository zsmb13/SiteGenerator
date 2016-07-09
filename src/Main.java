import dom.roots.Article;
import dom.roots.Page;
import dom.roots.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zsmb on 2016-07-06.
 */
public class Main {

    private static List<File> getSourceFilesFromDir(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            // TODO error handling
            return null;
        }

        return Arrays.asList(directory.listFiles());
    }

    public static void main(String[] args) {
        // Get source files
        // TODO? make this more generic and customizable
        List<File> articleFiles = getSourceFilesFromDir(new File("articles/"));
        List<File> abandonedProjectFiles = getSourceFilesFromDir(new File("projects/abandoned/"));
        List<File> completedProjectFiles = getSourceFilesFromDir(new File("projects/completed/"));

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

        for (Page p : pages) {
            p.write();
        }
    }

}
