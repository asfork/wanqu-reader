package com.steve.wanqureader.presentation.presenters;

import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.base.BasePresenter;
import com.steve.wanqureader.presentation.ui.BaseView;

import java.util.ArrayList;

/**
 * Created by steve on 3/28/16.
 */
public interface PostsPresenter extends BasePresenter {

    interface View extends BaseView {

        void showPosts(ArrayList<Post> posts);

        void showMorePosts(ArrayList<Post> posts);

        void onClickReadPost(String url, String slug);

        void onClickStarPost(Post post);

        void onPostStarred();

        void onClickUnStarPost(int id);

        void onPostUnStarred();
    }

    void fetchPostsList();

    void fetchMorePostsList(int page);

    void starPost(Post post);

    void unStarPost(int id);
}