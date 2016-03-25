package me.varlez.movies.common.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO holding the search results.
 */
public class SearchResults {

    @SerializedName("Search")
    List<Movie> movies;

    public SearchResults(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}
