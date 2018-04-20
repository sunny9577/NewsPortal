package in.isunny.newsportal.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import in.isunny.newsportal.R;
import in.isunny.newsportal.adapters.ArticlesAdapter;
import in.isunny.newsportal.models.Articles;
import in.isunny.newsportal.models.Feed;
import in.isunny.newsportal.utils.APIS;
import in.isunny.newsportal.utils.MyAsyncTask;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticlesAdapter recyclerAdapter;
    private List<Articles> articlesList;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.verticalrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        swipeRefreshLayout = findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        loadNews();


    }

    void loadNews() {
        @SuppressLint("StaticFieldLeak")
        MyAsyncTask theverge = new MyAsyncTask(APIS.TV_FEED) {
            @Override
            public void onResponseReceived(Feed result) {
                articlesList = result.getArticles();
            }
        };
        @SuppressLint("StaticFieldLeak")
        MyAsyncTask ars = new MyAsyncTask(APIS.ARS_FEED) {
            @Override
            public void onResponseReceived(Feed result) {
                articlesList.addAll(result.getArticles());
                recyclerAdapter = new ArticlesAdapter(getApplicationContext(), MainActivity.this, articlesList);
                recyclerView.setAdapter(recyclerAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        theverge.execute();
        ars.execute();
    }

}
