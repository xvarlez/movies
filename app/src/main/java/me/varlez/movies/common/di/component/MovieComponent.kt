package me.varlez.movies.common.di.component

import dagger.Component
import me.varlez.movies.common.di.ChildScope
import me.varlez.movies.common.di.module.MovieModule
import me.varlez.movies.movie.detail.presenter.DefaultMovieDetailPresenter
import me.varlez.movies.movie.list.presenter.DefaultMovieListPresenter

/**
 * Component that will be used for all of our movie use cases.
 */
@ChildScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MovieModule::class))
interface MovieComponent {
    fun inject(defaultMovieDetailPresenter: DefaultMovieDetailPresenter)
    fun inject(defaultMovieDetailPresenter: DefaultMovieListPresenter)
}
