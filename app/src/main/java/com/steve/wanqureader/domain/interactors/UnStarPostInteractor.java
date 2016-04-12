package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;
import com.steve.wanqureader.storage.model.StarredPost;

/**
 * Created by steve on 4/11/16.
 */
public interface UnStarPostInteractor extends Interactor {
    interface Callback {
        void onPostUnstar(StarredPost post);
    }
}
