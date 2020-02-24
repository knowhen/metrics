package when.metrics.utils;

/**
 * @author: when
 * @create: 2020-02-24  09:38
 **/
public class StringUtils {
    public static boolean isBlank(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(value.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
