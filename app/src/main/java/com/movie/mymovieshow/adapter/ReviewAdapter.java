package com.movie.mymovieshow.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.movie.mymovieshow.R;
import com.movie.mymovieshow.databinding.CastItemRowLayBinding;
import com.movie.mymovieshow.databinding.ReviewRowLayBinding;
import com.movie.mymovieshow.model.Cast;
import com.movie.mymovieshow.model.MovieReviewResult;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private LayoutInflater layoutInflater;
    private List<MovieReviewResult> movieReviewResultList;


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ReviewRowLayBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.review_row_lay, parent, false);
        return new ReviewAdapter.ReviewViewHolder(binding);    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.reviewRowLayBinding.setMovieReview(movieReviewResultList.get(position));
        if (movieReviewResultList.get(position).getAuthorDetails().getRating() != null) {
            holder.reviewRowLayBinding.ratingBar.setRating(movieReviewResultList.get(position).getAuthorDetails().getRating());
        }
        if (movieReviewResultList.get(position).getAuthorDetails().getAvatarPath() != null) {

            Glide.with(holder.itemView.getContext()).load(
                    "https://image.tmdb.org/t/p/w185" + movieReviewResultList.get(position).getAuthorDetails().getAvatarPath()
            )
                    .error(Glide.with(holder.reviewRowLayBinding.profileImage).load(R.drawable.defaultpf))
                    .into(holder.reviewRowLayBinding.profileImage);
        }

    }

    @Override
    public int getItemCount() {
        return (movieReviewResultList != null) ? movieReviewResultList.size() : 0;
    }
    public void setListData(List<MovieReviewResult> movieReviewResultList){
        this.movieReviewResultList = movieReviewResultList;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
    private ReviewRowLayBinding reviewRowLayBinding;
        public ReviewViewHolder(@NonNull ReviewRowLayBinding reviewRowLayBinding) {
            super(reviewRowLayBinding.getRoot());
            this.reviewRowLayBinding = reviewRowLayBinding;
        }
    }
}
