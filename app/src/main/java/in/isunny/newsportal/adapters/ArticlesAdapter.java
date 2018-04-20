package in.isunny.newsportal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.isunny.newsportal.R;
import in.isunny.newsportal.activities.ArticleActivity;
import in.isunny.newsportal.models.Articles;

/**
 * Created by Sunny on 4/14/2018.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private boolean trending = true;
    private List<Articles> articlesList;

    public ArticlesAdapter(Context c, Activity a, List<Articles> articles) {
        context = c;
        activity = a;
        articlesList = articles;
    }

    public ArticlesAdapter(Context c, Activity a, List<Articles> articles, boolean t) {
        context = c;
        activity = a;
        articlesList = articles;
        trending = t;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_view, parent, false);
            return new ViewHolder1(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontalrecyclercardview, parent, false);
        return new ViewHolder0(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == 0) {
            ViewHolder0 viewHolder0 = (ViewHolder0) holder;
            viewHolder0.recyclerView.setHasFixedSize(false);
            viewHolder0.recyclerView.setAdapter(new SourcesAdapter(context, activity));
            viewHolder0.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        }
        if (holder.getItemViewType() == 1) {
            int get = 0;
            if (trending)
                get = position - 1;
            else get = position;
            final Articles article = articlesList.get(get);
            final ViewHolder1 viewHolder1 = (ViewHolder1) holder;

            //thumbnail
            Picasso.get().load(article.getUrlToImage()).into(viewHolder1.imageView);

            //title
            viewHolder1.title.setText(article.getTitle());

            //published
            viewHolder1.publishedAt.setText(article.getPublishedAt().substring(0, 10));

            viewHolder1.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.putExtra("article", article);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewHolder1.imageView, viewHolder1.imageView.getTransitionName());
                    activity.startActivity(intent, options.toBundle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (trending)
            return articlesList.size() + 1;
        return articlesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (trending)
            if (position == 0)
                return 0;
        return 1;
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {

        View view;
        RecyclerView recyclerView;

        ViewHolder0(View itemView) {
            super(itemView);
            view = itemView;
            recyclerView = itemView.findViewById(R.id.horizontalrecyclerview);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        ImageButton like, bookmark;
        View view;
        ImageView imageView;
        TextView title, publishedAt;

        ViewHolder1(View itemView) {
            super(itemView);
            view = itemView;

            like = itemView.findViewById(R.id.imageButton);
            bookmark = itemView.findViewById(R.id.imageButton2);
            imageView = itemView.findViewById(R.id.imageView3);
            title = itemView.findViewById(R.id.textView);
            publishedAt = itemView.findViewById(R.id.textView2);

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
        }
    }
}
