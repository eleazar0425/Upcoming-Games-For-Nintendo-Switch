package projects.eleazar0425.nintendoswitchgames.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class NetworkAvailability {

    public static boolean isNetworkAvailable(Context context) {
        if(context != null){
            ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if ( activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }
}
