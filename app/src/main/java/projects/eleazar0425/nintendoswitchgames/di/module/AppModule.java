package projects.eleazar0425.nintendoswitchgames.di.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import projects.eleazar0425.nintendoswitchgames.Application;
import projects.eleazar0425.nintendoswitchgames.net.GsonProvider;
import projects.eleazar0425.nintendoswitchgames.net.OkHttpClientProvider;
import projects.eleazar0425.nintendoswitchgames.net.service.GameListService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eleazar Estrella on 6/20/17.
 */

@Module  public class AppModule {

    static final String BASE_URL = "http://eshopgames.azurewebsites.net/";

    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Application providesApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(){
        return OkHttpClientProvider.provideOkHttpClient();
    }

    @Provides
    public Gson providesGson(){
        return GsonProvider.provideGsonIntance();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(Gson gson, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public Realm providesRealm(Application application){
        Realm.init(application);
        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded() //by now just delete realm file
                        .build();
        Realm.setDefaultConfiguration(config);
        return Realm.getInstance(config);
    }

    @Provides
    public GameListService providesGameListService(Retrofit retrofit){
        return retrofit.create(GameListService.class);
    }
}
