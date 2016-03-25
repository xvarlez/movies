package me.varlez.movies.movie.detail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.varlez.movies.MoviesApp;
import me.varlez.movies.R;
import me.varlez.movies.common.Consts;
import me.varlez.movies.common.Logger;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.model.Movie;
import me.varlez.movies.common.rest.MovieService;
import me.varlez.movies.movie.list.view.MovieListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_MOVIE_ID = "movieId";

    /**
     * The IMDB URL that will be opened in the web browser.
     */
    private static final String IMDB_URL = "http://www.imdb.com/title/%s";

    /**
     * The movie represented in our fragment.
     */
    private Movie currentMovie;

    @Bind(R.id.movie_director)
    TextView directorTextView;

    @Bind(R.id.movie_plot)
    TextView plotTextView;

    @Inject
    MovieService movieService;

    @Inject
    Picasso picassoService;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoviesComponent moviesComponent = ((MoviesApp) getActivity().getApplication()).getMoviesComponent();
        moviesComponent.inject(this);

        if (getArguments().containsKey(ARG_MOVIE_ID)) {
            requestApiData();
        }
    }

    /**
     * Request the data from our REST API and handle the response.
     */
    private void requestApiData() {
        Call<Movie> call = movieService.movie(getArguments().getString(ARG_MOVIE_ID));
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                currentMovie = response.body();
                if (plotTextView != null) {
                    // The view is already loaded, display content
                    setMovieContent();
                }
                Logger.d("OK ! Got the movie details");
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Logger.e("Errorrrr ! Couldn't get the movie details :(");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail, container, false);
        ButterKnife.bind(this, view);

        if(currentMovie != null) {
            // The data was already fetched, display data
            setMovieContent();
        }

        return view;
    }

    private Uri getImdbUrl() {
        return Uri.parse(String.format(IMDB_URL, currentMovie.getId()));
    }

    private void setMovieContent() {
        Activity activity = this.getActivity();

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        ImageView headerImage = (ImageView) activity.findViewById(R.id.imageview_header);
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);

        if (appBarLayout != null) {
            picassoService.load(currentMovie.getPosterUrl())
                    .fit()
                    .centerCrop()
                    .into(headerImage);
            appBarLayout.setTitle(currentMovie.getTitle());
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openImdbIntent = new Intent(Intent.ACTION_VIEW, getImdbUrl());
                startActivity(openImdbIntent);
            }
        });

        plotTextView.setText(currentMovie.getPlot());
        directorTextView.setText(currentMovie.getDirector());
    }

}
