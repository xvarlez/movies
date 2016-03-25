package me.varlez.movies;

import android.app.Application;

import me.varlez.movies.common.di.component.DaggerMoviesComponent;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.di.module.MoviesModule;

/**
 * Custom application class handling Dagger graph creation.
 */
public class MoviesApp extends Application {

    protected MoviesComponent moviesComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    protected void initAppComponent() {
        this.moviesComponent = DaggerMoviesComponent
                .builder()
                .moviesModule(new MoviesModule(this))
                .build();
    }

    public MoviesComponent getMoviesComponent() {
        return this.moviesComponent;
    }

}
