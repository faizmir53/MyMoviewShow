package com.movie.mymovieshow.viewmodel;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.movie.mymovieshow.adapter.CastAdapter;
import com.movie.mymovieshow.adapter.MoviesAdapter;
import com.movie.mymovieshow.adapter.ReviewAdapter;
import com.movie.mymovieshow.model.Cast;
import com.movie.mymovieshow.model.ComResponse;
import com.movie.mymovieshow.model.Genre;
import com.movie.mymovieshow.model.MovieCredit;
import com.movie.mymovieshow.model.MovieDetails;
import com.movie.mymovieshow.model.MovieReview;
import com.movie.mymovieshow.model.MovieReviewResult;
import com.movie.mymovieshow.model.MovieSimilar;
import com.movie.mymovieshow.network.ApiClient;
import com.movie.mymovieshow.network.ApiInterface;
import com.movie.mymovieshow.utility.OnItemClickListener;
import com.movie.mymovieshow.view.MovieDetailsActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsViewModel extends ViewModel {

    private MutableLiveData<ComResponse> comResponseMutableLiveData =  new MutableLiveData<>();
    private CastAdapter castAdapter;
    private ReviewAdapter reviewAdapter;


    public void initAdapter(){
        castAdapter = new CastAdapter();
    }

    public CastAdapter getAdapter(){
        return castAdapter;
    }

    public void initReviewAdapter(){
        reviewAdapter = new ReviewAdapter();
    }

    public ReviewAdapter getReviewAdapter(){
        return reviewAdapter;
    }

    public MutableLiveData<ComResponse> getComResponseMutableLiveData(){
        return comResponseMutableLiveData;
    }
    public  void setReviewList(List<MovieReviewResult> reviewList){
        reviewAdapter.setListData(reviewList);
    }

    public void setListToAdapter(List<Cast> casts){
        if (casts != null) {
            castAdapter.setListData(casts);
            castAdapter.notifyDataSetChanged();
        }
    }
    public void getMoviesDetails(String mId){
        getDetails(mId);
    }
    public String getGenres(List<Genre> genres){
        StringBuilder result = new StringBuilder();
        for (Genre gen : genres){
            result.append(gen.getName());
            result.append(",");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1).toString(): "";

    }

    private void getDetails(String movieId){
        Observable<MovieDetails> movieDeatils = ApiClient.getClient()
                .create(ApiInterface.class)
                .movieDetails(movieId,"57a354d40b98dd07afc3d4f7be447097")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<MovieReview> movieReview = ApiClient.getClient()
                .create(ApiInterface.class)
                .movieReview(movieId,"57a354d40b98dd07afc3d4f7be447097")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<MovieCredit> movieCredit = ApiClient.getClient()
                .create(ApiInterface.class)
                .movieCredit(movieId,"57a354d40b98dd07afc3d4f7be447097")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<MovieSimilar> movieSimilar = ApiClient.getClient()
                .create(ApiInterface.class)
                .movieSimilar(movieId,"57a354d40b98dd07afc3d4f7be447097")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<ComResponse> combined = Observable.zip(movieDeatils.subscribeOn(Schedulers.io()),
                movieReview.subscribeOn(Schedulers.io()), movieCredit.subscribeOn(Schedulers.io()),
                movieSimilar.subscribeOn(Schedulers.io()), new Function4<MovieDetails, MovieReview, MovieCredit, MovieSimilar, ComResponse>() {
                    @Override
                    public ComResponse apply(MovieDetails movieDetails, MovieReview movieReview, MovieCredit movieCredit, MovieSimilar movieSimilar) throws Exception {
                        return new ComResponse(movieDetails,movieReview,movieCredit,movieSimilar);
                    }
                });
        combined.subscribe(new Observer<ComResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ComResponse comResponse) {
                Log.d("RESPONSE", "onNext:=======" + comResponse.toString());
                comResponseMutableLiveData.setValue(comResponse);

            }

            @Override
            public void onError(Throwable e) {
                Log.d("RESPONSE", "onNext:=======");

            }

            @Override
            public void onComplete() {
                Log.d("RESPONSE", "DONE==========");

            }
        });

    }
}
