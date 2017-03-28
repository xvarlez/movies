package me.varlez.movies.movie.list.presenter;

import android.content.Context;
import android.content.Intent;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import me.varlez.movies.common.Logger;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.model.SearchResults;
import me.varlez.movies.common.rest.MovieService;
import me.varlez.movies.movie.detail.view.MovieDetailActivity;
import me.varlez.movies.movie.detail.view.MovieDetailFragment;
import me.varlez.movies.movie.list.view.MovieListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Default implementation of our Movies list presenter, in charge of retrieving
 * the data and passing it to the view.
 */
public class DefaultMovieListPresenter extends MvpBasePresenter<MovieListView> implements MovieListPresenter {

    @Inject
    MovieService movieService;

    public DefaultMovieListPresenter(MoviesComponent moviesComponent) {
        moviesComponent.inject(this);
    }

    @Override
    public void attachView(MovieListView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    @Override
    public void movieList(String search, final boolean pullToRefresh) {
        // Tell the view to display a loading
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        Call<SearchResults> call = movieService.list(search, "movie");

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                if(isViewAttached()) {
                    getView().setData(response.body().getMovies());
                    getView().showContent();
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                if(isViewAttached()) {
                    getView().showError(t, pullToRefresh);
                }

                Logger.e("Errroooooorrrrrrrr !");
                Logger.e(t.getMessage());
            }
        });
    }

    /**
     * Open the movie details in a new activity.
     * @param movieId The ID of the selected movie.
     */
    @Override
    public void openMovieDetails(Context context, String movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailFragment.ARG_MOVIE_ID, movieId);

        context.startActivity(intent);
    }
}
