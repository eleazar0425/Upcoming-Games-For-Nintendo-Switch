package projects.eleazar0425.nintendoswitchgames.data.local;

import java.util.List;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import projects.eleazar0425.nintendoswitchgames.data.DataNoAvailableException;
import projects.eleazar0425.nintendoswitchgames.data.GameListDataSource;
import projects.eleazar0425.nintendoswitchgames.model.Game;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class GameListLocalDataSource implements GameListDataSource {

    private Realm realm;

    /**
     *
     * @param realm
     */
    public GameListLocalDataSource(Realm realm) {
        this.realm = realm;
    }

    /**
     *
     * @param callBack
     */
    @Override
    public void getGameList(OnGameListLoadedCallBack callBack) {
        RealmQuery<Game> query = realm.where(Game.class);
        RealmResults<Game> results = query.findAll();
        List<Game> gameList = realm.copyFromRealm(results);
        if(gameList.size() == 0){
            //TODO remove hardcoded strings
            callBack.onError("There's no data available", new DataNoAvailableException());
        }else {
            callBack.onGameListLoaded(gameList);
        }
    }

    /**
     *
     * @param gameList to save
     */
    public void saveGameList(List<Game> gameList){
        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealm(gameList);
        realm.commitTransaction();
    }
}
