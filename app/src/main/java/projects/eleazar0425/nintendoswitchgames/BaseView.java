package projects.eleazar0425.nintendoswitchgames;

/**
 * Created by Eleazar Estrella on 6/6/17.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
    void onError(String message, Throwable t);
}
