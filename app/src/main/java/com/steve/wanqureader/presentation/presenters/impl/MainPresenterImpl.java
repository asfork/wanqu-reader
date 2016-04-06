package com.steve.wanqureader.presentation.presenters.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.GetLatestPostsInteractor;
import com.steve.wanqureader.domain.interactors.impl.GetLatestPostsInteractorImpl;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.MainPresenter;
import com.steve.wanqureader.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by steve on 3/28/16.
 */
public class MainPresenterImpl extends AbstractPresenter
        implements MainPresenter, GetLatestPostsInteractor.Callback {

    private MainPresenter.View mView;
    private PostRepository mPostRepository;

    public MainPresenterImpl(Executor executor, MainThread mainThread,
                             View view, PostRepository postRepository) {
        super(executor, mainThread);
        mView = view;
        mPostRepository = postRepository;
    }

    @Override
    public void getLatestPosts() {
        GetLatestPostsInteractorImpl getLatestPostsInteractor = new GetLatestPostsInteractorImpl(
                mExecutor,
                mMainThread,
                mPostRepository,
                this
        );
        getLatestPostsInteractor.execute();
    }

    @Override
    public void onPostsRetrieved(List<Post> posts) {
        mView.showPosts(posts);
        mView.onSetProgressBarVisibility(false);
    }

    @Override
    public void likePost(Post post) {

    }

    @Override
    public void resume() {
        getLatestPosts();
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
