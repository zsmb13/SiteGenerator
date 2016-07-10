package io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zsmb on 2016-07-07.
 */
public class TextHelper {

    public static String process(String s) {
        String link = "(.*?)\\[(.*?)\\]\\((.*?[^\\\\])\\)(?:\\{(.*?)\\})?(?:\"(.*?)\")?(.*)";
        Pattern linkPattern = Pattern.compile(link);

        boolean hasLink = true;
        while(hasLink) {
            Matcher matcher = linkPattern.matcher(s);
            hasLink = matcher.find();

            if(hasLink) {
                s = processLink(matcher);
            }
        }

        String icon = "(.*?)\\{(fa-.*?)\\}(.*)";
        Pattern iconPattern = Pattern.compile(icon);

        boolean hasIcon = true;
        while(hasIcon) {
            Matcher matcher = iconPattern.matcher(s);
            hasIcon = matcher.find();

            if(hasIcon) {
                s = processIcon(matcher);
            }
        }

        return s;
    }

    private static String processIcon(Matcher matcher) {
        String beginning = matcher.group(1);
        String end = matcher.group(3);

        String icon = matcher.group(2);

        return beginning + "<i class=\"fa " + icon + "\"></i>" + end;
    }

    private static String processLink(Matcher matcher) {
        String beginning = matcher.group(1);
        String end = matcher.group(6);

        String text = matcher.group(2);
        String link = matcher.group(3);
        String icon = matcher.group(4);
        String hover = matcher.group(5);

        return beginning + createLinkHTML(text, link, icon, hover) + end;
    }

    private static String createLinkHTML(String text, String link, String icon, String hover) {
        boolean isExternal = link.charAt(0) != '/';

        int firstSpace = text.indexOf(' ');
        //TODO check if this is working and is indexed right
        String firstWord = text.substring(0, firstSpace-1);
        String restOfText = text.substring(firstSpace+1);

        return "<a href=\"" + link + "\" " +
                (hover.isEmpty() ? "" : "title = \"" + hover + "\" ") +
                (isExternal ? "target=\"blank\" " : "") +
                "><span class=\"nowrap\">" +
                "<i class=\"fa " + icon + "\"></i>&nbsp;" +
                firstWord + "</span>" + restOfText + "</a>";
    }

    public static String stripHTML(String s) {
        // remove tags
        s = s.replaceAll("<.*?>", "");
        // remove double spaces
        s = s.replace("  ", " ");

        return s;
    }

}
