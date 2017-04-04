package me.varlez.movies.common.interactor

import io.reactivex.Observable
import me.varlez.movies.common.model.Movie
import me.varlez.movies.common.model.SearchResults

interface MovieInteractor {
    fun list(query: String, type :String): Observable<List<Movie>>
    fun movie(id: String): Observable<Movie>
}