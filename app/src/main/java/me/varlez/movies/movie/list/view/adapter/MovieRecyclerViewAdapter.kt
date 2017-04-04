package me.varlez.movies.movie.list.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_list.view.*
import me.varlez.movies.R
import me.varlez.movies.common.di.component.AppComponent
import me.varlez.movies.common.model.Movie
import java.util.*
import javax.inject.Inject


/**
 * Simple adapter used to display the movies list.
 */
class MovieRecyclerViewAdapter(private val values: List<Movie>,
                               private val listener: MovieRecyclerViewAdapter.MovieClickListener,
                               moviesComponent: AppComponent) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

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
                .inflate(R.layout.item_movie_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = values[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            picassoService.load(movie.posterUrl)
                    .fit()
                    .into(itemView.imageview_poster)

            itemView.textview_title.text = movie.title
            itemView.textview_year.text = movie.year.toString()
            view.setOnClickListener { listener.movieClicked(movie.id) }
        }
    }
}