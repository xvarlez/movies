package me.varlez.movies.movie.list.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.varlez.movies.R
import me.varlez.movies.common.di.component.MoviesComponent
import me.varlez.movies.common.model.Movie
import java.util.*
import javax.inject.Inject


/**
 * Simple adapter used to display the movies list.
 */
class MovieRecyclerViewAdapter(private val values: List<Movie>,
                               private val listener: MovieRecyclerViewAdapter.MovieClickListener,
                               moviesComponent: MoviesComponent) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    interface MovieClickListener {
        fun movieClicked(movieId: String)
    }

    @Inject
    internal lateinit var picassoService: Picasso

    init {

        moviesComponent.inject(this)

        // We sort the movies by year (descending)
        Collections.sort(this.values, Movie.YearComparator())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = values[position]

        picassoService.load(movie.posterUrl)
                .fit()
                .into(holder.posterView)

        holder.titleView.text = movie.title
        holder.yearView.text = movie.year.toString()
        holder.view.setOnClickListener { listener.movieClicked(movie.id) }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.textview_title) as TextView
        val yearView: TextView = view.findViewById(R.id.textview_year) as TextView
        val posterView: ImageView = view.findViewById(R.id.imageview_poster) as ImageView
    }
}