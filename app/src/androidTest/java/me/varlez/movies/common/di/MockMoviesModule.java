package me.varlez.movies.common.di;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.varlez.movies.common.rest.MockMovieService;
import me.varlez.movies.common.rest.MovieService;

/**
 * Dagger module for testing purposes that will be injecting our different
 * mocked dependencies.
 */
@Module
public class MockMoviesModule {

    private Context context;

    public MockMoviesModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        return Picasso.with(context);
    }

    @Provides
    @Singleton
    MovieService provideMovieService() {
        return new MockMovieService();
    }
}
