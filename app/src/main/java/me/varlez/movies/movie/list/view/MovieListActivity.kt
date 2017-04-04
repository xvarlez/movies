package me.varlez.movies.movie.list.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity
import kotlinx.android.synthetic.main.movie_list.*
import me.varlez.movies.MoviesApp
import me.varlez.movies.R
import me.varlez.movies.common.di.component.AppComponent
import me.varlez.movies.common.di.component.DaggerMovieComponent
import me.varlez.movies.common.di.module.MovieModule
import me.varlez.movies.common.model.Movie
import me.varlez.movies.movie.detail.view.MovieDetailActivity
import me.varlez.movies.movie.detail.view.MovieDetailFragment
import me.varlez.movies.movie.list.presenter.DefaultMovieListPresenter
import me.varlez.movies.movie.list.presenter.MovieListPresenter
import me.varlez.movies.movie.list.view.adapter.MovieRecyclerViewAdapter


/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [MovieDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MovieListActivity : MvpLceActivity<SwipeRefreshLayout, List<Movie>, MovieListView, MovieListPresenter>(), MovieListView, MovieRecyclerViewAdapter.MovieClickListener, SwipeRefreshLayout.OnRefreshListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var isTwoPane: Boolean = false

    /**
     * The dagger component that will be used to inject our dependencies.
     */
    private lateinit var appComponent: AppComponent

    /**
     * The movie title that should be searched.
     */
    private var searchQuery: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = MoviesApp.get(this).appComponent

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        contentView.setOnRefreshListener(this)
        contentView.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

        supportActionBar?.title = title

        if (movie_detail_container != null) {
            isTwoPane = true
            val layoutManager = LinearLayoutManager(this)
            val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)

            movie_list?.addItemDecoration(dividerItemDecoration)
            movie_list?.layoutManager = layoutManager
        } else {
            movie_list?.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.grid_span))
        }

        searchQuery = "Batman"

        loadData(false)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    /**
     * Handles the intent received by the activity and performs a search if needed.
     * @param intent
     */
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY)
            loadData(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_movie_list, menu)

        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun movieClicked(movieId: String) {
        if (isTwoPane) {
            val arguments = Bundle()
            arguments.putString(MovieDetailFragment.ARG_MOVIE_ID, movieId)
            val fragment = MovieDetailFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit()
        } else {
            presenter.openMovieDetails(this, movieId)
        }
    }

    override fun showContent() {
        super.showContent()
        contentView.isRefreshing = false
    }

    override fun showError(e: Throwable, pullToRefresh: Boolean) {
        super.showError(e, false)
        contentView.isRefreshing = false
    }

    override fun getErrorMessage(e: Throwable, pullToRefresh: Boolean): String {
        return "Oops ! Something bad happened :\n\n  ${e.message} \n\nTouch to retry"
    }

    override fun setData(data: List<Movie>) {
        movie_list?.adapter = MovieRecyclerViewAdapter(data, this, appComponent)
    }

    override fun loadData(pullToRefresh: Boolean) {
        if (searchQuery != null) {
            presenter.movieList(searchQuery!!, pullToRefresh)
        }
    }

    override fun createPresenter(): MovieListPresenter {
        val movieComponent = DaggerMovieComponent
                .builder()
                .appComponent(appComponent)
                .movieModule(MovieModule(this))
                .build()
        return DefaultMovieListPresenter(movieComponent)
    }

    override fun onRefresh() {
        loadData(true)
    }
}
