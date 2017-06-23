package projects.eleazar0425.nintendoswitchgames.util;


import java.util.List;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class FilterUtil {

    public interface Predicate<T> {
        boolean satisfyCondition(T t);
    }

   public static  <T> T findFirstElement(List<T> list, Predicate<T> predicate){
        for(T element: list){
            if(predicate.satisfyCondition(element)){
                return element;
            }
        }
        return null;
   }

    public static  <T> int findFirstElementPosition(List<T> list, Predicate<T> predicate){
        for(int x=0; x<list.size(); x++){
            T element = list.get(x);
            if(predicate.satisfyCondition(element)){
                return x;
            }
        }
        return -1;
    }
}
