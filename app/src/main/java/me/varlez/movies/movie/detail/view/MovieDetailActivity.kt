package me.varlez.movies.movie.detail.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import me.varlez.movies.R
import me.varlez.movies.movie.list.view.MovieListActivity

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MovieListActivity].
 */
class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val arguments = Bundle()
        arguments.putString(MovieDetailFragment.ARG_MOVIE_ID,
                intent.getStringExtra(MovieDetailFragment.ARG_MOVIE_ID))
        val fragment = MovieDetailFragment()
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, MovieListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
