package projects.eleazar0425.nintendoswitchgames.view.gamelisting;

import java.util.List;

import projects.eleazar0425.nintendoswitchgames.BasePresenter;
import projects.eleazar0425.nintendoswitchgames.BaseView;
import projects.eleazar0425.nintendoswitchgames.model.Game;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class GameListingContract {

    public interface View extends BaseView<Presenter> {
        void setGameList(List<Game> gameList);
        void showProgressBar();
        void hideProgressBar();
    }

    public interface Presenter extends BasePresenter {
        void getGameList();
    }

}
