package com.hasom.mvc.IssueDetail.presenter;

import com.hasom.mvc.IssueDetail.adapter.contract.IssueCommentAdapterContract;

/**
 * Created by leejunho on 2017. 1. 14..
 */

public interface DetailPresenter {

    interface View {
        void updateProfile(String url);
        void updateUserName(String name);
        void updateIssueDetail(String text);
        void updateBody(String body);
        void hideKeyPad();
        void clearEditText();
        void showToast();
    }

    interface Presenter {
        void attachView(DetailPresenter.View view);

        void detachView();

        void loadIsueDetail(int issueNum);

        void setIssueCommentAdapterModel(IssueCommentAdapterContract.Model adapterModel);

        void setIssueCommentAdapterView(IssueCommentAdapterContract.View adapterView);

        void loadIsueComment(int issueNum);

        void sendIssueComment(int issueNum, String token, String comment);

        boolean checkRefreshData();

    }
}
