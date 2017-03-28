package me.varlez.movies.common.di

import dagger.Component
import me.varlez.movies.common.di.component.MoviesComponent
import me.varlez.movies.movie.detail.MovieDetailsActivityBehaviorTest
import me.varlez.movies.movie.list.MovieListActivityBehaviorTest
import javax.inject.Singleton

/**
 * Dagger component for test purposes, using mock modules.
 */
@Singleton
@Component(modules = arrayOf(MockMoviesModule::class))
interface TestComponent : MoviesComponent {
    fun inject(movieListActivityBehaviorTest: MovieListActivityBehaviorTest)

    fun inject(movieDetailsActivityBehaviorTest: MovieDetailsActivityBehaviorTest)
}
