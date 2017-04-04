package me.varlez.movies.common.rest

import io.reactivex.Observable
import me.varlez.movies.common.model.Movie
import me.varlez.movies.common.model.SearchResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Endpoint interface for the different calls to our REST API.
 */
interface MovieService {
    @GET("/")
    fun movie(@Query("i") movieId: String): Observable<Movie>

    @GET("/")
    fun list(@Query("s") query: String?, @Query("type") type: String): Observable<SearchResults>
}
