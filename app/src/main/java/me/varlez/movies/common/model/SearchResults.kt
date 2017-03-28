package me.varlez.movies.common.model

import com.google.gson.annotations.SerializedName

/**
 * POJO holding the search results.
 */
class SearchResults(movies: List<Movie>) {

    @SerializedName("Search")
    var movies: List<Movie>
        internal set

    init {
        this.movies = movies
    }

}
