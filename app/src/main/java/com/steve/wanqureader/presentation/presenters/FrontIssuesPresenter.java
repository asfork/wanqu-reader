package com.steve.wanqureader.presentation.presenters;

import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.presentation.presenters.base.BasePresenter;
import com.steve.wanqureader.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by steve on 4/6/16.
 */
public interface FrontIssuesPresenter extends BasePresenter {

    interface View extends BaseView {
        void showIssues(List<Issue> issues);

        void onClickReadIssue(int issueId);
    }

    void fetchIssuesList();
}
