package com.btxsqdr.githubshowcase;

import com.squareup.picasso.Picasso;
import com.btxsqdr.githubshowcase.network.GithubService;

import dagger.Component;

@GithubApplicationScope
@Component(modules = {GithubServiceModule.class, PicassoModule.class, ActivityModule.class})
public interface GithubApplicationComponent {

    Picasso getPicasso();
    GithubService getGithubService();

}
