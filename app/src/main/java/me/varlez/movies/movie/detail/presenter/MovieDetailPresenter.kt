package me.varlez.movies.movie.detail.presenter

import android.content.Context

import com.hannesdorfmann.mosby3.mvp.MvpPresenter

import me.varlez.movies.movie.detail.view.MovieDetailView

/**
 * Interface for the movie details presenter, that will be used in the view.
 */
interface MovieDetailPresenter : MvpPresenter<MovieDetailView> {
    fun details(movieId: String, pullToRefresh: Boolean)
    fun openImdb(context: Context)
}
