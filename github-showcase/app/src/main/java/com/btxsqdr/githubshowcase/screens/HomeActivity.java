package com.btxsqdr.githubshowcase.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.btxsqdr.githubshowcase.models.GithubRepoList;
import com.squareup.picasso.Picasso;
import com.btxsqdr.githubshowcase.GithubApplication;
import com.btxsqdr.githubshowcase.R;
import com.btxsqdr.githubshowcase.models.GithubRepo;
import com.btxsqdr.githubshowcase.network.GithubService;
import com.btxsqdr.githubshowcase.screens.home.AdapterRepos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.repo_home_list)
    ListView listView;

    @BindView(R.id.et_search)
    EditText editTextSearch;

    @Inject
    GithubService githubService;

    @Inject
    AdapterRepos adapterRepos;

    Call<GithubRepoList> reposCall;

    List<GithubRepo> mRepoCache = null;
    Integer mPage = 0;
    String mPreviousSearchTerm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        HomeActivityComponent component = DaggerHomeActivityComponent.builder()
                .homeActivityModule(new HomeActivityModule(this))
                .githubApplicationComponent(GithubApplication.get(this).component())
                .build();

        component.injectHomeActivity(this);

        mRepoCache = new ArrayList<>();

        listView.setAdapter(adapterRepos);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    mPage += 1;

                    updateListView();
                }
            }
        });

        updateListView();

        hideSoftKeyboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (reposCall != null) {
            reposCall.cancel();
        }
    }

    @OnTextChanged(value = { R.id.et_search }, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inputName(Editable editable) {
        if (editable.toString() != null && editable.toString().length() >= 3) {
            updateListView(editable.toString());
        }
    }

    public void updateListView() {
        updateListView(editTextSearch.getText().toString());
    }

    public void updateListView(String searchTerm) {
        if (searchTerm == null || searchTerm.length() < 1) searchTerm = "tetris";

        if (mPreviousSearchTerm == null || !mPreviousSearchTerm.contentEquals(searchTerm)) {
            mPage = 0;
            mRepoCache.clear();
        }

        reposCall = githubService.getAllReposByTerm(searchTerm, mPage);

        reposCall.enqueue(new Callback<GithubRepoList>() {
            @Override
            public void onResponse(Call<GithubRepoList> call, Response<GithubRepoList> response) {
                if (response.body() != null) {
                    mRepoCache.addAll(response.body().items);
                    adapterRepos.swapData(mRepoCache);
                }
            }

            @Override
            public void onFailure(Call<GithubRepoList> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error getting repos " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mPreviousSearchTerm = searchTerm;
    }

    private void hideSoftKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        if (getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
