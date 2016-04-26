package com.steve.wanqureader.presentation.ui.adapters;

/**
 * Created by steve on 4/19/16.
 */


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.steve.wanqureader.R;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.SearchPresenter;
import com.steve.wanqureader.presentation.ui.listeners.RecyclerViewClickListener;
import com.steve.wanqureader.utils.DateUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by steve on 3/22/16.
 */
public class SearchPostsAdapter extends RecyclerView.Adapter<SearchPostsAdapter.ViewHolder>
        implements RecyclerViewClickListener {
    private ArrayList<Post> mPostList;
    private Context mContext;
    public final SearchPresenter.View mView;

    public SearchPostsAdapter(SearchPresenter.View view, Context context) {
        mPostList = new ArrayList<>();
        mView = view;
        mContext = context;
    }

    public void refreshPosts(@NonNull ArrayList<Post> posts) {
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
        String info = mContext.getResources().getString(R.string.post_info);

        holder.domainView.setText(post.getUrlDomain());
        holder.infoView.setText(String.format(info,
                DateUtil.displayTime(post.getCreationDate()),
                post.getReadTimeMinutes()));
        holder.titleView.setText(post.getReadableTitle());
        holder.articleView.setText(post.getReadableArticle());
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                mView.onClickStarPost(post);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                mView.onClickUnStarPost(post.getPostNo());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    @Override
    public void onClickView(int position) {
        mView.onClickReadPost(mPostList.get(position).getUrl(), mPostList.get(position).getSlug());
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;

        @Bind(R.id.tv_header_domain)
        TextView domainView;
        @Bind(R.id.tv_title)
        TextView titleView;
        @Bind(R.id.tv_article)
        TextView articleView;
        @Bind(R.id.tv_header_info)
        TextView infoView;
        @Bind(R.id.likebutton)
        LikeButton likeButton;

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
