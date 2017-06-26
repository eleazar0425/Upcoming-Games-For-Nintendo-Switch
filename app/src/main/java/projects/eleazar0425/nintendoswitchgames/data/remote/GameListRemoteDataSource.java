package projects.eleazar0425.nintendoswitchgames.data.remote;

import java.util.List;
import projects.eleazar0425.nintendoswitchgames.data.DataNoAvailableException;
import projects.eleazar0425.nintendoswitchgames.data.GameListDataSource;
import projects.eleazar0425.nintendoswitchgames.model.Game;
import projects.eleazar0425.nintendoswitchgames.net.service.GameListService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class GameListRemoteDataSource implements GameListDataSource {

    private GameListService service;

    /**
     *
     * @param service required service for requesting data
     */
    public GameListRemoteDataSource(GameListService service) {
        this.service = service;
    }

    @Override
    public void getGameList(final OnGameListLoadedCallBack callBack) {

        service.getGameList(0).enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if(response.body() != null && response.isSuccessful()){
                    callBack.onGameListLoaded(response.body());
                }else {
                    //TODO remove hardcoded strings
                    callBack.onError("Data no available", new DataNoAvailableException());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                //TODO this need an error handling to figure out what type of error was
                //TODO remove hardcoded strings
                callBack.onError("There was a network error", t);
            }
        });
    }
}
