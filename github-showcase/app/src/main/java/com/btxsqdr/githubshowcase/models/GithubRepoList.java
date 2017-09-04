
package com.btxsqdr.githubshowcase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

public class GithubRepoList {

    @SerializedName("total_count")
    @Expose
    public long totalCount;

    @SerializedName("incomplete_results")
    @Expose
    public boolean incompleteResults;

    @SerializedName("items")
    @Expose
    public List<GithubRepo> items;

}
