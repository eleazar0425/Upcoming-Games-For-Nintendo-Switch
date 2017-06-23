package projects.eleazar0425.nintendoswitchgames.di.component;

import javax.inject.Singleton;
import dagger.Component;
import io.realm.Realm;
import projects.eleazar0425.nintendoswitchgames.Application;
import projects.eleazar0425.nintendoswitchgames.di.module.AppModule;
import projects.eleazar0425.nintendoswitchgames.net.service.GameListService;

/**
 * Created by Eleazar Estrella on 6/20/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(Application application);

    Application provideApplication();

    Realm provideRealm();

    GameListService provideGameListService();
}
