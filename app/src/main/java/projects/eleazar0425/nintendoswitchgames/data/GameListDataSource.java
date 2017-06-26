package projects.eleazar0425.nintendoswitchgames.data;

import java.util.List;
import projects.eleazar0425.nintendoswitchgames.model.Game;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public interface GameListDataSource {

    interface OnGameListLoadedCallBack {
        /**
         * Callback called when the data is loaded
         * @param gameList
         */
        void onGameListLoaded(List<Game> gameList);

        /**
         * Callback called in case of error
         * @param message error message
         * @param t exception thrown
         */
        void onError(String message, Throwable t);
    }

    void getGameList(OnGameListLoadedCallBack callBack);
}
