package com.btxsqdr.githubshowcase.network;


import com.btxsqdr.githubshowcase.models.GithubRepo;
import com.btxsqdr.githubshowcase.models.GithubRepoList;
import com.btxsqdr.githubshowcase.models.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {

  public static final String GITHUB_ACCESS_TOKEN = "c8d2002afe7ff775bd978c18827f8eb275db210e";

  @GET("users/{username}/repos?access_token=" + GITHUB_ACCESS_TOKEN)
  Call<List<GithubRepo>> getReposForUser(@Path("username") String username);

  @GET("repositories?&access_token=" + GITHUB_ACCESS_TOKEN)
  Call<List<GithubRepo>> getAllRepos();

  @GET("search/repositories?per_page=10&access_token=" + GITHUB_ACCESS_TOKEN)
  Call<GithubRepoList> getAllReposByTerm(
          @Query("q") String searchTerm,
          @Query("page") Integer page
  );

  @GET("users/{username}?access_token=" + GITHUB_ACCESS_TOKEN)
  Call<GithubUser> getUser(@Path("username") String username);
}
