package projects.eleazar0425.nintendoswitchgames.view.gamelisting;

import java.util.List;

import projects.eleazar0425.nintendoswitchgames.data.GameListDataSource;
import projects.eleazar0425.nintendoswitchgames.data.GameListRepository;
import projects.eleazar0425.nintendoswitchgames.model.Game;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class GameListPresenter implements GameListingContract.Presenter {

    private GameListRepository repository;
    private GameListingContract.View view;

    public GameListPresenter(GameListRepository repository, GameListingContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void start() {
        //Do nothing
    }

    @Override
    public void getGameList() {
        view.showProgressBar();
        repository.getGameList(new GameListDataSource.OnGameListLoadedCallBack() {
            @Override
            public void onGameListLoaded(List<Game> gameList) {
                view.hideProgressBar();
                view.setGameList(gameList);
            }

            @Override
            public void onError(String message, Throwable t) {
                view.hideProgressBar();
                view.onError(message, t);
            }
        });
    }
}
