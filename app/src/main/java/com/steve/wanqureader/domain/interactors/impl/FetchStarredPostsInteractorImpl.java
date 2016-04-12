package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchPostsListInteractor;
import com.steve.wanqureader.domain.interactors.FetchStarredPostsInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.storage.model.StarredPost;

import java.util.List;

/**
 * Created by steve on 4/9/16.
 */
public class FetchStarredPostsInteractorImpl extends AbstractInteractor implements FetchPostsListInteractor {
    private PostRepository mPostRepository;
    private FetchStarredPostsInteractor.Callback mCallback;

    public FetchStarredPostsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                           PostRepository postRepository,
                                           FetchStarredPostsInteractor.Callback callback) {
        super(threadExecutor, mainThread);

        if (postRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mPostRepository = postRepository;
        mCallback = callback;
    }

    @Override
    public void run() {
        final List<StarredPost> posts = mPostRepository.getStarredPostsList();

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPostsListRetrieved(posts);
            }
        });
    }
}
