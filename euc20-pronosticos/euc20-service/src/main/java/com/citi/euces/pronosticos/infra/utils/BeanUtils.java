/**
 *
 */
package com.citi.euces.pronosticos.infra.utils;

import java.util.List;
import java.util.Set;

/**
 *
 * @author lbermejo
 *
 * TODO Commons
 */
public final class BeanUtils {

    private BeanUtils() {
    }

    public static boolean isNull(final Object object) {
        return object == null;
    }

    public static boolean isNotNull(final Object object) {
        return !isNull(object);
    }

    public static boolean isFalse(boolean expresion) {
        return !expresion;
    }

    public static boolean isEmpty(String string) {
        return isNull(string) || string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return isNotNull(string) && !string.isEmpty();
    }

    public static boolean isEmpty(List<?> list) {
        boolean output = false;
        if (isNotNull(list)) {
            output = list.isEmpty();
        }
        return output;
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(Set<?> set) {
        boolean output = false;
        if (isNotNull(set)) {
            output = set.isEmpty();
        }
        return output;
    }

    public static boolean isNotEmpty(Set<?> set) {
        return !isEmpty(set);
    }
}
