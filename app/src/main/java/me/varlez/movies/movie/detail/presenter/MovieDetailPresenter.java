package me.varlez.movies.movie.detail.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import me.varlez.movies.movie.detail.view.MovieDetailView;

/**
 * Interface for the movie details presenter, that will be used in the view.
 */
public interface MovieDetailPresenter extends MvpPresenter<MovieDetailView> {
    void details(String movieId, boolean pullToRefresh);
    void openImdb(Context context);
}
