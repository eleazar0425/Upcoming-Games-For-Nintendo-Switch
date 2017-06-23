package projects.eleazar0425.nintendoswitchgames.net;


import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class OkHttpClientProvider {

    public static OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);
        return builder.build();
    }
}
