package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.UnStarPostInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.storage.model.StarredPost;

/**
 * Created by steve on 4/11/16.
 */
public class UnStarPostInteractorImpl extends AbstractInteractor implements UnStarPostInteractor {
    private UnStarPostInteractor.Callback mCallback;
    private PostRepository mPostRepository;
    private int id;

    public UnStarPostInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    int id, PostRepository postRepository,
                                    UnStarPostInteractor.Callback callback) {
        super(threadExecutor, mainThread);

        if (postRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mPostRepository = postRepository;
        mCallback = callback;
        this.id = id;
    }

    @Override
    public void run() {
        final StarredPost post = mPostRepository.getStarredPostbyId(id);
        if (post != null) mPostRepository.delete(post);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPostUnstar(post);
            }
        });
    }
}