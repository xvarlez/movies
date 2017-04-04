package me.varlez.movies.common.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import me.varlez.movies.common.di.ChildScope
import me.varlez.movies.common.interactor.DefaultMovieInteractor
import me.varlez.movies.common.interactor.MovieInteractor

@Module
class MovieModule(private val context: Context) {

    @Provides
    @ChildScope
    internal fun provideMovieInteractor(): MovieInteractor = DefaultMovieInteractor(context)
}