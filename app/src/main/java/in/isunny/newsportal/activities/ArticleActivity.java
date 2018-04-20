package in.isunny.newsportal.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.isunny.newsportal.R;
import in.isunny.newsportal.models.Articles;

public class ArticleActivity extends AppCompatActivity {

    ImageView imageView;
    TextView title, description;
    ImageButton like, bookmark, share, open;
    Articles article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        article = (Articles) getIntent().getSerializableExtra("article");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.textView4);
        description = findViewById(R.id.textView5);
        like = findViewById(R.id.likeart);
        bookmark = findViewById(R.id.bmkart);
        share = findViewById(R.id.sharebtn);
        open = findViewById(R.id.openext);

        Picasso.get().load(article.getUrlToImage()).into(imageView);
        title.setText(article.getTitle());
        description.setText(article.getDescription());

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.getTag().toString().equals("notliked")) {
                    like.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    like.setTag("liked");
                } else {
                    like.setTag("notliked");
                    like.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmark.getTag().toString().equals("notbookmarked")) {
                    bookmark.setBackgroundResource(R.drawable.ic_bookmark_black_24dp);
                    bookmark.setTag("bookmarked");
                } else {
                    bookmark.setTag("notbookmarked");
                    bookmark.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
                }
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                startActivity(browserIntent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder.from(ArticleActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Share Article")
                        .setText(article.getTitle())
                        .startChooser();
            }
        });

    }

}
