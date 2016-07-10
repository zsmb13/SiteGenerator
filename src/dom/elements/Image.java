package dom.elements;

import io.HTMLWriter;

/**
 * Created by zsmb on 2016-07-07.
 */
public class Image implements Element {

    private boolean isLarge;
    private String altText;
    private String imgPath;

    public Image(String path, String alt, boolean large) {
        this.imgPath = path;
        this.altText = alt;
        this.isLarge = large;
    }

    public static Image create(String firstLine) {
        String[] pieces = firstLine.substring(1).split("\"");
        String path = pieces[0].trim();
        String alt = pieces[1];
        boolean large = firstLine.charAt(0) == '[';

        return new Image(path, alt, large);
    }

    @Override
    public void writeHTML() {
        String imageHTML =
                "<img " +
                (isLarge ? "class=\"articleimage\" " : "") +
                "alt=\"" + altText + "\" " +
                "src=\"" + imgPath + "\">";

        HTMLWriter.writeLine(imageHTML);
    }
}
