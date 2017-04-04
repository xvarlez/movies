package me.varlez.movies.movie.detail.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.varlez.movies.common.Logger
import me.varlez.movies.common.di.component.MovieComponent
import me.varlez.movies.common.interactor.MovieInteractor
import me.varlez.movies.common.model.Movie
import me.varlez.movies.movie.detail.view.MovieDetailView
import javax.inject.Inject

/**
 * Default implementation of the movie details presenter, in charge of
 * fetching the data and passing it to the view.
 */
class DefaultMovieDetailPresenter(movieComponent: MovieComponent) : MvpBasePresenter<MovieDetailView>(), MovieDetailPresenter {

    @Inject
    lateinit var movieInteractor: MovieInteractor

    init {
        movieComponent.inject(this)
    }

    override fun details(movieId: String, pullToRefresh: Boolean) {
        movieInteractor.movie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading(pullToRefresh) }
                .subscribe({ movie -> showContent(movie) },
                        { throwable -> showError(throwable, pullToRefresh) },
                        { Logger.d("Got Movie details") })
    }

    fun showContent(movie: Movie) {
        view?.setData(movie)
        view?.showContent()
        Logger.d("OK ! Got the movie details")
    }

    fun showError(throwable: Throwable, pullToRefresh: Boolean) {
        view?.showError(throwable, pullToRefresh)
        Logger.e("Errorrrr ! Couldn't get the movie details :( ${throwable.message!!}")
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
        get() = Uri.parse(String.format(IMDB_URL, "3"))

    companion object {
        /**
         * The IMDB URL that will be opened in the web browser.
         */
        private val IMDB_URL = "http://www.imdb.com/title/%s"
    }
}
