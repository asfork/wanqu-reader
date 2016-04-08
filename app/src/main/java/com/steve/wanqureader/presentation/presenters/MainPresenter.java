package com.steve.wanqureader.presentation.presenters;

import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.base.BasePresenter;
import com.steve.wanqureader.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by steve on 3/28/16.
 */
public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {

        void showPosts(List<Post> posts);

        void onClickReadPost(String url, String slug);

        void onClickLikePost(Post post);

        void onPostLiked(Post post);
    }

    void fetchPostsList();

    void fetchMorePostsList(Integer page);

    void likePost(Post post);
}