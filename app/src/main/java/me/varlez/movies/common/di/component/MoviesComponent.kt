package me.varlez.movies.common.di.component

import dagger.Component
import me.varlez.movies.common.di.module.MoviesModule
import me.varlez.movies.movie.detail.presenter.DefaultMovieDetailPresenter
import me.varlez.movies.movie.detail.view.MovieDetailFragment
import me.varlez.movies.movie.list.presenter.DefaultMovieListPresenter
import me.varlez.movies.movie.list.view.adapter.MovieRecyclerViewAdapter
import javax.inject.Singleton

/**
 * Component exposing the custom Application object.
 */
@Singleton
@Component(modules = arrayOf(MoviesModule::class))
interface MoviesComponent {
    fun inject(movieRecyclerViewAdapter: MovieRecyclerViewAdapter)

    fun inject(defaultMovieListPresenter: DefaultMovieListPresenter)

    fun inject(defaultMovieDetailPresenter: DefaultMovieDetailPresenter)

    fun inject(movieDetailFragment: MovieDetailFragment)
}
