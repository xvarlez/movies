package me.varlez.movies

import me.varlez.movies.common.di.DaggerTestAppComponent
import me.varlez.movies.common.di.MockNetworkModule

/**
 * Mock of our custom Application class that will be creating our
 * different mock dependencies.
 */
class MockMoviesApp : MoviesApp() {

    override fun initAppComponent() {
        this.appComponent = DaggerTestAppComponent
                .builder()
                .mockNetworkModule(MockNetworkModule(this))
                .build()
    }

}
