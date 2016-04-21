package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;
import com.steve.wanqureader.network.model.Post;

import java.util.ArrayList;

/**
 * Created by steve on 4/19/16.
 */
public interface FetchPostsByIssueIdInteractor extends Interactor {
    interface Callback {
        void onPostsByIssueIdRetrieved(ArrayList<Post> posts);
    }
}
