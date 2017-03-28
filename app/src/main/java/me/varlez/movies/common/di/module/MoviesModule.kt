package me.varlez.movies.common.di.module

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import me.varlez.movies.common.Consts
import me.varlez.movies.common.rest.MovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module providing our required dependencies.
 */
@Module
class MoviesModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun providePicasso(): Picasso {
        val builder = Picasso.Builder(context)

        val picasso = builder.build()
        //        picasso.setIndicatorsEnabled(true);

        return picasso
    }

    @Provides
    @Singleton
    internal fun provideMovieService(): MovieService {
        val retrofit = Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create<MovieService>(MovieService::class.java!!)
    }
}
