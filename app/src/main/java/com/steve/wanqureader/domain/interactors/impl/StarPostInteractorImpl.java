package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.StarPostInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.model.Post;

/**
 * Created by steve on 4/11/16.
 */
public class StarPostInteractorImpl extends AbstractInteractor implements StarPostInteractor {
    private StarPostInteractor.Callback mCallback;
    private PostRepository mPostRepository;
    private Post mPost;

    public StarPostInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                  Post post, PostRepository postRepository,
                                  StarPostInteractor.Callback callback) {
        super(threadExecutor, mainThread);

        if (postRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mPostRepository = postRepository;
        mCallback = callback;
        mPost = post;
    }

    @Override
    public void run() {
        mPostRepository.insert(mPost);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPostStarred();
            }
        });
    }
}
