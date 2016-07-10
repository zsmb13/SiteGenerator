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
        while (hasLink) {
            Matcher matcher = linkPattern.matcher(s);
            hasLink = matcher.find();

            if (hasLink) {
                s = processLink(matcher);
            }
        }

        String icon = "(.*?)\\{(fa-.*?)\\}(.*)";
        Pattern iconPattern = Pattern.compile(icon);

        boolean hasIcon = true;
        while (hasIcon) {
            Matcher matcher = iconPattern.matcher(s);
            hasIcon = matcher.find();

            if (hasIcon) {
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
        String firstWord = text.substring(0, firstSpace - 1);
        String restOfText = text.substring(firstSpace + 1);

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

    public static String simplify(String s) {
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isAlphaNum(c)) {
                temp = temp + c;
            }
        }
        return temp.toLowerCase();
    }

    private static boolean isAlphaNum(char c) {
        return ('a' <= c && c <= 'z')
                || ('A' <= c && c <= 'Z')
                || ('0' <= c && c <= '9');
    }

    public static String hyphenate(String s) {
        String temp = "";
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(isAlphaNum(c)) {
                temp = temp + c;
            }
            else if(c == ' ' || c == '-' || c == '_') {
                temp = temp + '-';
            }
        }
        return temp.toLowerCase();
    }
}
