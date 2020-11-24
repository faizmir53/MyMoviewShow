package com.movie.mymovieshow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.movie.mymovieshow.R;
import com.movie.mymovieshow.databinding.CastItemRowLayBinding;
import com.movie.mymovieshow.databinding.MovieItemRowBinding;
import com.movie.mymovieshow.model.Cast;
import com.movie.mymovieshow.model.Result;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewModel> {
    private LayoutInflater layoutInflater;
    private List<Cast> castList;



    @NonNull
    @Override
    public CastViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CastItemRowLayBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.cast_item_row_lay, parent, false);
        return new CastAdapter.CastViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewModel holder, int position) {
        holder.castItemRowLayBinding.setCast(castList.get(position));
        if (castList.get(position).getProfilePath() != null) {
            holder.castItemRowLayBinding.setImageUrl("https://image.tmdb.org/t/p/w185" + castList.get(position).getProfilePath());
        }else {
            holder.castItemRowLayBinding.profileImage.setImageResource(R.drawable.defaultpf);
        }


    }
    public void setListData(List<Cast> castList){
        this.castList = castList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return (castList != null) ? castList.size() : 0;
    }


    public class CastViewModel extends RecyclerView.ViewHolder{
        private CastItemRowLayBinding castItemRowLayBinding;

        public CastViewModel(@NonNull CastItemRowLayBinding castItemRowLayBinding) {
            super(castItemRowLayBinding.getRoot());
            this.castItemRowLayBinding = castItemRowLayBinding;
        }
    }

}
