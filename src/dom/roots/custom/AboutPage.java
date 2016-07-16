package dom.roots.custom;

import resources.ResourceFetcher;
import resources.Strings;

import java.io.File;

/**
 * Created by zsmb on 2016-07-11.
 */
public class AboutPage extends CustomPage {

    public AboutPage() {
        // This page is built from a markdown text file
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
