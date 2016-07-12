package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zsmb on 2016-07-07.
 */
public class ResourceFetcher {

    private static Map<Strings, String> stringStorage = new HashMap<>();
    private static Map<StringLists, List<String>> stringListStorage = new HashMap<>();

    public static String getString(Strings stringIdentifier) {
        if (!stringStorage.containsKey(stringIdentifier)) {
            String str = fetchString(stringIdentifier.toString());
            stringStorage.put(stringIdentifier, str);
        }

        return stringStorage.get(stringIdentifier);
    }

    private static String fetchString(String s) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File("res/strings.res")))) {

            String temp;
            do {
                temp = br.readLine();
            } while (temp != null && !temp.equals(s));

            if (temp == null) {
                System.err.println("Invalid String resource requested");
                return null;
            }

            // return the next line
            return br.readLine();

        } catch (IOException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getStringList(StringLists stringListIdentifier) {
        if (!stringListStorage.containsKey(stringListIdentifier)) {
            List<String> strings = fetchStringList(stringListIdentifier.toString());
            stringListStorage.put(stringListIdentifier, strings);
        }

        return stringListStorage.get(stringListIdentifier);
    }

    private static List<String> fetchStringList(String s) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File("res/stringlists.res")))) {

            String temp;
            do {
                temp = br.readLine();
            } while (temp != null && !temp.equals(s));

            if (temp == null) {
                System.err.println("Invalid String resource requested");
                return null;
            }

            // return the next line
            List<String> stringList = new ArrayList<>();
            while ((temp = br.readLine()) != null && !temp.trim().equals("")) {
                stringList.add(temp);
            }
            return stringList;

        } catch (IOException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        }
    }

}
