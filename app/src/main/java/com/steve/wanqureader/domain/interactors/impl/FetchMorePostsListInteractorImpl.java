package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchMorePostsListInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.model.Post;

import java.util.ArrayList;

/**
 * Created by steve on 4/8/16.
 */
public class FetchMorePostsListInteractorImpl extends AbstractInteractor implements FetchMorePostsListInteractor {
    private FetchMorePostsListInteractor.Callback mCallback;
    private PostRepository mPostRepository;
    private int mPage;

    public FetchMorePostsListInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                            int page, PostRepository postRepository,
                                            FetchMorePostsListInteractor.Callback callback) {
        super(threadExecutor, mainThread);

        if (postRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mPage = page;
        mPostRepository = postRepository;
        mCallback = callback;
    }

    @Override
    public void run() {
        // retrieve the posts from the disk or network
        final ArrayList<Post> posts = mPostRepository.fetchMorePostsList(mPage);

        // show posts on the main thread
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMorePostsRetrieved(posts);
            }
        });
    }
}
