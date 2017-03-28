package me.varlez.movies.movie.list.presenter

import android.content.Context
import android.content.Intent
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import me.varlez.movies.common.Logger
import me.varlez.movies.common.di.component.MoviesComponent
import me.varlez.movies.common.model.SearchResults
import me.varlez.movies.common.rest.MovieService
import me.varlez.movies.movie.detail.view.MovieDetailActivity
import me.varlez.movies.movie.detail.view.MovieDetailFragment
import me.varlez.movies.movie.list.view.MovieListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Default implementation of our Movies list presenter, in charge of retrieving
 * the data and passing it to the view.
 */
class DefaultMovieListPresenter(moviesComponent: MoviesComponent) : MvpBasePresenter<MovieListView>(), MovieListPresenter {

    @Inject
    internal lateinit var movieService: MovieService

    init {
        moviesComponent.inject(this)
    }

    override fun movieList(search: String?, pullToRefresh: Boolean) {
        // Tell the view to display a loading
        if (isViewAttached) {
            view!!.showLoading(pullToRefresh)
        }

        val call = movieService.list(search, "movie")

        call.enqueue(object : Callback<SearchResults> {
            override fun onResponse(call: Call<SearchResults>, response: Response<SearchResults>) {
                if (isViewAttached) {
                    view!!.setData(response.body().movies)
                    view!!.showContent()
                }
            }

            override fun onFailure(call: Call<SearchResults>, t: Throwable) {
                if (isViewAttached) {
                    view!!.showError(t, pullToRefresh)
                }

                Logger.e("Errroooooorrrrrrrr !")
                Logger.e(t.message!!)
            }
        })
    }

    /**
     * Open the movie details in a new activity.
     * @param movieId The ID of the selected movie.
     */
    override fun openMovieDetails(context: Context, movieId: String) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailFragment.ARG_MOVIE_ID, movieId)

        context.startActivity(intent)
    }
}
