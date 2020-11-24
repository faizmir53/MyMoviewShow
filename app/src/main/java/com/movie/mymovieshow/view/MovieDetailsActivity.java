package com.movie.mymovieshow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.movie.mymovieshow.R;
import com.movie.mymovieshow.adapter.CastAdapter;
import com.movie.mymovieshow.databinding.ActivityMovieDetailsBinding;
import com.movie.mymovieshow.model.Cast;
import com.movie.mymovieshow.model.ComResponse;
import com.movie.mymovieshow.model.Genre;
import com.movie.mymovieshow.model.MovieReview;
import com.movie.mymovieshow.model.MovieReviewResult;
import com.movie.mymovieshow.viewmodel.DashboardViewModel;
import com.movie.mymovieshow.viewmodel.MovieDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    private String movieId;
    private ActivityMovieDetailsBinding movieDetailsBinding;
    private MovieDetailsViewModel movieDetailsViewModel;
    private ComResponse combinedResponse;
    private String genres;
    private CastAdapter castAdapter;
    private List<Cast> castList;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_movie_details);
        movieDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_movie_details);
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieDetailsBinding.setLifecycleOwner(this);
      //  movieDetailsBinding.setDetailsViewModle(movieDetailsViewModel);
        movieDetailsBinding.setDetailsViewModel(movieDetailsViewModel);
        movieDetailsBinding.castRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        movieDetailsViewModel.initAdapter();
        movieDetailsViewModel.initReviewAdapter();
       movieId = getIntent().getStringExtra("movieId");
      movieDetailsBinding.shimmerViewContainer.startShimmerAnimation();
        movieDetailsViewModel.getMoviesDetails(movieId);

        movieDetailsViewModel.getComResponseMutableLiveData().observe(this, new Observer<ComResponse>() {
            @Override
            public void onChanged(ComResponse comResponse) {
                combinedResponse = comResponse;
                if (combinedResponse != null) {
                    movieDetailsBinding.shimmerViewContainer.stopShimmerAnimation();
                    movieDetailsBinding.shimmerViewContainer.setVisibility(View.GONE);
                    movieDetailsBinding.movieDetailsLay.setVisibility(View.VISIBLE);

                    Log.d("HERE", "HERE");
                    movieDetailsBinding.setImageUrl("https://image.tmdb.org/t/p/w185"+combinedResponse.movieDetails.getPosterPath());
                    movieDetailsBinding.ratingBar.setRating(combinedResponse.movieDetails.getVoteAverage());
                    movieDetailsBinding.setMovieDetails(combinedResponse.movieDetails);
                    genres = movieDetailsViewModel.getGenres(combinedResponse.movieDetails.getGenres());
                    movieDetailsBinding.setGenres(genres);
                    List<Cast> castList = combinedResponse.movieCredit.getCast();
                    if (combinedResponse.movieCredit.getCast() != null) {
                        movieDetailsViewModel.setListToAdapter(castList);
                        // castAdapter.setListData(castList);
                        //castAdapter.notifyDataSetChanged();
                    }

                    List<MovieReviewResult> movieReviewList = combinedResponse.movieReview.getResults();
                    if (movieReviewList != null) {
                        movieDetailsViewModel.setReviewList(movieReviewList);
                    }
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        movieDetailsBinding.shimmerViewContainer.stopShimmerAnimation();

    }
}