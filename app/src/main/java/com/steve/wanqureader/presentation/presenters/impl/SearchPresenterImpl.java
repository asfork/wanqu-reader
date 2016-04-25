package com.steve.wanqureader.presentation.presenters.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchPostsByIssueIdInteractor;
import com.steve.wanqureader.domain.interactors.StarPostInteractor;
import com.steve.wanqureader.domain.interactors.impl.FetchPostsByIssueIdInteractorImpl;
import com.steve.wanqureader.domain.interactors.impl.StarPostInteractorImpl;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.SearchPresenter;
import com.steve.wanqureader.presentation.presenters.base.AbstractPresenter;
import com.steve.wanqureader.utils.Constant;

import java.util.ArrayList;

/**
 * Created by steve on 4/18/16.
 */
public class SearchPresenterImpl extends AbstractPresenter
        implements SearchPresenter,
        FetchPostsByIssueIdInteractor.Callback,
        StarPostInteractor.Callback {

    private SearchPresenter.View mView;
    private PostRepository mPostRepository;

    public SearchPresenterImpl(Executor executor, MainThread mainThread,
                               View view, PostRepository postRepository) {
        super(executor, mainThread);

        mView = view;
        mPostRepository = postRepository;
    }

    @Override
    public void fetchPostsByIssueId(int issueId) {
        FetchPostsByIssueIdInteractorImpl interactor = new FetchPostsByIssueIdInteractorImpl(
                mExecutor,
                mMainThread,
                issueId,
                mPostRepository,
                this
        );
        interactor.execute();
        mView.onSetProgressBarVisibility(Constant.PROGRESS_VISIBLE);
    }

    @Override
    public void onPostsByIssueIdRetrieved(ArrayList<Post> posts) {
        mView.showPosts(posts);
        mView.onSetProgressBarVisibility(Constant.PROGRESS_INVISIBLE);
    }

    @Override
    public void starPost(Post post) {
        StarPostInteractorImpl interactor = new StarPostInteractorImpl(
                mExecutor,
                mMainThread,
                post,
                mPostRepository,
                this
        );
        interactor.execute();
    }

    @Override
    public void onPostStarred() {
        mView.onPostStarred();
    }

    @Override
    public void resume() {

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
