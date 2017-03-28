package me.varlez.movies.movie.list.view

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

import me.varlez.movies.common.model.Movie

/**
 * The interface representing our Movie list view.
 */
interface MovieListView : MvpLceView<List<Movie>>
