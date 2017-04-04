package me.varlez.movies.common.di

import dagger.Component
import me.varlez.movies.common.di.component.AppComponent
import me.varlez.movies.movie.detail.MovieDetailsActivityInstrumentationTest
import me.varlez.movies.movie.list.MovieListActivityInstrumentationTest
import javax.inject.Singleton

/**
 * Dagger app component for test purposes, using mock modules.
 */
@Singleton
@Component(modules = arrayOf(MockNetworkModule::class))
interface TestAppComponent : AppComponent {
    fun inject(movieListActivityInstrumentationTest: MovieListActivityInstrumentationTest)

    fun inject(movieDetailsActivityInstrumentationTest: MovieDetailsActivityInstrumentationTest)
}
