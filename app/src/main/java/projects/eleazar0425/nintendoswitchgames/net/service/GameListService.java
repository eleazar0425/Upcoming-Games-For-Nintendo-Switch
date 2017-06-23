package projects.eleazar0425.nintendoswitchgames.net.service;

import java.util.List;

import projects.eleazar0425.nintendoswitchgames.model.Game;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public interface GameListService {
    @GET("/gamesAmerica")
    Call<List<Game>> getGameList(@Query("offset") int offset);
}
