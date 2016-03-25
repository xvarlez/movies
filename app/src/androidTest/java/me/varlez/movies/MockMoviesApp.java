package me.varlez.movies;

import me.varlez.movies.common.di.DaggerTestComponent;
import me.varlez.movies.common.di.MockMoviesModule;

/**
 * Mock of our custom Application class that will be creating our
 * different mock dependencies.
 */
public class MockMoviesApp extends MoviesApp {

    @Override
    protected void initAppComponent() {
        this.moviesComponent = DaggerTestComponent
                .builder()
                .mockMoviesModule(new MockMoviesModule(this))
                .build();
    }

}
