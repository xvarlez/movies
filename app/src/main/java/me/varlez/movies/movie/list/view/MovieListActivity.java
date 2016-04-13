package me.varlez.movies.movie.list.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.varlez.movies.MoviesApp;
import me.varlez.movies.R;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.model.Movie;
import me.varlez.movies.movie.detail.view.MovieDetailActivity;
import me.varlez.movies.movie.detail.view.MovieDetailFragment;
import me.varlez.movies.movie.list.presenter.DefaultMovieListPresenter;
import me.varlez.movies.movie.list.presenter.MovieListPresenter;
import me.varlez.movies.movie.list.view.adapter.MovieRecyclerViewAdapter;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends MvpLceActivity<SwipeRefreshLayout, List<Movie>, MovieListView, MovieListPresenter> implements MovieListView, MovieRecyclerViewAdapter.MovieClickListener, SwipeRefreshLayout.OnRefreshListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /**
     * The dagger component that will be used to inject our dependencies.
     */
    private MoviesComponent moviesComponent;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.movie_list)
    RecyclerView recyclerView;

    /**
     * The movie title that should be searched.
     */
    private String searchQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ButterKnife.bind(this);

        contentView.setOnRefreshListener(this);
        contentView.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        searchQuery = "Batman";

        loadData(false);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handles the intent received by the activity and performs a search if needed.
     * @param intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY);
            loadData(false);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_movie_list, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void movieClicked(String movieId) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(MovieDetailFragment.ARG_MOVIE_ID, movieId);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {
           presenter.openMovieDetails(this, movieId);
        }
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, false);
        contentView.setRefreshing(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "Oops ! Something bad happened :\n\n" + e.getMessage() + "\n\nTouch to retry";
    }

    @Override
    public void setData(List<Movie> data) {
        recyclerView.setAdapter(new MovieRecyclerViewAdapter(data, MovieListActivity.this, moviesComponent));
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.movieList(searchQuery, pullToRefresh);
    }

    @NonNull
    @Override
    public MovieListPresenter createPresenter() {
        moviesComponent = ((MoviesApp) getApplication()).getMoviesComponent();
        return new DefaultMovieListPresenter(moviesComponent);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
}
