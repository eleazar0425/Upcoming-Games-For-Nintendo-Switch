package projects.eleazar0425.nintendoswitchgames.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class OrderingUtil {

    public enum OrderBy {
        TITLE,
        DATE,
        DATE_DESCENDING,
        NUMBER,
        NUMBER_DESCENDING
    }

    /**
     *
     * @param list list of any type
     * @param orderBy
     * @param fieldName is the field used to sorting the list, is extremely important that it
     *                  matches the name en the class T and corresponds to the type of specified
     *                  sortingType (a title or a date)
     */
    public static <T> void sort(List<T> list, final OrderBy orderBy, final String fieldName){
        try{

            Collections.sort(list, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    String element1 = get(o1, fieldName);
                    String element2 = get(o2, fieldName);
                    switch (orderBy){
                        case DATE:
                            return DateUtil.parse(element1).compareTo(DateUtil.parse(element2));
                        case DATE_DESCENDING:
                            return DateUtil.parse(element2).compareTo(DateUtil.parse(element1));
                        case TITLE:
                            return element1.toLowerCase().compareTo(element2.toLowerCase());
                        case NUMBER:
                            return Double.parseDouble(element1) < Double.parseDouble(element2) ? -1
                                    : (Double.parseDouble(element1) == Double.parseDouble(element2) ? 0 : 1);
                        case NUMBER_DESCENDING:
                            return  Double.parseDouble(element1) > Double.parseDouble(element2) ? -1
                                    : (Double.parseDouble(element1) == Double.parseDouble(element2) ? 0 : 1);
                        default:
                            return 0;
                    }
                }
            });
        }catch (Exception ex){
            return;
        }
    }

    /**
     *
     * @param object
     * @param fieldName
     * @param <V>
     * @return the value of the specified fieldName in the object
     */
    private static <V> V get(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (V) field.get(object);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }
}