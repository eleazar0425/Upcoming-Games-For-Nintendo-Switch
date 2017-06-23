package projects.eleazar0425.nintendoswitchgames.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class GsonProvider {
    public static Gson provideGsonIntance(){
        return new GsonBuilder().create();
    }
}
