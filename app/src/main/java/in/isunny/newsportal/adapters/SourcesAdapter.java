package in.isunny.newsportal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.isunny.newsportal.R;
import in.isunny.newsportal.activities.SourceActivity;
import in.isunny.newsportal.utils.APIS;

/**
 * Created by Sunny on 4/15/2018.
 */

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.MyHViewHolder> {

    private Context context;
    private Activity activity;

    private String sources[] = new String[]{"TechRadar", "The Verge", "Ars Technica", "Mashable", "Engadget", "Hacker News"};
    private String images[] = new String[]{"https://vanilla.futurecdn.net/techradar/20180418/apple-touch-icon.png", "https://cdn.vox-cdn.com/uploads/chorus_asset/file/7395359/ios-icon.0.png", "http://cdn.arstechnica.net/apple-touch-icon.png", "https://mashable.com/apple-touch-icon-120x120.png?v=m2Pmw8zNwl", "https://s.blogsmithmedia.com/www.engadget.com/assets-h2652a51007af504aa41205f5ad6693c4/images/apple-touch-icon-120x120.png?h=232a14b1a350de05a49b584a62abac9e", "https://besticon-demo.herokuapp.com/lettericons/Y-120-ff6600.png"};
    private String sourcesApi[] = new String[]{APIS.TR_FEED, APIS.TV_FEED, APIS.ARS_FEED, APIS.MB_FEED, APIS.EG_FEED, APIS.HN_FEED};

    SourcesAdapter(Context c, Activity a) {
        context = c;
        activity = a;
    }

    @Override
    public MyHViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_topics, parent, false);
        return new MyHViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHViewHolder holder, final int position) {

        holder.sourceName.setText(sources[position]);
        Picasso.get().load(images[position]).into(holder.imageButton);

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SourceActivity.class);
                intent.putExtra("source", sourcesApi[position]);
                intent.putExtra("image", images[position]);
                intent.putExtra("name", sources[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class MyHViewHolder extends RecyclerView.ViewHolder {

        ImageButton imageButton;
        TextView sourceName;
        View view;

        MyHViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageButton = itemView.findViewById(R.id.imageButton7);
            sourceName = itemView.findViewById(R.id.trendingtopic);

        }
    }
}

