package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;
import com.steve.wanqureader.network.model.Post;

import java.util.List;

/**
 * Created by steve on 3/28/16.
 * This interactor is responsible for retrieving a list of posts from the network.
 */
public interface GetLatestPostsInteractor extends Interactor {
    interface Callback {
        void onPostsRetrieved(List<Post> posts);
    }
}
