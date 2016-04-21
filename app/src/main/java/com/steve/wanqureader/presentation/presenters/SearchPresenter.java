package com.steve.wanqureader.presentation.presenters;

import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.base.BasePresenter;
import com.steve.wanqureader.presentation.ui.BaseView;

import java.util.ArrayList;

/**
 * Created by steve on 4/18/16.
 */
public interface SearchPresenter extends BasePresenter {

    interface View extends BaseView {

        void showPosts(ArrayList<Post> posts);

        void onClickReadPost(String url, String slug);

        void onClickStarPost(Post post);

        void onPostStarred();
    }

    void fetchPostsByIssueId(int issueId);

    void starPost(Post post);
}
