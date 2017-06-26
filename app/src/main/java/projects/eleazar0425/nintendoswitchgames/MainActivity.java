package projects.eleazar0425.nintendoswitchgames;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import projects.eleazar0425.nintendoswitchgames.donate.DonateActivity;
import projects.eleazar0425.nintendoswitchgames.view.gamelisting.GameListFragment;
import projects.eleazar0425.nintendoswitchgames.util.OrderingUtil;


public class MainActivity extends AppCompatActivity {

    private BottomSheetMenuDialog mBottomSheetDialog;
    private com.github.clans.fab.FloatingActionButton filterButton, sortByButton;
    private CoordinatorLayout coordinatorLayout;
    private GameListFragment fragment;
    private MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.main_label);

        materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);

        materialSearchView.setVoiceSearch(false);
        materialSearchView.setEllipsize(true);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                checkFragmentIsNotNull();
                fragment.search(newText);
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                checkFragmentIsNotNull();
                fragment.search("");
            }
        });


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        filterButton = (FloatingActionButton) findViewById(R.id.menu_item_filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupBottomSheetForFiltering();
                mBottomSheetDialog.show();
            }
        });

        sortByButton = (FloatingActionButton) findViewById(R.id.menu_item_sort);
        sortByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupBottomSheetForSorting();
                mBottomSheetDialog.show();
            }
        });

        // To avoid fragment to be committed twice
        if(savedInstanceState == null){
            fragment = GameListFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                showAboutDialog();
                break;
            case R.id.action_donate:
                Intent i = new Intent(MainActivity.this, DonateActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkFragmentIsNotNull(){
        if(fragment == null)
            fragment = (GameListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    private void setupBottomSheetForSorting(){
        mBottomSheetDialog = new BottomSheetBuilder(this, coordinatorLayout)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setBackgroundColorResource(android.R.color.white)
                .setMenu(R.menu.menu_order_by_list)
                .addTitleItem(R.string.order_by)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        OrderingUtil.OrderBy orderBy;
                        switch(item.getItemId()){
                            case R.id.by_date:
                                orderBy = OrderingUtil.OrderBy.DATE;
                                break;
                            case R.id.by_title:
                                orderBy = OrderingUtil.OrderBy.TITLE;
                                break;
                            case R.id.by_lowest_price:
                                orderBy = OrderingUtil.OrderBy.NUMBER;
                                break;
                            case R.id.by_highest_price:
                                orderBy = OrderingUtil.OrderBy.NUMBER_DESCENDING;
                                break;
                            default:
                                orderBy = OrderingUtil.OrderBy.DATE;
                        }
                        checkFragmentIsNotNull();
                        fragment.orderBy(orderBy);
                    }
                }).expandOnStart(true)
                .createDialog();
    }


    private void setupBottomSheetForFiltering(){
        mBottomSheetDialog = new BottomSheetBuilder(this, coordinatorLayout)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setBackgroundColorResource(android.R.color.white)
                .setMenu(R.menu.menu_filter_by_list)
                .addTitleItem(R.string.order_by)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        GameListFragment.Filter filter;
                        switch(item.getItemId()){
                            case R.id.released_only:
                                filter = GameListFragment.Filter.RELEASED_ONLY;
                                break;
                            case R.id.not_released:
                                filter = GameListFragment.Filter.NOT_RELEASED;
                                break;
                            case R.id.physical_release:
                                filter = GameListFragment.Filter.PHYSICAL_RELEASE;
                                break;
                            case R.id.digital_release:
                                filter = GameListFragment.Filter.DIGITAL_RELEASE;
                                break;
                            case R.id.all_games:
                                filter = GameListFragment.Filter.ALL_GAMES;
                                break;
                            default:
                                filter = GameListFragment.Filter.ALL_GAMES;
                        }
                        if(fragment == null)
                            fragment = (GameListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                        fragment.filter(filter);
                    }
                }).expandOnStart(true)
                .createDialog();
    }



    private void showAboutDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.DialogStyle);
        alertDialogBuilder.setMessage(getString(R.string.about_text_information));
        alertDialogBuilder.setTitle(getString(R.string.about));
        alertDialogBuilder.setPositiveButton(getString(R.string.accept),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int arg1) {
                        //do what you want to do if user clicks ok
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(materialSearchView.isSearchOpen()){
            materialSearchView.closeSearch();
        }else{
            super.onBackPressed();
        }
    }
}
