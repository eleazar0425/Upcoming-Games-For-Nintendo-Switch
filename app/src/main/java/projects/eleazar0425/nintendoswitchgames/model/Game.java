package projects.eleazar0425.nintendoswitchgames.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Eleazar Estrella on 6/6/17.
 */

public class Game extends RealmObject{

    @SerializedName("eshop_price")
    private String price;
    @SerializedName("number_of_players")
    private String numberOfPlayers;
    private String title;
    @SerializedName("front_box_art")
    private String boxArt;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("buyitnow")
    private boolean physicalRelease = true;

    public Game() {
        price = "-1";
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBoxArt() {
        return boxArt;
    }

    public void setBoxArt(String boxArt) {
        this.boxArt = boxArt;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isPhysicalRelease() {
        return physicalRelease;
    }

    public void setPhysicalRelease(boolean physicalRelease) {
        this.physicalRelease = physicalRelease;
    }
}
