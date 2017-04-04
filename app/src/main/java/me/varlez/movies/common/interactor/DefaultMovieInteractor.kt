package me.varlez.movies.common.interactor

import android.content.Context
import io.reactivex.Observable
import me.varlez.movies.MoviesApp
import me.varlez.movies.common.model.Movie
import me.varlez.movies.common.rest.MovieService
import javax.inject.Inject

class DefaultMovieInteractor(var context: Context) : MovieInteractor {

    @Inject
    internal lateinit var movieService: MovieService

    init {
        MoviesApp.get(context).appComponent.inject(this)
    }

    override fun list(query: String, type: String): Observable<List<Movie>> {
        // TODO cache or network management
        return movieService.list(query, type)
                .flatMap { searchResult -> Observable.fromArray(searchResult.movies) }
    }

    override fun movie(id: String): Observable<Movie> {
        // TODO cache or network management
        return movieService.movie(id)
    }
}