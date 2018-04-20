package in.isunny.newsportal.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.isunny.newsportal.R;
import in.isunny.newsportal.adapters.ArticlesAdapter;
import in.isunny.newsportal.models.Articles;
import in.isunny.newsportal.models.Feed;
import in.isunny.newsportal.utils.MyAsyncTask;

/**
 * Created by Sunny on 4/19/2018.
 */

public class SourceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArticlesAdapter recyclerAdapter;
    RecyclerView.LayoutManager linearLayoutManager, gridLayoutManager;
    Toolbar toolbar;
    int i = 0;
    List<Articles> articlesList;
    String source, image, name;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

        source = getIntent().getStringExtra("source");
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");

        toolbar.setTitle(name);
        imageView = findViewById(R.id.sourceimg);
        Picasso.get().load(image).resize(250, 150).centerCrop().into(imageView);

        linearLayoutManager = new LinearLayoutManager(SourceActivity.this);
        gridLayoutManager = new GridLayoutManager(SourceActivity.this, 2);

        recyclerView = findViewById(R.id.sourcerecyclerview);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        @SuppressLint("StaticFieldLeak")
        MyAsyncTask loader = new MyAsyncTask(source) {
            @Override
            public void onResponseReceived(Feed result) {
                articlesList = result.getArticles();
                recyclerAdapter = new ArticlesAdapter(getApplicationContext(), SourceActivity.this, articlesList, false);
                recyclerView.setAdapter(recyclerAdapter);
            }
        };
        loader.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        if (recyclerView.getLayoutManager() == linearLayoutManager) {
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
        } else {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}

