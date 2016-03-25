package me.varlez.movies.movie.list.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.varlez.movies.MoviesApp;
import me.varlez.movies.R;
import me.varlez.movies.common.Consts;
import me.varlez.movies.common.Logger;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.model.SearchResults;
import me.varlez.movies.common.rest.MovieService;
import me.varlez.movies.movie.detail.MovieDetailActivity;
import me.varlez.movies.movie.detail.MovieDetailFragment;
import me.varlez.movies.movie.list.view.adapter.MovieRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.MovieClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /**
     * The dagger component that will be used to inject our dependencies.
     */
    private MoviesComponent moviesComponent;

    @Inject
    MovieService movieService;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.movie_list)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        moviesComponent = ((MoviesApp) getApplication()).getMoviesComponent();
        moviesComponent.inject(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        requestApiData();
    }

    /**
     * Request the data from our REST API and handle the response.
     */
    private void requestApiData() {
        Call<SearchResults> call = movieService.list("Batman", "movie");

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                recyclerView.setAdapter(new MovieRecyclerViewAdapter(response.body().getMovies(), MovieListActivity.this, moviesComponent));
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Logger.e("Errroooooorrrrrrrr !");
                Logger.e(t.getMessage());
            }
        });
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
            Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailFragment.ARG_MOVIE_ID, movieId);

            startActivity(intent);
        }
    }
}
