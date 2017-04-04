package me.varlez.movies.common.di.module

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import me.varlez.movies.BuildConfig
import me.varlez.movies.common.Consts
import me.varlez.movies.common.rest.MovieService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module providing our required dependencies.
 */
@Module
class NetworkModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun providePicasso(): Picasso {
        val builder = Picasso.Builder(context)

        val picasso = builder.build()
        picasso.setIndicatorsEnabled(BuildConfig.DEBUG)

        return picasso
    }

    @Provides
    @Singleton
    internal fun provideMovieService(): MovieService {
        val retrofit = Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create<MovieService>(MovieService::class.java)
    }
}
