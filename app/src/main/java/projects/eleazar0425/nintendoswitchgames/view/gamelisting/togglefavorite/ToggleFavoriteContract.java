package projects.eleazar0425.nintendoswitchgames.view.gamelisting.togglefavorite;

import projects.eleazar0425.nintendoswitchgames.BasePresenter;
import projects.eleazar0425.nintendoswitchgames.BaseView;
import projects.eleazar0425.nintendoswitchgames.model.Game;

/**
 * Created by Eleazar Estrella on 6/23/17.
 */

public class ToggleFavoriteContract {

    public interface View extends BaseView<Presenter> {
        void onFavoriteUpdated(Game game, boolean result);
    }

    public interface Presenter extends BasePresenter {
        void setFavorite(Game game, boolean favorite);
    }
}
