package com.steve.wanqureader.presentation.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steve.wanqureader.R;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.MainPresenter;
import com.steve.wanqureader.presentation.ui.listeners.RecyclerViewClickListener;
import com.steve.wanqureader.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by steve on 3/22/16.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> implements RecyclerViewClickListener {
    private List<Post> mPostList;
    private Context mContext;
    public final MainPresenter.View mView;

    public PostsAdapter(MainPresenter.View view, Context context) {
        mPostList = new ArrayList<>();
        mView = view;
        mContext = context;
    }

    public void refreshPosts(@NonNull List<Post> posts) {
        // clean up old data
        if (mPostList != null) {
            mPostList.clear();
        }
        mPostList = posts;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post post = mPostList.get(position);
        String readTime = mContext.getResources().getString(R.string.read_time);

        holder.domainView.setText(post.getUrlDomain());
        holder.dateView.setText(DateUtil.displayTime(post.getCreationDate()));
        holder.costsView.setText(String.format(readTime, post.getReadTimeMinutes()));
        holder.titleView.setText(post.getReadableArticle());
        holder.summaryView.setText(post.getReadableSummary());
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    @Override
    public void onClickView(int position) {
        mView.onClickReadPost(mPostList.get(position));
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;

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

        public ViewHolder(View view, final RecyclerViewClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClickView(getAdapterPosition());
        }
    }
}
