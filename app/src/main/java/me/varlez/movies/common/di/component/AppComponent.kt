package me.varlez.movies.common.di.component

import dagger.Component
import me.varlez.movies.common.di.module.NetworkModule
import me.varlez.movies.common.interactor.DefaultMovieInteractor
import me.varlez.movies.movie.detail.view.MovieDetailFragment
import me.varlez.movies.movie.list.view.adapter.MovieRecyclerViewAdapter
import javax.inject.Singleton

/**
 * Component exposing the custom Application object.
 */
@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface AppComponent {
    fun inject(movieRecyclerViewAdapter: MovieRecyclerViewAdapter)

    fun inject(movieDetailFragment: MovieDetailFragment)
    fun inject(defaultMovieInteractor: DefaultMovieInteractor)
}
