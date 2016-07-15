package dom.roots;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zsmb on 2016-07-10.
 */
public class PageDirectory {

    // Access to current page
    private static Page current = null;
    // Description management
    private static boolean awaitingDesc = false;
    // Post keeping
    private static List<Page> postedPages = new ArrayList<>();
    // Project keeping
    private static List<Project> projects = new ArrayList<>();

    public static Page getCurrentPage() {
        return current;
    }

    static void setCurrentPage(Page page) {
        current = page;
        awaitingDesc = false;
    }

    public static void prepareForDesc() {
        awaitingDesc = true;
    }

    public static boolean wantsDesc() {
        return awaitingDesc;
    }

    public static void setCurrentDesc(String description) {
        current.setDescription(description);
        awaitingDesc = false;
    }

    static void post(Page page) {
        postedPages.add(page);
    }

    public static List<Page> getPostedPages() {
        postedPages.sort(new Comparator<Page>() {
            @Override
            public int compare(Page o1, Page o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return postedPages;
    }

    static void addProject(Project p) {
        projects.add(p);
    }

    public static List<Project> getProjects() {
        return projects;
    }

}
