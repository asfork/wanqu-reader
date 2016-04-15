package com.steve.wanqureader.presentation.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steve.wanqureader.R;
import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.presentation.presenters.FrontIssuesPresenter;
import com.steve.wanqureader.presentation.ui.listeners.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by steve on 3/22/16.
 */
public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder>
        implements RecyclerViewClickListener {
    private List<Issue> mIssuesList;
    private Context mContext;
    private final FrontIssuesPresenter.View mView;

    public IssuesAdapter(FrontIssuesPresenter.View view, Context context) {
        mIssuesList = new ArrayList<>();
        this.mView = view;
        this.mContext = context;
    }

    public void refreshIssues(@NonNull List<Issue> issues) {
        // clean up old data
        if (mIssuesList != null) {
            mIssuesList.clear();
        }
        mIssuesList = issues;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_issue, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Issue issue = mIssuesList.get(position);
        String issue_no = mContext.getResources().getString(R.string.issue_title);
//        String issue_title = DateUtil.titleTime(issue.getCreationDate())
//                + String.format(issue_no, issue.getIssueNo());

//        holder.titleView.setText(issue_title);
        holder.summaryView.setText(issue.getReadableTitle());
    }

    @Override
    public int getItemCount() {
        return mIssuesList == null ? 0 : mIssuesList.size();
    }

    @Override
    public void onClickView(int position) {
        mView.onClickReadIssue(mIssuesList.get(position).getIssueNo());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;

        @Bind(R.id.tv_title)
        TextView titleView;
        @Bind(R.id.tv_article)
        TextView summaryView;

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
