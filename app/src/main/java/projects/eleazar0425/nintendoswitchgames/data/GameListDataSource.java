package projects.eleazar0425.nintendoswitchgames.data;

import java.util.List;
import projects.eleazar0425.nintendoswitchgames.model.Game;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public interface GameListDataSource {

    interface OnGameListLoadedCallBack {
        void onGameListLoaded(List<Game> gameList);
        void onError(String message, Throwable t);
    }

    void getGameList(OnGameListLoadedCallBack callBack);
}
