package com.steve.wanqureader.presentation.presenters.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchMorePostsListInteractor;
import com.steve.wanqureader.domain.interactors.FetchPostsListInteractor;
import com.steve.wanqureader.domain.interactors.impl.FetchMorePostsListInteractorImpl;
import com.steve.wanqureader.domain.interactors.impl.FetchPostsListInteractorImpl;
import com.steve.wanqureader.domain.interactors.impl.StarPostInteractorImpl;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.PostsPresenter;
import com.steve.wanqureader.presentation.presenters.base.AbstractPresenter;
import com.steve.wanqureader.utils.Constant;

import java.util.ArrayList;

/**
 * Created by steve on 3/28/16.
 */
public class PostsPresenterImpl extends AbstractPresenter
        implements PostsPresenter,
        FetchPostsListInteractor.Callback,
        FetchMorePostsListInteractor.Callback,
        StarPostInteractorImpl.Callback {

    private PostsPresenter.View mView;
    private PostRepository mPostRepository;

    public PostsPresenterImpl(Executor executor, MainThread mainThread,
                              View view, PostRepository postRepository) {
        super(executor, mainThread);
        mView = view;
        mPostRepository = postRepository;
    }

    @Override
    public void fetchPostsList() {
        FetchPostsListInteractorImpl fetchPostsListInteractor = new FetchPostsListInteractorImpl(
                mExecutor,
                mMainThread,
                mPostRepository,
                this
        );
        fetchPostsListInteractor.execute();
    }

    @Override
    public void onPostsRetrieved(ArrayList<Post> posts) {
        mView.showPosts(posts);
        mView.onSetProgressBarVisibility(Constant.PROGRESS_HEADER_INVISIBILITY);
    }

    @Override
    public void fetchMorePostsList(int page) {
        FetchMorePostsListInteractorImpl fetchMorePostsListInteractor = new FetchMorePostsListInteractorImpl(
                mExecutor,
                mMainThread,
                page,
                mPostRepository,
                this
        );
        fetchMorePostsListInteractor.execute();
    }

    @Override
    public void onMorePostsRetrieved(ArrayList<Post> posts) {
        mView.showMorePosts(posts);
        mView.onSetProgressBarVisibility(Constant.PROGRESS_FOOTER_INVISIBILITY);
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
        fetchPostsList();
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
