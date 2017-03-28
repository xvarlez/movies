package me.varlez.movies

import me.varlez.movies.common.di.MockMoviesModule

/**
 * Mock of our custom Application class that will be creating our
 * different mock dependencies.
 */
class MockMoviesApp : MoviesApp() {

    override fun initAppComponent() {
        this.moviesComponent = DaggerTestComponent
                .builder()
                .mockMoviesModule(MockMoviesModule(this))
                .build()
    }

}
