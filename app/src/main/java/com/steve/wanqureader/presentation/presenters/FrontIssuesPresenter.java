package com.steve.wanqureader.presentation.presenters;

import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.presentation.presenters.base.BasePresenter;
import com.steve.wanqureader.presentation.ui.BaseView;

import java.util.ArrayList;

/**
 * Created by steve on 4/6/16.
 */
public interface FrontIssuesPresenter extends BasePresenter {

    interface View extends BaseView {
        void showIssues(ArrayList<Issue> issues);

        void showMoreIssues(ArrayList<Issue> issues);

        void onClickReadIssue(int issueId);
    }

    void fetchIssuesList();

    void fetchMoreIssuesList(int page);
}
