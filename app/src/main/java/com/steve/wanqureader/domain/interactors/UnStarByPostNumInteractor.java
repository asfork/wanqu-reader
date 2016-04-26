package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;

/**
 * Created by steve on 4/26/16.
 */
public interface UnStarByPostNumInteractor extends Interactor {
    interface Callback {
        void onPostUnstarred();
    }
}
