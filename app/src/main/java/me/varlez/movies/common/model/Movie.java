package me.varlez.movies.common.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * POJO representing a movie item
 */
public class Movie {
    @SerializedName("imdbID")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private Integer year;

    @SerializedName("Poster")
    private String posterUrl;

    @SerializedName("Director")
    private String director;

    @SerializedName("Plot")
    private String plot;

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Comparator that compares two movies by their respective year (descending order).
     */
    public static class YearComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie lhs, Movie rhs) {
            return rhs.getYear().compareTo(lhs.getYear());
        }
    }
}
