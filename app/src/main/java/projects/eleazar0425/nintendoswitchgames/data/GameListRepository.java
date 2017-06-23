package projects.eleazar0425.nintendoswitchgames.data;

import android.content.Context;
import java.util.List;
import projects.eleazar0425.nintendoswitchgames.data.local.GameListLocalDataSource;
import projects.eleazar0425.nintendoswitchgames.data.remote.GameListRemoteDataSource;
import projects.eleazar0425.nintendoswitchgames.model.Game;
import projects.eleazar0425.nintendoswitchgames.net.NetworkAvailability;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class GameListRepository implements GameListDataSource {

    private GameListRemoteDataSource mRemoteDataSource;
    private GameListLocalDataSource mLocalDataSource;
    private Context mContext;

    public GameListRepository(Context mContext, GameListRemoteDataSource mRemoteDataSource,
                              GameListLocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
        this.mContext = mContext;
    }

    @Override
    public void getGameList(final OnGameListLoadedCallBack callBack) {
        if (NetworkAvailability.isNetworkAvailable(mContext)) {
            getDataFromRemoteDataSource(callBack);
            return;
        }

        mLocalDataSource.getGameList(new OnGameListLoadedCallBack() {
            @Override
            public void onGameListLoaded(List<Game> gameList) {
                callBack.onGameListLoaded(gameList);
            }

            @Override
            public void onError(String message, Throwable t) {
                callBack.onError(message, t);
            }
        });
    }

    private void getDataFromRemoteDataSource(final OnGameListLoadedCallBack callBack){
        mRemoteDataSource.getGameList(new OnGameListLoadedCallBack() {
            @Override
            public void onGameListLoaded(List<Game> gameList) {
                mLocalDataSource.saveGameList(gameList);
                callBack.onGameListLoaded(gameList);
            }

            @Override
            public void onError(String message, Throwable t) {
                mLocalDataSource.getGameList(callBack);
            }
        });
    }
}
