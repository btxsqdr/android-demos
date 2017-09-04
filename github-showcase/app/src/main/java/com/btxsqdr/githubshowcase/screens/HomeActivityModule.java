package com.btxsqdr.githubshowcase.screens;

import android.widget.Toast;

import com.btxsqdr.githubshowcase.models.GithubRepoList;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Module
public class HomeActivityModule {

    private final HomeActivity homeActivity;

    public HomeActivityModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    private Call<GithubRepoList> reposCall;

    @Provides
    @HomeActivityScope
    public HomeActivity homeActivity() {
        return homeActivity;
    }

}
