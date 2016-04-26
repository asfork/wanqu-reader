package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.UnStarByPostNumInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.storage.model.StarredPost;

/**
 * Created by steve on 4/26/16.
 */
public class UnStarByPostNumInteractorImpl extends AbstractInteractor implements UnStarByPostNumInteractor {
    private UnStarByPostNumInteractor.Callback mCallback;
    private PostRepository mPostRepository;
    private int No;

    public UnStarByPostNumInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         int num, PostRepository postRepository,
                                         UnStarByPostNumInteractor.Callback callback) {
        super(threadExecutor, mainThread);

        if (postRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mPostRepository = postRepository;
        mCallback = callback;
        this.No = num;
    }

    @Override
    public void run() {
        final StarredPost post = mPostRepository.getStarredPostByNum(No);
        if (post != null) mPostRepository.delete(post);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPostUnstarred();
            }
        });
    }
}
