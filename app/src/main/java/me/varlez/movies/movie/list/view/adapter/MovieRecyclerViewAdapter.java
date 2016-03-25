package me.varlez.movies.movie.list.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import me.varlez.movies.R;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.model.Movie;


/**
 * Simple adapter used to display the movies list.
 */
public class MovieRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    public interface MovieClickListener {
        void movieClicked(String movieId);
    }

    private final List<Movie> values;

    @Inject
    Picasso picassoService;

    private MovieClickListener listener;

    public MovieRecyclerViewAdapter(List<Movie> items, MovieClickListener listener, MoviesComponent moviesComponent) {
        this.values = items;
        this.listener = listener;

        moviesComponent.inject(this);

        // We sort the movies by year (descending)
        Collections.sort(this.values, new Movie.YearComparator());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = values.get(position);

        picassoService.load(movie.getPosterUrl())
                .fit()
                .into(holder.posterView);

        holder.titleView.setText(movie.getTitle());
        holder.yearView.setText(movie.getYear().toString());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.movieClicked(movie.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView titleView;
        public final TextView yearView;
        public final ImageView posterView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = (TextView) view.findViewById(R.id.textview_title);
            yearView = (TextView) view.findViewById(R.id.textview_year);
            posterView = (ImageView) view.findViewById(R.id.imageview_poster);
        }
    }
}