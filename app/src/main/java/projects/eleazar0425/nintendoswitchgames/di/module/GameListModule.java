package projects.eleazar0425.nintendoswitchgames.di.module;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import projects.eleazar0425.nintendoswitchgames.Application;
import projects.eleazar0425.nintendoswitchgames.data.GameListRepository;
import projects.eleazar0425.nintendoswitchgames.data.local.GameListLocalDataSource;
import projects.eleazar0425.nintendoswitchgames.data.remote.GameListRemoteDataSource;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.GameListPresenter;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.GameListingContract;
import projects.eleazar0425.nintendoswitchgames.net.service.GameListService;

/**
 * Created by Eleazar Estrella on 6/20/17.
 */

@Module public class GameListModule {
    private GameListingContract.View view;

    public GameListModule(GameListingContract.View view) {
        this.view = view;
    }

    @Provides public GameListingContract.View provideView(){
        return view;
    }

    @Provides
    public GameListingContract.Presenter providesGameListPresenter(GameListingContract.View view, GameListRepository repository){
        return new GameListPresenter(repository, view);
    }

    @Provides
    public GameListRemoteDataSource providesRemoteDataSource(GameListService service){
        return new GameListRemoteDataSource(service);
    }

    @Provides
    public GameListLocalDataSource providesLocalDataSource(Realm realm){
        return new GameListLocalDataSource(realm);
    }

    @Provides
    public GameListRepository providesRepository(Application context, GameListRemoteDataSource remoteDataSource,
                                                GameListLocalDataSource localDataSource){
        return new GameListRepository(context, remoteDataSource, localDataSource);
    }
}
