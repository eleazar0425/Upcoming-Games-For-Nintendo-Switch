package projects.eleazar0425.nintendoswitchgames.di.module;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.togglefavorite.ToggleFavoriteContract;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.togglefavorite.ToggleFavoritePresenter;

/**
 * Created by Eleazar Estrella on 6/23/17.
 */

@Module
public class ToggleFavoriteModule {

    private ToggleFavoriteContract.View view;

    public ToggleFavoriteModule(ToggleFavoriteContract.View view) {
        this.view = view;
    }

    @Provides
    public ToggleFavoriteContract.View providesView(){
        return view;
    }

    @Provides
    public ToggleFavoritePresenter providesToggleFavoritePresenter(Realm realm, ToggleFavoriteContract.View view){
        return new ToggleFavoritePresenter(realm, view);
    }

}
