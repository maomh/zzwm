package site.mhjn.zzwm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtil {
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }
}
