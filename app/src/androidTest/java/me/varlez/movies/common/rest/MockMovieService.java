package me.varlez.movies.common.rest;

import java.util.ArrayList;
import java.util.List;

import me.varlez.movies.common.model.Movie;
import me.varlez.movies.common.model.SearchResults;
import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.mock.Calls;

/**
 * Mock of our movie service REST API.
 */
public class MockMovieService implements MovieService {

    private List<Movie> movies;

    public MockMovieService() {
        movies = new ArrayList<>();
        createMovies();
    }

    @Override
    public Call<Movie> movie(@Query("i") String movieId) {
        return Calls.response(movies.get(0));
    }

    @Override
    public Call<SearchResults> list(@Query("s") String query, @Query("type") String type) {
        return Calls.response(new SearchResults(movies));
    }

    private void createMovies() {
        Movie firstMovie = new Movie();
        firstMovie.setId("1");
        firstMovie.setTitle("Batman");
        firstMovie.setYear(2005);
        firstMovie.setPlot("The Batman !");
        firstMovie.setDirector("Christopher Nolan");

        Movie secondMovie = new Movie();
        secondMovie.setId("2");
        secondMovie.setTitle("Space Jam");
        secondMovie.setYear(1996);
        secondMovie.setPlot("The best movie !");
        secondMovie.setDirector("Joe Pytka");

        movies.add(firstMovie);
        movies.add(secondMovie);
    }
}
