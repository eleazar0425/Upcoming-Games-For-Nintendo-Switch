package projects.eleazar0425.nintendoswitchgames;

import projects.eleazar0425.nintendoswitchgames.di.component.AppComponent;
import projects.eleazar0425.nintendoswitchgames.di.component.DaggerAppComponent;
import projects.eleazar0425.nintendoswitchgames.di.module.AppModule;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class Application extends android.app.Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGraph();
    }

    private void setupGraph(){
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

