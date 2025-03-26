package site.mhjn.zzwm.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class CollectionUtil {

    /**
     * 判断集合是否为空
     * @param collection 可为null
     * @return true: 为空, false: 不为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空
     * @param collection 可为null
     * @return true: 不为空, false: 为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
