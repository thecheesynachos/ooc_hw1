package muic.ooc.hw1.q4.crawler;

import org.apache.commons.io.FilenameUtils;

public class DirectoryStringFixer {

    private final static String[] REMOVED_STRINGS = {"#", "?", "\'", "javascript:"};

    public static String cleanUrl(String url){

        for (String s: REMOVED_STRINGS) {
            int i = url.indexOf(s);
            if (i != -1) url = url.substring(0, i);
        }

        return url;

    }

    public static String connectUrl(String currentDir, String newDir){

        return FilenameUtils.normalize(cleanUrl(FilenameUtils.getPath(currentDir) + newDir));

    }

}
