package me.varlez.movies.common.rest

import io.reactivex.Observable
import me.varlez.movies.common.model.Movie
import me.varlez.movies.common.model.SearchResults
import retrofit2.http.Query

/**
 * Mock of our movie service REST API.
 */
class MockMovieService : MovieService {

    private val movies: MutableList<Movie> = createMovies()

    override fun movie(@Query("i") movieId: String): Observable<Movie> {
        return Observable.just(movies[0])
    }

    override fun list(@Query("s") query: String?, @Query("type") type: String): Observable<SearchResults> {
        return Observable.just(SearchResults(movies))
    }

    private fun createMovies(): ArrayList<Movie> {
        val firstMovie = Movie(id = "1",
                title = "Batman",
                year = 2005,
                posterUrl = "",
                plot = "The Batman !",
                director = "Christopher Nolan")

        val secondMovie = Movie(id = "2",
                title = "Space Jam",
                year = 1996,
                posterUrl = "",
                plot = "The best movie !",
                director = "Joe Pytka")

        return arrayListOf(firstMovie, secondMovie)
    }
}
