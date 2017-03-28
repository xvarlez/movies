package me.varlez.movies.common.model

import com.google.gson.annotations.SerializedName

import java.util.Comparator

/**
 * Model for a movie item.
 */
data class Movie (@SerializedName("imdbID") val id: String,
                  @SerializedName("Title") var title: String,
                  @SerializedName("Year") var year: Int,
                  @SerializedName("Poster") var posterUrl: String,
                  @SerializedName("Director") var director: String,
                  @SerializedName("Plot") var plot: String) {
    /**
     * Comparator that compares two movies by their respective year (descending order).
     */
    class YearComparator : Comparator<Movie> {
        override fun compare(lhs: Movie, rhs: Movie): Int {
            return rhs.year.compareTo(lhs.year)
        }
    }
}
