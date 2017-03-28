package me.varlez.movies.movie.list.presenter

import android.content.Context

import com.hannesdorfmann.mosby3.mvp.MvpPresenter

import me.varlez.movies.movie.list.view.MovieListView

/**
 * The presenter interface that the Movies list view will be referring to.
 */
interface MovieListPresenter : MvpPresenter<MovieListView> {
    fun movieList(search: String?, pullToRefresh: Boolean)
    fun openMovieDetails(context: Context, movieId: String)
}
