package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;
import com.steve.wanqureader.network.model.Post;

import java.util.List;

/**
 * Created by steve on 4/8/16.
 */
public interface FetchMorePostsListInteractor extends Interactor {
    interface Callback {
        void onMorePostsRetrieved(List<Post> posts);
    }
}
