package dom.roots;

/**
 * Created by zsmb on 2016-07-09.
 */
public enum Category {
    Tutorial, Article, NewProject, SiteNews, TestCategory;

    //TODO consider moving this to a resource file?
    @Override
    public String toString() {
        switch (this) {
            case Article:
                return "Article";
            case NewProject:
                return "New project";
            case SiteNews:
                return "Site news";
            case TestCategory:
                return "Test category";
            case Tutorial:
                return "Tutorial";
            default:
                return "Unknown category";
        }
    }

    public static Category parse(String catDesc) {
        switch(catDesc.toLowerCase()) {
            case "article":
                return Article;
            case "tutorial":
                return Tutorial;
            case "project":
            case "newproject":
                return NewProject;
            case "test":
                return TestCategory;
            case "site":
            case "sitenews":
                return SiteNews;
            default:
                System.err.println("Can't parse category description");
                return null;
        }
    }
}
