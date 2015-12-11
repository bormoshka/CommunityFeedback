package ru.ulmc.communityFeedback.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static String SAFE_REGEXP = "^((?!(delete |select |insert |update |alter |drop |truncate |</\\w+>|<\\w+>|<script)).)*$";
    public static Pattern SAFE = Pattern.compile(SAFE_REGEXP, Pattern.CASE_INSENSITIVE);

    public static boolean matches(Pattern pattern, String str) {
        Matcher mat = pattern.matcher(str);
        return (mat != null && mat.matches());
    }
    public static boolean validateLogin(String str) {
        return matches(SAFE, str);
    }
}
