package projects.eleazar0425.nintendoswitchgames.di.component;

import dagger.Component;
import projects.eleazar0425.nintendoswitchgames.di.ActivityScope;
import projects.eleazar0425.nintendoswitchgames.di.module.GameListModule;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.GameListFragment;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.GameListingContract;

/**
 * Created by Eleazar Estrella on 6/20/17.
 */

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {GameListModule.class})
public interface GameListComponent {

    GameListingContract.Presenter provideGameListPresenter();

    void inject(GameListFragment gameListFragment);
}
