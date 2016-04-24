package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;

/**
 * Created by steve on 4/24/16.
 */
public interface RestarPostInteractor extends Interactor {
    interface Callback {
        void onPostRestarred();
    }
}
