package dom.roots.custom;

import resources.ResourceFetcher;
import resources.Strings;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by zsmb on 2016-07-11.
 */
public class AboutPage extends CustomPage {

    public AboutPage() {
        super(new File(ResourceFetcher.getString(Strings.SourceDir) + "about.txt"));

        description = ResourceFetcher.getString(Strings.AboutDesc);
    }

    @Override
    protected String createURL() {
        return "/about/";
    }

    @Override
    protected String createSitePath() {
        return "";
    }

    @Override
    protected String createFilename() {
        return "about.html";
    }

}
