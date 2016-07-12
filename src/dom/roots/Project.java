package dom.roots;

import io.TextHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Project extends Page {

    public Project(File sourceFile) {
        super(sourceFile);

        hasBanner = true;

        if(categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(Category.NewProject);

        PageDirectory.addProject(this);
    }

    @Override
    protected String createURL() {
        return "/projects/" + TextHelper.simplify(shortTitle) + "/";
    }

    @Override
    protected String createSitePath() {
        return "projects/" + TextHelper.simplify(shortTitle) + "/";
    }

    @Override
    protected String createFilename() {
        return TextHelper.simplify(shortTitle) + ".html";
    }
}
