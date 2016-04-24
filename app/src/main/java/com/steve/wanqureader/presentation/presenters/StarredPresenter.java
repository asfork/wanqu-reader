package com.steve.wanqureader.presentation.presenters;

import com.steve.wanqureader.presentation.presenters.base.BasePresenter;
import com.steve.wanqureader.presentation.ui.BaseView;
import com.steve.wanqureader.storage.model.StarredPost;

import java.util.List;

/**
 * Created by steve on 4/9/16.
 */
public interface StarredPresenter extends BasePresenter {

    interface View extends BaseView {

        void showPosts(List<StarredPost> posts);

        void onClickReadStarredPost(String url, String slug);

        void onClickUnstarPost(int id, int position);

        void onPostUnstarred(int position);

        void onClickRestarPost(StarredPost post);

        void onPostRestarred();
    }

    void fetchStarredPostsList();

    void unStarPost(int id, int position);

    void reStarPost(StarredPost post);
}
