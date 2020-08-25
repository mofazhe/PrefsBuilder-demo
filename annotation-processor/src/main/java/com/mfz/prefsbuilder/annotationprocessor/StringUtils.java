package com.mfz.prefsbuilder.annotationprocessor;

import java.util.Locale;

import javax.annotation.Nullable;

/**
 * @author mz
 * @date 2020/05/19/Tue
 * @time 11:21
 */
public class StringUtils {
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(@Nullable CharSequence str) {
        return !isEmpty(str);
    }

    public static String format(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    /**
     * TEST_STRING 转 TestString
     *
     * @param constName the const name
     * @return the string
     */
    public static String const2BigCamel(String constName) {
        String lowerCase = constName.toLowerCase();
        char[] charArray = lowerCase.toCharArray();
        if (charArray.length <= 0) {
            return constName;
        }
        charArray[0] = Character.toUpperCase(charArray[0]);
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char c = charArray[i];
            if (c == '_') {
                int nextI = i + 1;
                if (nextI < charArrayLength && charArray[nextI] != '_') {
                    charArray[nextI] = Character.toUpperCase(charArray[nextI]);
                }
            }
        }
        return new String(charArray).replace("_", "");
    }

    /**
     * TestString 转 test_string
     *
     * @param camelString the camel string
     * @return the string
     */
    public static String camel2SmallConst(String camelString) {
        char[] charArray = camelString.toCharArray();
        StringBuilder sb = new StringBuilder();
        if (charArray.length <= 0) {
            return camelString;
        }
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char c = charArray[i];
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
