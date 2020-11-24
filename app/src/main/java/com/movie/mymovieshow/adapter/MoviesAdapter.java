package com.movie.mymovieshow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.movie.mymovieshow.R;
import com.movie.mymovieshow.databinding.MovieItemRowBinding;
import com.movie.mymovieshow.model.Result;
import com.movie.mymovieshow.utility.OnItemClickListener;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Result> resultList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public MoviesAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.movie_item_row, parent, false);
        return new MoviesAdapter.MoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, final int position) {
        holder.movieItemRowBinding.setMovie(resultList.get(position));
        holder.movieItemRowBinding.setImageUrl("https://image.tmdb.org/t/p/w185"+resultList.get(position).getPosterPath());
        holder.movieItemRowBinding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onRowClick(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (resultList == null) ? 0 : resultList.size();
    }

    public void setListData(List<Result> resultList){
        this.resultList = resultList;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        MovieItemRowBinding movieItemRowBinding;
        public MoviesViewHolder(@NonNull MovieItemRowBinding movieItemRowBinding) {
            super(movieItemRowBinding.getRoot());
            this.movieItemRowBinding = movieItemRowBinding;
        }
    }
}
