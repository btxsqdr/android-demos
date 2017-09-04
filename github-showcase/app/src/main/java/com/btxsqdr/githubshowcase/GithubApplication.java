package com.btxsqdr.githubshowcase;

import android.app.Activity;
import android.app.Application;

import com.squareup.picasso.Picasso;
import com.btxsqdr.githubshowcase.network.GithubService;
import timber.log.Timber;

public class GithubApplication extends Application {

    private GithubApplicationComponent component;

    public static GithubApplication get(Activity activity) {
        return (GithubApplication) activity.getApplication();
    }

    private GithubService githubService;
    private Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        component = DaggerGithubApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        githubService = component.getGithubService();
        picasso = component.getPicasso();
    }

    public GithubApplicationComponent component() {
        return component;
    }
}