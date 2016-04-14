package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;
import com.steve.wanqureader.network.model.Issue;

import java.util.ArrayList;

/**
 * Created by steve on 4/6/16.
 */
public interface FetchIssuesListInteractor extends Interactor {
    interface Callback {
        void onIssuesRetrieved(ArrayList<Issue> issues);
    }
}
