package me.varlez.movies.movie.detail.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import me.varlez.movies.common.Logger;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.model.Movie;
import me.varlez.movies.common.rest.MovieService;
import me.varlez.movies.movie.detail.view.MovieDetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Default implementation of the movie details presenter, in charge of
 * fetching the data from the REST API and passing it to the view.
 */
public class DefaultMovieDetailPresenter extends MvpBasePresenter<MovieDetailView> implements MovieDetailPresenter {

    /**
     * The IMDB URL that will be opened in the web browser.
     */
    private static final String IMDB_URL = "http://www.imdb.com/title/%s";

    /**
     * The current model we are interacting with.
     */
    private Movie currentMovie;

    @Inject
    MovieService movieService;

    public DefaultMovieDetailPresenter(MoviesComponent moviesComponent) {
        moviesComponent.inject(this);
    }

    @Override
    public void details(String movieId, final boolean pullToRefresh) {
        // Tell the view to display a loading
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        Call<Movie> call = movieService.movie(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (isViewAttached()) {
                    currentMovie = response.body();
                    getView().setData(currentMovie);
                    getView().showContent();
                }

                Logger.d("OK ! Got the movie details");
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                currentMovie = null;
                getView().showError(t, pullToRefresh);
                Logger.e("Errorrrr ! Couldn't get the movie details :(");
            }
        });
    }

    @Override
    public void openImdb(Context context) {
        Intent openImdbIntent = new Intent(Intent.ACTION_VIEW, getImdbUrl());
        context.startActivity(openImdbIntent);
    }

    @Override
    public void attachView(MovieDetailView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    /**
     * Returns an URL to full movie details on IMDB.
     * @return
     */
    private Uri getImdbUrl() {
        return Uri.parse(String.format(IMDB_URL, currentMovie.getId()));
    }
}
