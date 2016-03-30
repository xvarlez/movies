package me.varlez.movies.movie.list.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import me.varlez.movies.movie.list.view.MovieListView;

/**
 * The presenter interface that the Movies list view will be referring to.
 */
public interface MovieListPresenter extends MvpPresenter<MovieListView> {
    void movieList(String search, boolean pullToRefresh);
    void openMovieDetails(Context context, String movieId);
}
