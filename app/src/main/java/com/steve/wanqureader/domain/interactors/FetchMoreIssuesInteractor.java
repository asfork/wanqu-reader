package com.steve.wanqureader.domain.interactors;

import com.steve.wanqureader.domain.interactors.base.Interactor;
import com.steve.wanqureader.network.model.Issue;

import java.util.ArrayList;

/**
 * Created by steve on 4/15/16.
 */
public interface FetchMoreIssuesInteractor extends Interactor {
    interface Callback {
        void onMoreIssuesRetrieved(ArrayList<Issue> issues);
    }
}
