package me.varlez.movies.movie.detail.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.varlez.movies.MoviesApp;
import me.varlez.movies.R;
import me.varlez.movies.common.di.component.MoviesComponent;
import me.varlez.movies.common.model.Movie;
import me.varlez.movies.movie.detail.presenter.DefaultMovieDetailPresenter;
import me.varlez.movies.movie.detail.presenter.MovieDetailPresenter;
import me.varlez.movies.movie.list.view.MovieListActivity;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends MvpLceFragment<RelativeLayout, Movie, MovieDetailView, MovieDetailPresenter> implements MovieDetailView {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_MOVIE_ID = "movieId";

    @Bind(R.id.movie_director)
    TextView directorTextView;

    @Bind(R.id.movie_plot)
    TextView plotTextView;

    @Inject
    Picasso picasso;

    private MoviesComponent moviesComponent;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesComponent = ((MoviesApp) getActivity().getApplication()).getMoviesComponent();
        moviesComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_detail, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openImdb(getActivity());
            }
        });

        if (getArguments().containsKey(ARG_MOVIE_ID)) {
            loadData(false);
        }
    }

    @Override
    public void setData(Movie data) {
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        ImageView headerImage = (ImageView) getActivity().findViewById(R.id.imageview_header);

        if (appBarLayout != null) {
            appBarLayout.setTitle(data.getTitle());
        }

        picasso.load(data.getPosterUrl()).fit().centerCrop().into(headerImage);
        plotTextView.setText(data.getPlot());
        directorTextView.setText(data.getDirector());
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.details(getArguments().getString(ARG_MOVIE_ID), pullToRefresh);
    }

    @NonNull
    @Override
    public MovieDetailPresenter createPresenter() {
        return new DefaultMovieDetailPresenter(moviesComponent);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }
}
