package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.RestarPostInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.storage.model.StarredPost;

/**
 * Created by steve on 4/24/16.
 */
public class RestarPostInteractorImpl extends AbstractInteractor implements RestarPostInteractor {
    private RestarPostInteractor.Callback mCallback;
    private PostRepository mPostRepository;
    private StarredPost mPost;

    public RestarPostInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    StarredPost post, PostRepository postRepository,
                                    RestarPostInteractor.Callback callback) {
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
                mCallback.onPostRestarred();
            }
        });
    }
}