package me.varlez.movies

import android.app.Application
import android.content.Context

import me.varlez.movies.common.di.component.DaggerMoviesComponent
import me.varlez.movies.common.di.component.MoviesComponent
import me.varlez.movies.common.di.module.MoviesModule

/**
 * Custom application class handling Dagger graph creation.
 */
open class MoviesApp : Application() {

    lateinit var moviesComponent: MoviesComponent
        protected set

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    protected open fun initAppComponent() {
        this.moviesComponent = DaggerMoviesComponent
                .builder()
                .moviesModule(MoviesModule(this))
                .build()
    }

    companion object {
        fun get(context: Context) = context.applicationContext as MoviesApp
    }

}
