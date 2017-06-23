package projects.eleazar0425.nintendoswitchgames.view.gamelisting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import projects.eleazar0425.nintendoswitchgames.Application;
import projects.eleazar0425.nintendoswitchgames.R;
import projects.eleazar0425.nintendoswitchgames.di.component.DaggerGameListComponent;
import projects.eleazar0425.nintendoswitchgames.di.module.GameListModule;
import projects.eleazar0425.nintendoswitchgames.model.Game;
import projects.eleazar0425.nintendoswitchgames.util.DateUtil;
import projects.eleazar0425.nintendoswitchgames.util.FilterUtil;
import projects.eleazar0425.nintendoswitchgames.util.OrderingUtil;
import projects.eleazar0425.nintendoswitchgames.util.Searchable;

/**
 * Created by Eleazar Estrella on 6/6/17.
 */

public class GameListFragment extends Fragment implements GameListingContract.View, Searchable {

    private RecyclerView recyclerView;
    @Inject GameListingContract.Presenter presenter;
    private List<Game> gameList;
    private GameListAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView errorBackground;
    public enum Filter {
        ALL_GAMES,
        RELEASED_ONLY,
        NOT_RELEASED,
        PHYSICAL_RELEASE,
        DIGITAL_RELEASE
    }

    public static GameListFragment newInstance() {
        Bundle args = new Bundle();
        GameListFragment fragment = new GameListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerGameListComponent
                .builder()
                .appComponent(((Application)getActivity().getApplication()).getAppComponent())
                .gameListModule(new GameListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_game_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getGameList();
            }
        });
        errorBackground = (ImageView) view.findViewById(R.id.error_background);
        gameList = new ArrayList<>();
        adapter = new GameListAdapter(gameList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        presenter.start();
        presenter.getGameList();
        return view;
    }

    @Override
    public void setPresenter(GameListingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onError(String message, Throwable t) {
        //show alert
        errorBackground.setVisibility(View.VISIBLE);
        if(getContext()!=null){
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setGameList(List<Game> gameList) {
        adapter.clear();
        adapter.addAll(gameList);
        adapter.notifyDataSetChanged();
        OrderingUtil.sort(this.gameList, OrderingUtil.OrderBy.DATE, "releaseDate");
        int pos = FilterUtil.findFirstElementPosition(this.gameList, new FilterUtil.Predicate<Game>() {
            @Override
            public boolean satisfyCondition(Game game) {
                Calendar today = Calendar.getInstance();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.parse(game.getReleaseDate()));
                if(today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                        && today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
                    return true;
                }else{
                    return false;
                }
            }
        });
        if(pos != -1){
            recyclerView.scrollToPosition(pos);
        }
    }

    @Override
    public void showProgressBar() {
        errorBackground.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void orderBy(OrderingUtil.OrderBy orderBy){
        switch (orderBy){
            case DATE:
                OrderingUtil.sort(gameList, OrderingUtil.OrderBy.DATE, "releaseDate");
                break;
            case TITLE:
                OrderingUtil.sort(gameList, OrderingUtil.OrderBy.TITLE, "title");
                break;
            case NUMBER:
                OrderingUtil.sort(gameList, OrderingUtil.OrderBy.NUMBER, "price");
                break;
            case NUMBER_DESCENDING:
                OrderingUtil.sort(gameList, OrderingUtil.OrderBy.NUMBER_DESCENDING, "price");
        }
        adapter.notifyDataSetChanged();
    }

    public void filter(Filter filter){
        switch (filter){
            case ALL_GAMES:
                adapter.resetFilter();
                break;
            case RELEASED_ONLY:
                adapter.filter(new GameListAdapter.FilterPredicate() {
                    @Override
                    public boolean filter(Game game) {
                        int days = DateUtil.getDaysBetweenDates(new Date(),
                                DateUtil.parse(game.getReleaseDate()));
                        if(days <= 0) {
                            return true;
                        }else{
                            return false;
                        }
                    }
                });
                break;
            case NOT_RELEASED:
                adapter.filter(new GameListAdapter.FilterPredicate() {
                    @Override
                    public boolean filter(Game game) {
                        int days = DateUtil.getDaysBetweenDates(new Date(),
                                DateUtil.parse(game.getReleaseDate()));
                        if(days > 0) {
                            return true;
                        }else{
                            return false;
                        }
                    }
                });
                break;

            case PHYSICAL_RELEASE:
                adapter.filter(new GameListAdapter.FilterPredicate() {
                    @Override
                    public boolean filter(Game game) {
                        return game.isPhysicalRelease();
                    }
                });
                break;
            case DIGITAL_RELEASE:
                adapter.filter(new GameListAdapter.FilterPredicate() {
                    @Override
                    public boolean filter(Game game) {
                        return !game.isPhysicalRelease();
                    }
                });
                break;
        }
    }

    @Override
    public void search(String query) {
        adapter.search(query);
    }
}
