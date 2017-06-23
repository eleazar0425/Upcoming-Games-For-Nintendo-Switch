package projects.eleazar0425.nintendoswitchgames.di.component;

import dagger.Component;
import projects.eleazar0425.nintendoswitchgames.di.ActivityScope;
import projects.eleazar0425.nintendoswitchgames.di.module.ToggleFavoriteModule;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.GameListAdapter;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.togglefavorite.ToggleFavoritePresenter;

/**
 * Created by Eleazar Estrella on 6/23/17.
 */
@ActivityScope
@Component(modules = {ToggleFavoriteModule.class}, dependencies = {AppComponent.class})
public interface ToggleFavoriteComponent {
    ToggleFavoritePresenter provideToggleFavoritePresenter();
    void inject(GameListAdapter.GameListViewHolder gameListViewHolder);
}
