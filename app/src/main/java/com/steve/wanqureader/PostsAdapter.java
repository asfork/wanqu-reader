package com.steve.wanqureader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steve.wanqureader.model.entitiy.Post;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by steve on 3/22/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    List<Post> posts;
    Context context;

    public PostsAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = posts.get(position);
        holder.titleView.setText(post.title);
        holder.domainView.setText(post.url);
        holder.summaryView.setText(post.summary);
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    public void setData(List<Post> posts) {
        this.posts = posts;
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView titleView;
        @Bind(R.id.tv_domain)
        TextView domainView;
        @Bind(R.id.tv_summary)
        TextView summaryView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
