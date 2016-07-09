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
}
