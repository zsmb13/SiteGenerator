package dom.roots;

import io.TextHelper;

import java.io.File;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Article extends Page {

    public Article(File sourceFile) {
        super(sourceFile);

        hasBanner = true;
    }

    @Override
    protected String createURL() {
        return sitePath + TextHelper.hyphenate(shortTitle) + "/";
    }

    @Override
    protected String createSitePath() {
        return "/articles/";
    }

    @Override
    protected String createFilename() {
        return TextHelper.hyphenate(shortTitle) + ".html";
    }
}
