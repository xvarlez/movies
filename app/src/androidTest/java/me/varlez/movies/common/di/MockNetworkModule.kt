package me.varlez.movies.common.di

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import me.varlez.movies.common.rest.MockMovieService
import me.varlez.movies.common.rest.MovieService
import javax.inject.Singleton

/**
 * Dagger module for testing purposes that will be injecting our different
 * mocked dependencies.
 */
@Module
class MockNetworkModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun providePicasso(): Picasso = Picasso.with(context)

    @Provides
    @Singleton
    internal fun provideMovieService(): MovieService = MockMovieService()
}
