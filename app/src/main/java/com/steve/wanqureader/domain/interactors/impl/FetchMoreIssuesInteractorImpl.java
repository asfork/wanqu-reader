package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchMoreIssuesInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.IssueRepository;
import com.steve.wanqureader.network.model.Issue;

import java.util.ArrayList;

/**
 * Created by steve on 4/15/16.
 */
public class FetchMoreIssuesInteractorImpl extends AbstractInteractor
        implements FetchMoreIssuesInteractor {
    private FetchMoreIssuesInteractor.Callback mCallback;
    private IssueRepository mIssueRepository;
    private int mPage;

    public FetchMoreIssuesInteractorImpl(Executor threadExecutor, MainThread mainThread, int page,
                                         IssueRepository issueRepository, Callback callback) {
        super(threadExecutor, mainThread);
        if (issueRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mIssueRepository = issueRepository;
        mCallback = callback;
        mPage = page;
    }

    @Override
    public void run() {
        final ArrayList<Issue> issues = mIssueRepository.fetchMoreIssuesList(mPage);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMoreIssuesRetrieved(issues);
            }
        });
    }
}
