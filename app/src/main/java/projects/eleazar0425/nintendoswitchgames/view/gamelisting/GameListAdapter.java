package projects.eleazar0425.nintendoswitchgames.view.gamelisting;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import projects.eleazar0425.nintendoswitchgames.Application;
import projects.eleazar0425.nintendoswitchgames.R;
import projects.eleazar0425.nintendoswitchgames.di.component.DaggerToggleFavoriteComponent;
import projects.eleazar0425.nintendoswitchgames.di.module.ToggleFavoriteModule;
import projects.eleazar0425.nintendoswitchgames.model.Game;
import projects.eleazar0425.nintendoswitchgames.util.DateUtil;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.togglefavorite.ToggleFavoriteContract;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.togglefavorite.ToggleFavoritePresenter;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private List<Game> gameList, gameListCopy; //copy to maintain original list in case of filtering
    private Activity context;

    public GameListAdapter(List<Game> gameList, Activity context) {
        this.gameList = gameList;
        this.gameListCopy = new ArrayList<>();
        this.gameListCopy.addAll(gameList);
        this.context = context;
    }

    @Override
    public GameListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_game_item, parent, false);
        return new GameListViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(final GameListViewHolder holder, final int position) {
        holder.title.setText(gameList.get(position).getTitle());
        holder.no_OfPlayers.setText(
                context.getString(R.string.no_of_player) + " " + gameList.get(position).getNumberOfPlayers()
        );


        String daysToRelease = "";
        String comingOn = "";
        int days = DateUtil.getDaysBetweenDates(new Date(),
                                        DateUtil.parse(gameList.get(position).getReleaseDate()));
        if(days <= 0){
            daysToRelease = context.getString(R.string.already_released);
            comingOn = context.getString(R.string.released_on) + " " + gameList.get(position).getReleaseDate();
        }else{
            daysToRelease = String.valueOf(days)+" "+context.getString(R.string.days_to_release);
            comingOn = context.getString(R.string.coming_on) + " " + gameList.get(position).getReleaseDate();
        }

        holder.daysToRelease.setText(daysToRelease);
        holder.comingDate.setText(comingOn);

        String isPhysicalRelease =  gameList.get(position).isPhysicalRelease()
                ? context.getString(R.string.yes) : context.getString(R.string.no);

        holder.physicalRelease.setText(
                context.getString(R.string.physical_release)
                        + " " + isPhysicalRelease
        );

        String price = gameList.get(position).getPrice().equals("-1")
                ? "TBA" : "$"+gameList.get(position).getPrice();
        holder.price.setText(price);

        Glide.with(holder.boxArt.getContext())
                .load(gameList.get(position).getBoxArt())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.boxArt);

        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.updateFavorite(gameList.get(position), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void addAll(List<Game> games){
        this.gameList.addAll(games);
        this.gameListCopy.addAll(games);
    }

    public void clear(){
        this.gameListCopy.clear();
        this.gameList.clear();
    }

    public void filter(FilterPredicate filerPredicate){
        gameList.clear();
        for(Game game : gameListCopy){
            if(filerPredicate.filter(game)){
                gameList.add(game);
            }
        }
        notifyDataSetChanged();
    }

    public void resetFilter(){
        gameList.clear();
        gameList.addAll(gameListCopy);
        notifyDataSetChanged();
    }

    public void search(String query){
        gameList.clear();
        if(query.isEmpty()){
            gameList.addAll(gameListCopy);
        }else{
            query = query.toLowerCase();
            for(Game game: gameListCopy){
                if(game.getTitle().toLowerCase().equals(query) ||
                        game.getTitle().toLowerCase().contains(query)){
                    gameList.add(game);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class GameListViewHolder extends RecyclerView.ViewHolder
            implements ToggleFavoriteContract.View {
        TextView title, comingDate, no_OfPlayers, daysToRelease, physicalRelease, price;
        ImageView boxArt;
        ToggleButton toggleButton;
        @Inject ToggleFavoritePresenter presenter;
        private boolean previousResult = false;
        private Activity activity;

        public GameListViewHolder(View itemView, Activity activity) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.text_game_title);
            comingDate = (TextView) itemView.findViewById(R.id.text_coming_date);
            no_OfPlayers = (TextView) itemView.findViewById(R.id.text_no_of_players);
            daysToRelease = (TextView) itemView.findViewById(R.id.text_days_to_release);
            physicalRelease = (TextView) itemView.findViewById(R.id.text_physical_release);
            price = (TextView) itemView.findViewById(R.id.text_price);
            boxArt = (ImageView) itemView.findViewById(R.id.image_cover_art);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.toggleButton);
            this.activity = activity;
            setupDIGraph();
        }

        @Override
        public void setPresenter(ToggleFavoriteContract.Presenter presenter) {

        }

        @Override
        public void onError(String message, Throwable t) {
            toggleButton.setChecked(previousResult);
            Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFavoriteUpdated(Game game, boolean result) {
            toggleButton.setChecked(result);
            Toast.makeText(itemView.getContext(), "ANADIDO", Toast.LENGTH_SHORT).show();
        }

        public void updateFavorite(Game game, boolean favorite){
            previousResult = favorite;
            presenter.setFavorite(game, favorite);
        }

        private void setupDIGraph(){
            DaggerToggleFavoriteComponent
                    .builder()
                    .appComponent(((Application)activity.getApplication()).getAppComponent())
                    .toggleFavoriteModule(new ToggleFavoriteModule(this))
                    .build()
                    .inject(this);
        }
    }

    public interface FilterPredicate {
        boolean filter(Game game);
    }
}
