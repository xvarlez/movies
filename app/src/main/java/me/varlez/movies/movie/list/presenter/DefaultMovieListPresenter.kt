package me.varlez.movies.movie.list.presenter

import android.content.Context
import android.content.Intent
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.varlez.movies.common.Logger
import me.varlez.movies.common.di.component.MovieComponent
import me.varlez.movies.common.interactor.MovieInteractor
import me.varlez.movies.common.model.Movie
import me.varlez.movies.movie.detail.view.MovieDetailActivity
import me.varlez.movies.movie.detail.view.MovieDetailFragment
import me.varlez.movies.movie.list.view.MovieListView
import javax.inject.Inject

/**
 * Default implementation of our Movies list presenter, in charge of retrieving
 * the data and passing it to the view.
 */
class DefaultMovieListPresenter(movieComponent: MovieComponent) : MvpBasePresenter<MovieListView>(), MovieListPresenter {

    @Inject
    internal lateinit var movieInteractor: MovieInteractor

    init {
        movieComponent.inject(this)
    }

    /**
     * Retrieve the movie
     */
    override fun movieList(search: String, pullToRefresh: Boolean) {
        movieInteractor.list(search, "movie")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading(pullToRefresh) }
                .subscribe({ movies -> showContent(movies) },
                        { throwable -> showError(throwable, pullToRefresh) },
                        { Logger.d("Got Movies list") })
    }

    fun showContent(movies: List<Movie>) {
        view?.setData(movies)
        view?.showContent()
    }

    fun showError(throwable: Throwable, pullToRefresh: Boolean) {
        view?.showError(throwable, pullToRefresh)
        Logger.e("Errroooooorrrrrrrr ! : ${throwable.message!!}")
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
