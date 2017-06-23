package projects.eleazar0425.nintendoswitchgames.view.gamelisting.togglefavorite;

import io.realm.Realm;
import projects.eleazar0425.nintendoswitchgames.model.Favorite;
import projects.eleazar0425.nintendoswitchgames.model.Game;

/**
 * Created by Eleazar Estrella on 6/23/17.
 */

public class ToggleFavoritePresenter implements ToggleFavoriteContract.Presenter {


    private Realm realm;
    private ToggleFavoriteContract.View view;

    public ToggleFavoritePresenter(Realm realm, ToggleFavoriteContract.View view) {
        this.realm = realm;
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void setFavorite(Game game, boolean isFavorite) {
        realm.beginTransaction();
        if(isFavorite){
            Favorite favorite = realm.createObject(Favorite.class);
            favorite.setGame(realm.where(Game.class).equalTo("title", game.getTitle()).findFirst());
            realm.copyToRealm(favorite);
        }else{
            Favorite favorite = realm.where(Favorite.class).equalTo("game.title", game.getTitle()).findFirst();
            favorite.deleteFromRealm();
        }
        realm.commitTransaction();
        view.onFavoriteUpdated(game, isFavorite);

        realm.where(Favorite.class).findAll();
    }
}
