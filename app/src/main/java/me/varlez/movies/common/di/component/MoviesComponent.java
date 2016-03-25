package me.varlez.movies.common.di.component;

import javax.inject.Singleton;

import dagger.Component;
import me.varlez.movies.common.di.module.MoviesModule;
import me.varlez.movies.movie.detail.MovieDetailFragment;
import me.varlez.movies.movie.list.view.MovieListActivity;
import me.varlez.movies.movie.list.view.adapter.MovieRecyclerViewAdapter;

/**
 * Component exposing the custom Application object.
 */
@Singleton
@Component(modules=MoviesModule.class)
public interface MoviesComponent {
    void inject(MovieListActivity movieListActivity);
    void inject(MovieRecyclerViewAdapter movieRecyclerViewAdapter);
    void inject(MovieDetailFragment movieDetailFragment);
}
