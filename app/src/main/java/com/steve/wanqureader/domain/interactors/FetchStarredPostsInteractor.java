package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;
import com.steve.wanqureader.storage.model.StarredPost;

import java.util.List;

/**
 * Created by steve on 4/9/16.
 */
public interface FetchStarredPostsInteractor extends Interactor {
    interface Callback {
        void onPostsListRetrieved(List<StarredPost> posts);
    }
}
