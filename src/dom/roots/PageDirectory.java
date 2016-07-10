package dom.roots;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsmb on 2016-07-10.
 */
public class PageDirectory {

    // Access to current page
    private static Page current = null;

    public static Page getCurrentPage() {
        return current;
    }

    static void setCurrentPage(Page page) {
        current = page;
        awaitingDesc = false;
    }

    // Description management
    private static boolean awaitingDesc = false;

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

    // Post keeping
    private static List<Page> postedPages = new ArrayList<>();

    static void post(Page page) {
        postedPages.add(page);
    }

}
