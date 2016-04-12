package com.steve.wanqureader.presentation.presenters.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchStarredPostsInteractor;
import com.steve.wanqureader.domain.interactors.UnStarPostInteractor;
import com.steve.wanqureader.domain.interactors.impl.FetchStarredPostsInteractorImpl;
import com.steve.wanqureader.domain.interactors.impl.UnStarPostInteractorImpl;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.presentation.presenters.StarredPresenter;
import com.steve.wanqureader.presentation.presenters.base.AbstractPresenter;
import com.steve.wanqureader.storage.model.StarredPost;

import java.util.List;

/**
 * Created by steve on 4/9/16.
 */
public class StarredPresenterImpl extends AbstractPresenter
        implements StarredPresenter, FetchStarredPostsInteractor.Callback,
        UnStarPostInteractor.Callback {

    private StarredPresenter.View mView;
    private PostRepository mPostRepository;

    public StarredPresenterImpl(Executor executor, MainThread mainThread,
                                View view, PostRepository postRepository) {
        super(executor, mainThread);
        mView = view;
        mPostRepository = postRepository;
    }

    @Override
    public void fetchStarredPostsList() {
        FetchStarredPostsInteractorImpl interactor = new FetchStarredPostsInteractorImpl(
                mExecutor,
                mMainThread,
                mPostRepository,
                this
        );
        interactor.execute();
    }

    @Override
    public void onPostsListRetrieved(List<StarredPost> posts) {
        mView.showPosts(posts);
    }

    @Override
    public void unStarPost(int id) {
        UnStarPostInteractorImpl interactor = new UnStarPostInteractorImpl(
                mExecutor,
                mMainThread,
                id,
                mPostRepository,
                this
        );
        interactor.execute();
    }

    @Override
    public void onPostUnstar(StarredPost post) {
        mView.onPostUnstarred(post);
    }

    @Override
    public void resume() {
        fetchStarredPostsList();
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

    }
}
