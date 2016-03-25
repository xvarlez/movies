package me.varlez.movies.common.di;

import javax.inject.Singleton;

import dagger.Component;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.movie.detail.MovieDetailsActivityBehaviorTest;
import me.varlez.movies.movie.list.MovieListActivityBehaviorTest;

/**
 * Dagger component for test purposes, using mock modules.
 */
@Singleton
@Component(modules=MockMoviesModule.class)
public interface TestComponent extends MoviesComponent {
    void inject(MovieListActivityBehaviorTest movieListActivityBehaviorTest);

    void inject(MovieDetailsActivityBehaviorTest movieDetailsActivityBehaviorTest);
}
