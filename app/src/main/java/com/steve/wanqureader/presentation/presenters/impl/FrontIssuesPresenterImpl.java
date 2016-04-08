package com.steve.wanqureader.presentation.presenters.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchIssuesListInteractor;
import com.steve.wanqureader.domain.interactors.impl.FetchIssuesListInteractorImpl;
import com.steve.wanqureader.domain.repository.IssueRepository;
import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.presentation.presenters.FrontIssuesPresenter;
import com.steve.wanqureader.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by steve on 4/6/16.
 */
public class FrontIssuesPresenterImpl extends AbstractPresenter
        implements FrontIssuesPresenter, FetchIssuesListInteractor.Callback {
    private IssueRepository mIssueRepository;
    private FrontIssuesPresenter.View mView;

    public FrontIssuesPresenterImpl(Executor executor, MainThread mainThread,
                                    View view, IssueRepository issueRepository) {
        super(executor, mainThread);
        mView = view;
        mIssueRepository = issueRepository;
    }

    @Override
    public void fetchIssuesList() {
        FetchIssuesListInteractorImpl fetchIssuesListInteractor = new FetchIssuesListInteractorImpl(
                mExecutor,
                mMainThread,
                mIssueRepository,
                this
        );
        fetchIssuesListInteractor.execute();
    }

    @Override
    public void onIssuesRetrieved(List<Issue> issues) {
        mView.showIssues(issues);
        mView.onSetProgressBarVisibility(false);
    }

    @Override
    public void resume() {
        fetchIssuesList();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        mView.onError(message);
    }
}
