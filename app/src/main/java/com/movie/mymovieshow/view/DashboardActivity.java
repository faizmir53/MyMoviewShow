package com.movie.mymovieshow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.movie.mymovieshow.R;
import com.movie.mymovieshow.databinding.ActivityDashboardBinding;
import com.movie.mymovieshow.model.Result;
import com.movie.mymovieshow.viewmodel.DashboardViewModel;

import java.util.List;


public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding activityDashboardBinding;
    private DashboardViewModel dashboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        activityDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        activityDashboardBinding.setLifecycleOwner(this);
        activityDashboardBinding.setDashboardViewModel(dashboardViewModel);
        dashboardViewModel.initAdapter();
        activityDashboardBinding.shimmerViewContainer.startShimmerAnimation();
        dashboardViewModel.getMovie();
        dashboardViewModel.getResultData().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> resultList) {
                if (resultList != null  || resultList.size() > 0){
                    activityDashboardBinding.shimmerViewContainer.stopShimmerAnimation();
                    activityDashboardBinding.shimmerViewContainer.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDashboardBinding.shimmerViewContainer.stopShimmerAnimation();
        activityDashboardBinding.shimmerViewContainer.setVisibility(View.GONE);


    }

    @Override
    protected void onPause() {
        super.onPause();
        activityDashboardBinding.shimmerViewContainer.stopShimmerAnimation();
        activityDashboardBinding.shimmerViewContainer.setVisibility(View.GONE);


    }
}