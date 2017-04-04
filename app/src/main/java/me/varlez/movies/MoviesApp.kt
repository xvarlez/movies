package me.varlez.movies

import android.app.Application
import android.content.Context
import me.varlez.movies.common.di.component.AppComponent
import me.varlez.movies.common.di.component.DaggerAppComponent
import me.varlez.movies.common.di.module.NetworkModule

/**
 * Custom application class handling Dagger graph creation.
 */
open class MoviesApp : Application() {

    lateinit var appComponent: AppComponent
        protected set

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    protected open fun initAppComponent() {
        this.appComponent = DaggerAppComponent
                .builder()
                .networkModule(NetworkModule(this))
                .build()
    }

    companion object {
        fun get(context: Context) = context.applicationContext as MoviesApp
    }

}
