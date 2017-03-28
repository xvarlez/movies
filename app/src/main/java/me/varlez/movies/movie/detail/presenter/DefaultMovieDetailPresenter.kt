package me.varlez.movies.movie.detail.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import me.varlez.movies.common.Logger
import me.varlez.movies.common.di.component.MoviesComponent
import me.varlez.movies.common.model.Movie
import me.varlez.movies.common.rest.MovieService
import me.varlez.movies.movie.detail.view.MovieDetailView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Default implementation of the movie details presenter, in charge of
 * fetching the data from the REST API and passing it to the view.
 */
class DefaultMovieDetailPresenter(moviesComponent: MoviesComponent) : MvpBasePresenter<MovieDetailView>(), MovieDetailPresenter {

    /**
     * The current model we are interacting with.
     */
    private var currentMovie: Movie? = null

    @Inject
    lateinit var movieService: MovieService

    init {
        moviesComponent.inject(this)
    }

    override fun details(movieId: String, pullToRefresh: Boolean) {
        // Tell the view to display a loading
        if (isViewAttached) {
            view.showLoading(pullToRefresh)
        }

        val call = movieService.movie(movieId)
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (isViewAttached) {
                    currentMovie = response.body()
                    view.setData(currentMovie)
                    view.showContent()
                }

                Logger.d("OK ! Got the movie details")
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                currentMovie = null
                view.showError(t, pullToRefresh)
                Logger.e("Errorrrr ! Couldn't get the movie details :(")
            }
        })
    }

    override fun openImdb(context: Context) {
        val openImdbIntent = Intent(Intent.ACTION_VIEW, imdbUrl)
        context.startActivity(openImdbIntent)
    }

    /**
     * Returns an URL to full movie details on IMDB.
     * @return
     */
    private val imdbUrl: Uri
        get() = Uri.parse(String.format(IMDB_URL, currentMovie?.id))

    companion object {
        /**
         * The IMDB URL that will be opened in the web browser.
         */
        private val IMDB_URL = "http://www.imdb.com/title/%s"
    }
}
