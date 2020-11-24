package com.movie.mymovieshow.viewmodel;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.movie.mymovieshow.adapter.MoviesAdapter;
import com.movie.mymovieshow.model.MovieDetails;
import com.movie.mymovieshow.model.Movies;
import com.movie.mymovieshow.model.Result;
import com.movie.mymovieshow.network.ApiClient;
import com.movie.mymovieshow.network.ApiInterface;
import com.movie.mymovieshow.utility.OnItemClickListener;
import com.movie.mymovieshow.view.MovieDetailsActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {
    private MoviesAdapter moviesAdapter;
    private MutableLiveData<List<Result>> listMutableLiveData= new MutableLiveData<>();
    private List<Result> resultList;


    public MutableLiveData<List<Result>> getResultData(){
        return listMutableLiveData;
    }


    public void initAdapter(){
        moviesAdapter = new MoviesAdapter(new OnItemClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                int id = resultList.get(position).getId();
                Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
                i.putExtra("movieId",String.valueOf(id));
                view.getContext().startActivity(i);
            }
        });
    }

    public boolean linearSearch(String searchTerm){
        boolean searchReturn = false;
        for (int index = 0; index < resultList.size() - 1; index++) {
            if (resultList.get(index).getTitle().equals(searchTerm)) {
                searchReturn = true;
                break;
            }
        }
        if (searchReturn) {
            System.out.println("is found in the array.");
        } else {
            System.out.println("not found in the array");
        }

        return searchReturn;
    }

    public MoviesAdapter getAdapter(){
        return moviesAdapter;
    }

public void getMovie(){
    Observable<Movies> movieDeatils = ApiClient.getClient()
            .create(ApiInterface.class)
            .getMoviesList("57a354d40b98dd07afc3d4f7be447097","en-In","2","")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());

    movieDeatils.subscribe(new Observer<Movies>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Movies movies) {
            Movies data = movies;
            resultList = data.getResults();
            listMutableLiveData.setValue(data.getResults());
           // Log.d("HERE", String.valueOf(movies.getPage()));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            moviesAdapter.setListData(listMutableLiveData.getValue());
            moviesAdapter.notifyDataSetChanged();
        }
    });
}
}
