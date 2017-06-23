package projects.eleazar0425.nintendoswitchgames.data;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class DataNoAvailableException extends Exception {
    @Override
    public String getMessage() {
        return "There's was an error, please try later";
    }
}
