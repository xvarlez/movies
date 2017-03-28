package me.varlez.movies.common.rest

import me.varlez.movies.common.model.Movie
import me.varlez.movies.common.model.SearchResults
import retrofit2.Call
import retrofit2.http.Query
import retrofit2.mock.Calls
import java.util.*

/**
 * Mock of our movie service REST API.
 */
class MockMovieService : MovieService {

    private val movies: MutableList<Movie>

    init {
        movies = ArrayList<Movie>()
        createMovies()
    }

    override fun movie(@Query("i") movieId: String): Call<Movie> {
        return Calls.response(movies[0])
    }

    override fun list(@Query("s") query: String?, @Query("type") type: String): Call<SearchResults> {
        return Calls.response(SearchResults(movies))
    }

    private fun createMovies() {
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

        movies.add(firstMovie)
        movies.add(secondMovie)
    }
}
