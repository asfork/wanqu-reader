package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchIssuesListInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.IssueRepository;
import com.steve.wanqureader.network.model.Issue;

import java.util.List;

/**
 * Created by steve on 4/6/16.
 */
public class FetchIssuesListInteractorImpl extends AbstractInteractor
        implements FetchIssuesListInteractor {
    private FetchIssuesListInteractor.Callback mCallback;
    private IssueRepository mIssueRepository;

    public FetchIssuesListInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         IssueRepository issueRepository, Callback callback) {
        super(threadExecutor, mainThread);
        if (issueRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mIssueRepository = issueRepository;
        mCallback = callback;
    }

    @Override
    public void run() {
        final List<Issue> issues = mIssueRepository.fetchIssuesList();
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onIssuesRetrieved(issues);
            }
        });
    }
}
