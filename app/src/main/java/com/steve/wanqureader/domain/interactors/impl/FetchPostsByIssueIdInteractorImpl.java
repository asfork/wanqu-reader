package com.steve.wanqureader.domain.interactors.impl;

import com.steve.wanqureader.domain.executor.Executor;
import com.steve.wanqureader.domain.executor.MainThread;
import com.steve.wanqureader.domain.interactors.FetchPostsByIssueIdInteractor;
import com.steve.wanqureader.domain.interactors.base.AbstractInteractor;
import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.model.Post;

import java.util.ArrayList;

/**
 * Created by steve on 4/19/16.
 */
public class FetchPostsByIssueIdInteractorImpl extends AbstractInteractor
        implements FetchPostsByIssueIdInteractor {
    private FetchPostsByIssueIdInteractor.Callback mCallback;
    private PostRepository mPostRepository;
    private int mIssueNum;

    public FetchPostsByIssueIdInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                             int id, PostRepository postRepository,
                                             FetchPostsByIssueIdInteractor.Callback callback) {
        super(threadExecutor, mainThread);

        if (postRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mIssueNum = id;
        mPostRepository = postRepository;
        mCallback = callback;
    }

    @Override
    public void run() {
        // retrieve the posts from the disk or network
        final ArrayList<Post> posts = mPostRepository.fetchPostsByIssueNum(mIssueNum);
        // show posts on the main thread
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPostsByIssueIdRetrieved(posts);
            }
        });
    }
}
