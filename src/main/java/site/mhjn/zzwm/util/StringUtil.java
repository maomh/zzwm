package site.mhjn.zzwm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str 可为null
     * @return true: 为空, false: 不为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否不为空
     * @param str 可为null
     * @return true: 不为空, false: 为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
