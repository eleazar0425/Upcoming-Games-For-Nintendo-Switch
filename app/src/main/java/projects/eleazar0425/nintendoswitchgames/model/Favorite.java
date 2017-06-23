package projects.eleazar0425.nintendoswitchgames.model;

import io.realm.RealmObject;

/**
 * Created by Eleazar Estrella on 6/23/17.
 */

public class Favorite extends RealmObject {
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
