package me.varlez.movies.common.di.component;

import javax.inject.Singleton;

import dagger.Component;
import me.varlez.movies.common.di.module.MoviesModule;
import me.varlez.movies.movie.detail.presenter.DefaultMovieDetailPresenter;
import me.varlez.movies.movie.detail.view.MovieDetailFragment;
import me.varlez.movies.movie.list.presenter.DefaultMovieListPresenter;
import me.varlez.movies.movie.list.view.adapter.MovieRecyclerViewAdapter;

/**
 * Component exposing the custom Application object.
 */
@Singleton
@Component(modules=MoviesModule.class)
public interface MoviesComponent {
    void inject(MovieRecyclerViewAdapter movieRecyclerViewAdapter);

    void inject(DefaultMovieListPresenter defaultMovieListPresenter);

    void inject(DefaultMovieDetailPresenter defaultMovieDetailPresenter);

    void inject(MovieDetailFragment movieDetailFragment);
}
