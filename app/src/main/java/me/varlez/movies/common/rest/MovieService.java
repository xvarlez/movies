package me.varlez.movies.common.rest;

import me.varlez.movies.common.model.Movie;
import me.varlez.movies.common.model.SearchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Endpoint interface for the different calls to our REST API.
 */
public interface MovieService {
    @GET("/")
    Call<Movie> movie(@Query("i") String movieId);

    @GET("/")
    Call<SearchResults> list(@Query("s") String query, @Query("type") String type);
}
