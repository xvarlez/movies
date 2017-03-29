package me.varlez.movies.movie.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import me.varlez.movies.MoviesApp
import me.varlez.movies.R
import me.varlez.movies.common.di.component.MoviesComponent
import me.varlez.movies.common.model.Movie
import me.varlez.movies.movie.detail.presenter.DefaultMovieDetailPresenter
import me.varlez.movies.movie.detail.presenter.MovieDetailPresenter
import me.varlez.movies.movie.list.view.MovieListActivity
import javax.inject.Inject

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a [MovieListActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : MvpLceFragment<RelativeLayout, Movie, MovieDetailView, MovieDetailPresenter>(), MovieDetailView {

    @Inject
    internal lateinit var picasso: Picasso

    private lateinit var moviesComponent: MoviesComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        moviesComponent = MoviesApp.get(context).moviesComponent
        super.onCreate(savedInstanceState)
        moviesComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        fab?.setOnClickListener { presenter.openImdb(activity) }

        if (arguments.containsKey(ARG_MOVIE_ID)) {
            loadData(false)
        }
    }

    override fun setData(data: Movie) {
        toolbar_layout?.title = data.title

        picasso.load(data.posterUrl)
                .fit()
                .centerCrop()
                .into(imageview_header)
        movie_plot_textview?.text = data.plot
        movie_director_textview?.text = data.director
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.details(arguments.getString(ARG_MOVIE_ID), pullToRefresh)
    }

    override fun createPresenter(): MovieDetailPresenter {
        return DefaultMovieDetailPresenter(moviesComponent)
    }

    override fun getErrorMessage(e: Throwable, pullToRefresh: Boolean): String {
        return e.message!!
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        val ARG_MOVIE_ID = "movieId"
    }
}
