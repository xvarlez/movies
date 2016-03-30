package me.varlez.movies.common.di.module;

import android.content.Context;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.varlez.movies.common.Consts;
import me.varlez.movies.common.rest.MovieService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger module providing our required dependencies.
 */
@Module
public class MoviesModule {

    private Context context;

    public MoviesModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        Picasso.Builder builder = new Picasso.Builder(context);

        Picasso picasso = builder.build();
//        picasso.setIndicatorsEnabled(true);

        return picasso;
    }

    @Provides
    @Singleton
    MovieService provideMovieService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MovieService.class);
    }
}
