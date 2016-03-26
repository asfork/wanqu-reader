package com.steve.wanqureader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steve.wanqureader.model.entitiy.Post;
import com.steve.wanqureader.util.DateUtil;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post post = posts.get(position);
        String readTime = context.getResources().getString(R.string.read_time);

        holder.domainView.setText(post.urlDomain);
        holder.dateView.setText(DateUtil.displayTime(post.createdAt));
        holder.costsView.setText(String.format(readTime, post.readTime));
        holder.titleView.setText(post.title);
        holder.summaryView.setText(post.summary);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    public void setData(List<Post> posts) {
        this.posts = posts;
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_header_domain)
        TextView domainView;
        @Bind(R.id.tv_title)
        TextView titleView;
        @Bind(R.id.tv_summary)
        TextView summaryView;
        @Bind(R.id.tv_header_date)
        TextView dateView;
        @Bind(R.id.tv_header_costs)
        TextView costsView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
